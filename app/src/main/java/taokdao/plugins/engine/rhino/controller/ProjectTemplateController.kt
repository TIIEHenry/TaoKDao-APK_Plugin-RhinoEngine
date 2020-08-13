package taokdao.plugin.engines.rhino.controller

import android.content.Context
import taokdao.api.data.bean.Properties
import taokdao.api.main.IMainContext
import taokdao.api.main.action.MainAction
import taokdao.api.plugin.bean.PluginManifest
import taokdao.api.template.project.ProjectTemplatePool
import taokdao.api.template.project.wrapped.ProjectTemplate
import taokdao.plugins.engine.rhino.AConstant
import taokdao.plugins.engine.rhino.R
import tiiehenry.android.ui.dialogs.api.callback.InputCallback
import tiiehenry.android.ui.dialogs.api.strategy.Dialogs
import tiiehenry.io.Filej
import java.io.File
import java.io.IOException

class ProjectTemplateController {

    private lateinit var projectTemplate: ProjectTemplate
    private lateinit var pluginContext: Context

    fun onAttach(pluginContext: Context) {
        this.pluginContext = pluginContext
    }

    fun onInit(iMainContext: IMainContext, pluginManifest: PluginManifest) {
        projectTemplate = ProjectTemplate(
            Properties(
                AConstant.Project_Template_ID,
                "AndroLua+ Project",
                "project for AndroLua+"
            ),
            pluginContext.getDrawable(R.mipmap.ic_launcher)
            ,
            ProjectTemplate.Callback { file ->
                showCreateDialog(
                    iMainContext,
                    file,
                    pluginManifest.pluginDir
                )
            }, null
        )
//        ProjectTemplatePool.getInstance().add(projectTemplate)
    }

    fun onDestroy(iMainContext: IMainContext, pluginManifest: PluginManifest) {
        ProjectTemplatePool.getInstance().remove(projectTemplate)
    }


    private fun showCreateDialog(
        main: IMainContext,
        currentDir: File,
        pluginDir: File
    ) {
        Dialogs.global.asInput()
            .title("请输入项目名")
            .input("请输入项目名",
                "demoProject",
                InputCallback { dialog, input ->
                    if (input == "") {
                        dialog.setInputError("项目名不能为空")
                        return@InputCallback
                    }
                    val newDir = Filej(currentDir, input.toString())
                    if (newDir.exists()) {
                        dialog.setInputError("项目名已存在")
                        return@InputCallback
                    }
                    Filej(newDir, "lua").mkdirs()
                    if (!newDir.exists()) {
                        dialog.setInputError("新建项目文件夹失败")
                        return@InputCallback
                    }
                    try {
                        Filej("$pluginDir/project").copyTo(newDir, true)
                        val config = File(newDir, "project.json")
                        val mainFile = File(newDir, "lua/main.lua")
                        main.projectManager.openProject(config)
                        main.fileOpenManager.requestOpen(mainFile.absolutePath)
                        main.fileOpenManager.requestOpen(config.absolutePath)
                        MainAction.onFileCreated.runObservers(main)
                        main.explorerWindow.hideWindow()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        dialog.setInputError(e.message)
                    }
                })
            .negativeText()
            .positiveText()
            .show()
    }

}
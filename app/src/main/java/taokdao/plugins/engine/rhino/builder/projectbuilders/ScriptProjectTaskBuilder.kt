package taokdao.plugins.engine.rhino.builder.projectbuilders


import org.mozilla.javascript.Function
import taokdao.api.builder.wrapped.BuildOption
import taokdao.api.data.bean.Properties
import taokdao.api.main.IMainContext
import taokdao.api.project.bean.Project
import taokdao.api.project.build.IProjectBuilder
import taokdao.api.ui.progressbar.ProgressBarSet
import taokdao.plugins.engine.rhino.engine.RhinoPluginEngine
import tiiehenry.script.rhino.RhinoEngine
import tiiehenry.script.rhino.RhinoEngineFactory
import tiiehenry.script.rhino.lang.RhinoFunction
import java.io.File

class ScriptProjectTaskBuilder(private val rhinoPluginEngine: RhinoPluginEngine) : IProjectBuilder {

    override fun id(): String {
        return "rhinoScript"
    }

    private val run = BuildOption<Project>(Properties("run", "run")) { main, config, _ ->
        ProgressBarSet.BOTTOM_HORIZONTAL.addUser(config.projectDir.absolutePath)
        try {
            callScriptTask(main, config, "runProject")
        } catch (e: Exception) {
            e.printStackTrace()
            main.send(e.message)
        } finally {
            ProgressBarSet.BOTTOM_HORIZONTAL.removeUser(config.projectDir.absolutePath)
        }
        true
    }

    private val build = BuildOption<Project>(Properties("build", "build")) { main, config, _ ->
        ProgressBarSet.BOTTOM_HORIZONTAL.addUser(config.projectDir.absolutePath)
        try {
            callScriptTask(main, config, "buildProject")
        } catch (e: Exception) {
            e.printStackTrace()
            main.send(e.message)
        } finally {
            ProgressBarSet.BOTTOM_HORIZONTAL.removeUser(config.projectDir.absolutePath)
        }
        true
    }

    private val buildList = mutableListOf(run, build)


    override fun getBuildOptionList(): MutableList<BuildOption<Project>> {
        return buildList
    }

    private fun callScriptTask(main: IMainContext, config: Project, name: String): Boolean {
        val engine = loadProjectBuildScript(main, File(config.projectDir, "build.js"))
            ?: return false
        return engine.let {
            val f = it.getScriptFunction(name)
            if (f != null)
                RhinoFunction(it, f).call(config.projectDir)
            true
        }
    }


    private fun RhinoEngine.getScriptFunction(name: String): Function? {
        val scriptMap = this.varBridge.get("script")?.value as? Map<*, *>?
        when (val f = scriptMap?.get(name)) {
            is Function -> return f
        }
        return null
    }

    private fun loadProjectBuildScript(main: IMainContext, configFile: File): RhinoEngine? {
        if (!configFile.isFile)
            return null

        val engine = RhinoEngineFactory().newScriptEngine().apply {
            create()
            requirer.findPathList.add(rhinoPluginEngine.getSourceDir())

            requirer.require("apply")
            stringEvaluator.evalSafely("var api=Packages.tiiehenry.taokdao.api;")
            try {
                fileEvaluator.eval(configFile)
            } catch (e: Exception) {
                e.printStackTrace()
                main.send("加载工程配置文件失败：" + e.message)
            }
        }

        val func = engine.varBridge.get("script")?.value as? Map<*, *>?
        if (func != null)
            return engine
        return null
    }

}
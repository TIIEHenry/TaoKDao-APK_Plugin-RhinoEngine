package taokdao.plugins.engine.rhino.builder.filebuilders

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.Keep
import taokdao.api.builder.IBuildOption
import taokdao.api.data.bean.Properties
import taokdao.api.main.IMainContext
import taokdao.api.ui.toolpage.IToolPageWindow
import taokdao.api.ui.toolpage.content.tree.ITreeItemCallback
import taokdao.api.ui.toolpage.content.tree.TreeItem
import taokdao.api.ui.toolpage.groups.build.BuildToolTab
import taokdao.api.ui.window.callback.BaseWindowStateObserver
import taokdao.plugins.engine.rhino.controller.TabToolController
import tiiehenry.script.rhino.RhinoEngineFactory
import tiiehenry.script.wrapper.framework.internal.GlobalScriptContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Keep
class RunWithRhino(private val tabToolController: TabToolController) : IBuildOption<File> {
    private val tabTool = tabToolController.rhinoTabTool

    private val rhinoEngine = RhinoEngineFactory().newScriptEngine(
        GlobalScriptContext(
            System.`in`,
            RhinoBuilderOutputStream(tabTool.fragment),
            RhinoBuilderOutputStream(tabTool.fragment)
        )
    ).apply {
        create()

    }
    private val dateFormatter = SimpleDateFormat("yyyy/MM/dd hh:mm")

    override fun onBuild(main: IMainContext, config: File): Boolean {
        val clickCallback= ITreeItemCallback{
            main.toolPageWindow.hideWindow()
            main.contentManager.show(config.absolutePath)
        }
        val rootTreeItem = TreeItem(config.name, "at " + dateFormatter.format(Date()),"run with rhino: "+config.absolutePath).apply {
            callback=clickCallback
        }
        val buildGroup=main.toolPageWindow.internalToolGroupManager.orCreateBuildGroup
        val buildToolTab= buildGroup.get(id())
            ?:BuildToolTab(Properties(id(),"Rhino"),null, mutableListOf()).apply {
                buildGroup.add(this)
            }
        buildToolTab.content.clear()
        buildToolTab.content.add(rootTreeItem)
        val fragment = tabTool.fragment
        try {
            val variable = rhinoEngine.fileEvaluator.eval(config)
//            fragment.addRunResult(
//                variable?.getString() ?: variable?.value?.toString() ?: "null"
//            )
//            main.toolPageWindow.addStateObserver(object : BaseWindowStateObserver<IToolPageWindow>() {
//                override fun onWindowShow(window: IToolPageWindow) {
//                    window.removeStateObserver(this)
//                    window.show(tabTool)
//                }
//            })
            main.toolPageWindow.show(tabTool)
        } catch (e: Exception) {
            val buildErrorItem = TreeItem(e.message, null,e.message).apply {
                callback=clickCallback
            }
            rootTreeItem.addChild(buildErrorItem)
//            main.toolPageWindow.addStateObserver(object : BaseWindowStateObserver<IToolPageWindow>() {
//                override fun onWindowShow(window: IToolPageWindow) {
//                    window.removeStateObserver(this)
//                    window.show(buildGroup)
//                    buildGroup.show(buildToolTab)
//                }
//            })
            main.toolPageWindow.show(buildGroup)
            buildGroup.show(buildToolTab)
//            fragment.addRunResult(e.message.toString())
        }
        buildGroup.refreshContent()
        main.toolPageWindow.showWindow()
        return true
    }

    override fun getIcon(): Drawable? {
        return null
    }

    override fun id(): String {
        return javaClass.simpleName
    }

    override fun getLabel(): String {
        return javaClass.simpleName
    }

    override fun getDescription(): String? {
        return "run with rhino on toolpage"
    }
}
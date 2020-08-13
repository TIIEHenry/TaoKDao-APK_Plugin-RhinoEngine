package taokdao.plugin.engines.rhino.builder.filebuilders

import android.graphics.drawable.Drawable
import androidx.annotation.Keep
import taokdao.api.builder.IBuildOption
import taokdao.api.main.IMainContext
import taokdao.api.ui.window.callback.BaseWindowStateObserver
import taokdao.api.ui.window.tabtool.ITabToolWindow
import taokdao.plugins.engine.rhino.controller.TabToolController
import tiiehenry.script.rhino.RhinoEngineFactory
import tiiehenry.script.wrapper.framework.internal.GlobalScriptContext
import java.io.File

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

    override fun onBuild(main: IMainContext, config: File): Boolean {
        val fragment = tabTool.fragment
        try {
            val variable = rhinoEngine.fileEvaluator.eval(config)
//            fragment.addRunResult(
//                variable?.getString() ?: variable?.value?.toString() ?: "null"
//            )
        } catch (e: Exception) {
            fragment.addRunResult(e.message.toString())
        }
        main.tabToolWindow.addStateObserver(object : BaseWindowStateObserver<ITabToolWindow>() {
            override fun onWindowShow(window: ITabToolWindow) {
                window.removeStateObserver(this)
                window.show(tabTool)
            }
        })
        main.tabToolWindow.showWindow()
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
        return "run with rhino on tabtool"
    }
}
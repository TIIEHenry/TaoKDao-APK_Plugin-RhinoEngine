package taokdao.plugins.engine.rhino.builder.filebuilders

import android.content.ComponentName
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.Keep
import taokdao.api.builder.IBuildOption
import taokdao.api.main.IMainContext
import tiiehenry.script.app.rhino.RhinoActivity
import tiiehenry.script.engine.android.ScriptContextConst
import java.io.File

@Keep
class RunWithRhinoOnNewActivity : IBuildOption<File> {
    override fun onBuild(main: IMainContext, config: File): Boolean {
        try {
            startActivity(main, config)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            main.send(e.message)
        }
        return false
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
        return "run with rhino on new activity"
    }


    fun startActivity(main: IMainContext, config: File): Boolean {
        val theme =
            "Packages.taokdao.plugins.engine.rhino.R.style.Theme_AppCompat" + if (main.themeManager.shouldDark())
                ""
            else
                "_Light"
        try {
            main.startActivity(Intent(Intent.ACTION_VIEW).apply {
                component =ComponentName("taokdao.plugins.engine.rhino", RhinoActivity::class.java.name)
//                putExtra(ScriptContextConst.ENGINE_FACTORY_NAME,"Rhino")
                putExtra(
                    ScriptContextConst.Companion.INTENT.SCRIPT_BEFORE_DATA,
                    "activity.setTheme(${theme});"
                )
                data = Uri.fromFile(File(config.absolutePath))
//                putExtra(ScriptContextConst.Companion.INTENT.SCRIPT_AFTER_DATA, "load(\"${config.absolutePath}\");")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        } catch (e: Exception) {
            e.printStackTrace()
            main.send(e.message)
        }
        return true
    }
}
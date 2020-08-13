package taokdao.plugins.engine.rhino.tabtool

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import androidx.annotation.Keep
import taokdao.api.main.IMainContext
import taokdao.api.setting.preference.base.IPreference
import taokdao.api.setting.theme.ThemeParts
import taokdao.api.ui.window.tabtool.ITabTool
import taokdao.api.ui.window.tabtool.callback.TabToolStateObserver
import taokdao.api.ui.window.tabtool.menu.TabToolMenu
import taokdao.plugins.engine.rhino.R
import java.util.*

@Keep
class RhinoTabTool(private val pluginContext: Context, val main: IMainContext) : ITabTool {
    private val fragment = RhinoTabToolFragment(pluginContext, main)
    private val appbarColors = main.themeManager.getThemeColors(ThemeParts.APPBAR)
    private val foregroundColor =
        main.themeManager.getThemeColors(ThemeParts.BOTTOM).foregroundColor
    private val icon =
        (pluginContext.getDrawable(R.drawable.tabtool_icon) as VectorDrawable).apply {
            setTint(foregroundColor)
        }

    private val menuToBottom = TabToolMenu(
        (pluginContext.getDrawable(R.drawable.tabtool_rhino_menu_tobottom) as VectorDrawable).apply {
            setTint(appbarColors.foregroundColor)
        }
    ) {
        fragment.scrollToBottom()
    }
    private val menuClear = TabToolMenu(
        (pluginContext.getDrawable(R.drawable.tabtool_rhino_menu_clear) as VectorDrawable).apply {
            setTint(appbarColors.foregroundColor)
        }
    ) {
        fragment.clearResultText()
    }
    private val menuList = arrayListOf(menuToBottom, menuClear)

    override fun id(): String = RhinoTabTool::class.java.name

    override fun getIcon(): Drawable? {
        return icon
    }

    override fun getLabel(): String = "Rhino"

    override fun getMenuList(): ArrayList<TabToolMenu> {
        return menuList
    }

    override fun getSettingList(): ArrayList<IPreference<*>> {
        return arrayListOf()
    }

    override fun getFragment(): RhinoTabToolFragment = fragment

    override fun getStateObserver(): TabToolStateObserver? {
        return null
    }
}
package taokdao.plugin.engines.rhino.tabtool

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import taokdao.api.base.fragment.StateFragment
import taokdao.api.main.IMainContext
import taokdao.api.setting.theme.ThemeParts
import taokdao.plugins.engine.rhino.R
import java.text.SimpleDateFormat
import java.util.*

class RhinoTabToolFragment(private val pluginContext: Context, val main: IMainContext) :
    StateFragment() {
    private val welcome = "Welcome to rhino plugin engine"
    private lateinit var scrollView: ScrollView
    private var tvOut: TextView? = null
    private val printDataFormatter: SimpleDateFormat =
        SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private val contentColors = main.themeManager.getThemeColors(ThemeParts.CONTENT)

    override fun getContext(): Context {
        return pluginContext
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.tabtool_layout, container, false)
    }

    override fun initView(view: View) {
        tvOut = view.findViewById(R.id.tv_out)
        scrollView = view.findViewById(R.id.scroll_view)
        tvOut?.setTextColor(contentColors.foregroundColor)
    }

    override fun onResume() {
        super.onResume()
        setResultText()
    }

    private fun setResultText() {
        tvOut?.text = resultStringBuilder
    }

    private val resultStringBuilder = StringBuilder().append(welcome)
    fun addRunResult(message: String) {
//        if (message == "\n")
        resultStringBuilder.append("\n")
        resultStringBuilder.append(printDataFormatter.format(Date()))
        resultStringBuilder.append(": ")
        resultStringBuilder.append(message)
        setResultText()
    }

    fun scrollToBottom() {
        scrollView.fullScroll(ScrollView.FOCUS_DOWN)
    }

    fun clearResultText() {
        tvOut?.text = welcome
    }

}
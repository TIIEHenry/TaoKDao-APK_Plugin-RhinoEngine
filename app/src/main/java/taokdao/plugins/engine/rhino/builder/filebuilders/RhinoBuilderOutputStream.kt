package taokdao.plugin.engines.rhino.builder.filebuilders

import taokdao.plugin.engines.rhino.tabtool.RhinoTabToolFragment
import tiiehenry.script.wrapper.framework.internal.OutputPrintStream
import java.util.*

class RhinoBuilderOutputStream(private val fragment: RhinoTabToolFragment) :
    OutputPrintStream(System.out, true) {
    override val formatter: Formatter = Formatter(this)

    override fun onPrint(cs: CharSequence) {
        fragment.addRunResult(cs.toString())
    }

    override fun onNewLine() {
        onPrint("\n")
    }

}
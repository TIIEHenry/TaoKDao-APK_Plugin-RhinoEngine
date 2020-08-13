package taokdao.plugins.engine.rhino.builder.filebuilders


import taokdao.api.builder.IBuildOption
import taokdao.api.file.build.IFileBuilder
import taokdao.api.main.IMainContext
import taokdao.plugins.engine.rhino.PluginConstant
import taokdao.plugins.engine.rhino.controller.TabToolController
import java.io.File

class RhinoBuilder(
    main: IMainContext,
    private val tabToolController: TabToolController
) : IFileBuilder {
    override fun id() = PluginConstant.FileBuilder.RHINO_RUNNER
    override fun getLabel(): String {
        return "Rhino Runner"
    }

    private val runWithRhino = RunWithRhino(tabToolController)
    private val runWithRhinoOnNewActivity = RunWithRhinoOnNewActivity()


    private val buildOptionList = mutableListOf(runWithRhino, runWithRhinoOnNewActivity)

    override fun getBuildOptionList(): MutableList<IBuildOption<File>> {
        return buildOptionList
    }

    override fun getSuffixes(): MutableList<String> {
        return mutableListOf("js", "jsx")
    }

}
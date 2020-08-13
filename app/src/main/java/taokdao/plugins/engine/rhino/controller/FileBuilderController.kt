package taokdao.plugin.engines.rhino.controller

import taokdao.api.file.build.FileBuilderPool
import taokdao.api.main.IMainContext
import taokdao.api.plugin.bean.PluginManifest
import taokdao.plugin.engines.rhino.builder.filebuilders.RhinoBuilder
import taokdao.plugins.engine.rhino.controller.TabToolController

class FileBuilderController(private val tabToolController: TabToolController) {
    private lateinit var rhinoBuilder: RhinoBuilder
    fun onInit(iMainContext: IMainContext, pluginManifest: PluginManifest) {
        rhinoBuilder = RhinoBuilder(iMainContext,tabToolController)
        FileBuilderPool.getInstance().add(rhinoBuilder)
    }

    fun onDestroy(iMainContext: IMainContext, pluginManifest: PluginManifest) {
        FileBuilderPool.getInstance().add(rhinoBuilder)
    }
}
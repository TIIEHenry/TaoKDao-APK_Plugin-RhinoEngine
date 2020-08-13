package taokdao.plugins.engine.rhino.controller

import taokdao.api.file.build.FileBuilderPool
import taokdao.api.main.IMainContext
import taokdao.api.plugin.bean.PluginManifest
import taokdao.api.plugin.entrance.BaseDynamicPluginEntrance
import taokdao.plugins.engine.rhino.builder.filebuilders.RhinoBuilder

class FileBuilderController(private val tabToolController: TabToolController) :
    BaseDynamicPluginEntrance() {
    private lateinit var rhinoBuilder: RhinoBuilder
    override fun onInit(iMainContext: IMainContext, pluginManifest: PluginManifest) {
        rhinoBuilder = RhinoBuilder(iMainContext, tabToolController)
        FileBuilderPool.getInstance().add(rhinoBuilder)
    }

    override fun onDestroy(iMainContext: IMainContext, pluginManifest: PluginManifest) {
        FileBuilderPool.getInstance().add(rhinoBuilder)
    }
}
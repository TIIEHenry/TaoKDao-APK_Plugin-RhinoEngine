package taokdao.plugin.engines.rhino.controller

import taokdao.api.main.IMainContext
import taokdao.api.plugin.bean.PluginManifest
import taokdao.api.plugin.engine.PluginEnginePool
import taokdao.plugin.engines.rhino.engine.RhinoPluginEngine

class EngineController {

   private lateinit var rhinoPluginEngine: RhinoPluginEngine

    fun onCreate(iMainContext: IMainContext, pluginManifest: PluginManifest) {
        rhinoPluginEngine = RhinoPluginEngine(iMainContext, pluginManifest.pluginDir)
        PluginEnginePool.getInstance().add(rhinoPluginEngine)
    }

    fun onDestroy(iMainContext: IMainContext, pluginManifest: PluginManifest) {
        PluginEnginePool.getInstance().remove(rhinoPluginEngine);
    }

    fun getEngine(): RhinoPluginEngine {
        return rhinoPluginEngine
    }
}
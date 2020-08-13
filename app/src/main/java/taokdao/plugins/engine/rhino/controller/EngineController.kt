package taokdao.plugins.engine.rhino.controller

import taokdao.api.main.IMainContext
import taokdao.api.plugin.bean.PluginManifest
import taokdao.api.plugin.engine.PluginEnginePool
import taokdao.api.plugin.entrance.BaseDynamicPluginEntrance
import taokdao.plugins.engine.rhino.engine.RhinoPluginEngine

class EngineController:BaseDynamicPluginEntrance() {

    private lateinit var rhinoPluginEngine: RhinoPluginEngine

    override fun onCreate(iMainContext: IMainContext, pluginManifest: PluginManifest) {
        rhinoPluginEngine = RhinoPluginEngine(iMainContext, pluginManifest.pluginDir)
        PluginEnginePool.getInstance().add(rhinoPluginEngine)
    }

    override fun onDestroy(iMainContext: IMainContext, pluginManifest: PluginManifest) {
        PluginEnginePool.getInstance().remove(rhinoPluginEngine)
    }

    fun getEngine(): RhinoPluginEngine {
        return rhinoPluginEngine
    }
}
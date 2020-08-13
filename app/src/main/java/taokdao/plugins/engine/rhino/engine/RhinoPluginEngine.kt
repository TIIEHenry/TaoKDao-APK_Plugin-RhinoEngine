package taokdao.plugin.engines.rhino.engine


import dalvik.system.DexClassLoader
import taokdao.api.data.bean.Properties
import taokdao.api.internal.InnerIdentifier
import taokdao.api.main.IMainContext
import taokdao.api.plugin.bean.Plugin
import taokdao.api.plugin.bean.PluginActions
import taokdao.api.plugin.bridge.invoke.IInvokeCallback
import taokdao.api.plugin.engine.wrapped.PluginEngine
import taokdao.plugins.engine.rhino.AConstant
import tiiehenry.script.rhino.RhinoEngine
import tiiehenry.script.rhino.RhinoEngineFactory
import tiiehenry.script.wrapper.engine.ScriptEngineManager
import java.io.File

open class RhinoPluginEngine(
    private val main: IMainContext,
    private val pluginDir: File
) :
    PluginEngine(Properties(AConstant.PluginEngine.RHINO, "Rhino")) {

    private lateinit var pluginDexLoader: PluginDexLoader
    private val pluginEnvList = HashMap<Plugin, RhinoEngine>()

    fun getSourceDir(): String = File(pluginDir, "js").absolutePath

    override fun onCreateEngine() {
        ScriptEngineManager.addEngineFactory(RhinoEngineFactory())

        pluginDexLoader = PluginDexLoader(main)
        RhinoEngine.initGlobalClassloader(main.dexLoader.classLoader)

    }

    override fun onDestroyEngine() {
        pluginEnvList.values.forEach {
            it.destroy()
        }
    }

    override fun invokePlugin(plugin: Plugin, method: String, params: String?, invokeCallback: IInvokeCallback?): String? {
        return getPluginModuleEnv(plugin)?.funcBridge?.get("onInvoke")?.call(main, plugin.manifest,method, params,invokeCallback)?.getString()
    }

    override fun callPluginAction(plugin: Plugin, action: PluginActions) {
        when (action) {
            PluginActions.onUpGrade -> callOnUpGrade(plugin)
            PluginActions.onDownGrade -> callOnDownGrade(plugin)
            PluginActions.onCreate -> callOnCreate(plugin)
            PluginActions.onDestroy -> callOnDestroy(plugin)
            PluginActions.onInit -> callOnInit(plugin)
            PluginActions.onCall -> callOnCall(plugin)
            PluginActions.onPause -> callOnPause(plugin)
            PluginActions.onResume -> callOnResume(plugin)
        }
    }

    override fun onInstallPlugin(plugin: Plugin) {
        runPluginFunc("onInstallPlugin", plugin)
    }

    override fun onUninstallPlugin(plugin: Plugin) {
        runPluginFunc("onUninstallPlugin", plugin)
    }


    private fun callOnUpGrade(plugin: Plugin) {
        runPluginFunc("onUpGrade", plugin)
    }

    private fun callOnDownGrade(plugin: Plugin) {
        runPluginFunc("onDownGrade", plugin)
    }

    private fun callOnCreate(plugin: Plugin) {
        val file = File(plugin.pluginDir, plugin.engine.entrance)
        if (file.isFile) {
            val dexLoaderMap = pluginDexLoader.loadPluginDex(plugin)
            val loadedMap = mutableMapOf<String, DexClassLoader>()
            for (mutableEntry in dexLoaderMap) {
                val path = mutableEntry.key.absolutePath
                val n = path.substring(plugin.pluginDir.absolutePath.length)
//                loge(n)
                loadedMap[n] = mutableEntry.value
            }
            main.runOnMain {
                newPluginModuleEnv(plugin).apply {
                    stringEvaluator.eval("var api=Packages.tiiehenry.taokdao.api;")
                    varBridge.set("loadedDexMap", loadedMap)
                    varBridge.set("context", main)
                    fileEvaluator.eval(file)
//                loge("dexLoaderMap" + dexLoaderMap.toString())
                    setPluginModuleEnv(plugin, this)
                }
                runPluginFunc("onCreate", plugin)
            }
        }
    }


    private fun callOnInit(plugin: Plugin) {
        runPluginFunc("onInit", plugin)
    }

    private fun callOnCall(plugin: Plugin) {
        runPluginFunc("onCall", plugin)
    }

    private fun callOnPause(plugin: Plugin) {
        runPluginFunc("onPause", plugin)
    }

    private fun callOnResume(plugin: Plugin) {
        runPluginFunc("onResume", plugin)
    }

    private fun callOnDestroy(plugin: Plugin) {
        runPluginFunc("onDestroy", plugin)
        removePluginModuleEnv(plugin)
    }

    private fun runPluginFunc(funcName: String, plugin: Plugin) {
        getPluginModuleEnv(plugin)?.funcBridge?.get(funcName)?.call(main, plugin.manifest)
    }


    private fun newPluginModuleEnv(id: Plugin): RhinoEngine {
        return RhinoEngineFactory().newScriptEngine().apply {
            create()

            requirer.findPathList.add(getSourceDir())
        }
    }

    private fun getPluginModuleEnv(id: Plugin): RhinoEngine? {
        return pluginEnvList[id]
    }

    private fun setPluginModuleEnv(id: Plugin, env: RhinoEngine) {
        pluginEnvList[id] = env
    }

    private fun removePluginModuleEnv(id: Plugin) {
        pluginEnvList.remove(id)
    }

}
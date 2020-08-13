package taokdao.plugin.engines.rhino.engine


import dalvik.system.DexClassLoader
import taokdao.api.main.IMainContext
import taokdao.api.plugin.bean.Plugin
import java.io.File

/**
 * 自动加载插件文件夹下的classes?.dex
 */
class PluginDexLoader(val main: IMainContext) {

    val loadedDexList = mutableListOf<String>()


    fun loadPluginDex(pluginManifest: Plugin): MutableMap<File, DexClassLoader> {
        return loadPluginDex(pluginManifest.pluginDir)
    }

    fun loadPluginDex(pluginModuleDir: File): MutableMap<File, DexClassLoader> {
        val loaderMap = mutableMapOf<File, DexClassLoader>()
        File(pluginModuleDir, "classes.dex").let {
            if (it.exists()) {
                main.dexLoader.loadDexFile(it)?.let { dexClassLoader ->
                    loadedDexList.add(it.absolutePath)
                    loaderMap[it] = dexClassLoader
                }
            }
        }
        for (i in 1..20) {
            val file = File(pluginModuleDir, "classes$i.dex")
            if (file.exists()) {
                main.dexLoader.loadDexFile(file)?.let {
                    loadedDexList.add(file.absolutePath)
                    loaderMap[file] = it
                }
            } else {
                break
            }
        }
        File(pluginModuleDir, "dex").let { file ->
            if (file.isDirectory) {
                file.listFiles()?.forEach { file1 ->
                    if (file1.isFile && file1.extension == "dex") {
                        main.dexLoader.loadDexFile(file)?.let {
                            loadedDexList.add(file.absolutePath)
                            loaderMap[file] = it
                        }
                    }
                }
            }
        }
        return loaderMap
    }

}
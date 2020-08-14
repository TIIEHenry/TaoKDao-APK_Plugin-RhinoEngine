package taokdao.plugins.engine.rhino.controller

import taokdao.api.data.bean.Properties
import taokdao.api.main.IMainContext
import taokdao.api.main.action.MainAction
import taokdao.api.plugin.bean.PluginManifest
import taokdao.api.plugin.entrance.BaseDynamicPluginEntrance
import taokdao.api.template.project.ProjectTemplatePool
import taokdao.api.template.project.wrapped.ProjectTemplate
import taokdao.plugins.engine.rhino.PluginConstant
import taokdao.plugins.engine.rhino.R
import taokdao.plugins.setup.io.Filej
import tiiehenry.android.ui.dialogs.api.callback.InputCallback
import tiiehenry.android.ui.dialogs.api.strategy.Dialogs
import java.io.File
import java.io.IOException

class ProjectTemplateController: BaseDynamicPluginEntrance() {


    override fun onInit(iMainContext: IMainContext, pluginManifest: PluginManifest) {

    }

    override fun onDestroy(iMainContext: IMainContext, pluginManifest: PluginManifest) {
    }

}
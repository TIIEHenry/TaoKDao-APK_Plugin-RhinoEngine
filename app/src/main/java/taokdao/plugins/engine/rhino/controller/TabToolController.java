package taokdao.plugins.engine.rhino.controller;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import taokdao.api.main.IMainContext;
import taokdao.api.plugin.bean.PluginManifest;
import taokdao.api.plugin.entrance.BaseDynamicPluginEntrance;
import taokdao.plugins.engine.rhino.tabtool.RhinoTabTool;

public class TabToolController extends BaseDynamicPluginEntrance {
    public RhinoTabTool rhinoTabTool;

    public void onInit(@NonNull final IMainContext iMainContext, @NotNull final PluginManifest pluginManifest) {
        rhinoTabTool = new RhinoTabTool(pluginContext, iMainContext);

        iMainContext.getToolPageWindow().add(rhinoTabTool, false);
    }

    public void onDestroy(@NonNull IMainContext iMainContext, @NotNull PluginManifest pluginManifest) {
        boolean removed = iMainContext.getToolPageWindow().remove(rhinoTabTool);
    }
}

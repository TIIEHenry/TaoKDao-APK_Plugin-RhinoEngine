package taokdao.plugins.engine.rhino.controller;

import android.content.Context;

import androidx.annotation.NonNull;

import taokdao.api.main.IMainContext;
import taokdao.api.plugin.bean.PluginManifest;
import taokdao.api.ui.window.tabtool.ITabToolWindow;
import taokdao.plugin.engines.rhino.tabtool.RhinoTabTool;

public class TabToolController {
    private Context pluginContext;
    public RhinoTabTool rhinoTabTool;

    public void onAttach(@NonNull Context context) {
        pluginContext = context;
    }

    public void onInit(@NonNull final IMainContext iMainContext, final PluginManifest pluginManifest) {
        rhinoTabTool = new RhinoTabTool(pluginContext, iMainContext);

        iMainContext.getTabToolWindow().add(rhinoTabTool, false);
    }

    public void onDestroy(@NonNull IMainContext iMainContext, PluginManifest pluginManifest) {
        ITabToolWindow window = iMainContext.getTabToolWindow();
        boolean removed = window.remove(rhinoTabTool);
    }
}

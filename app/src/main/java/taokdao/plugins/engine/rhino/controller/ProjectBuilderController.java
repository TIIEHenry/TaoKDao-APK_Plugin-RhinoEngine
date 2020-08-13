package taokdao.plugins.engine.rhino.controller;

import androidx.annotation.NonNull;

import taokdao.api.main.IMainContext;
import taokdao.api.plugin.bean.PluginManifest;
import taokdao.api.project.build.ProjectBuilderPool;
import taokdao.plugin.engines.rhino.builder.projectbuilders.ScriptProjectTaskBuilder;
import taokdao.plugin.engines.rhino.engine.RhinoPluginEngine;

public class ProjectBuilderController {
    private final TabToolController tabToolController;
    private RhinoPluginEngine rhinoPluginEngine;
    private ScriptProjectTaskBuilder scriptProjectTaskBuilder;

    public ProjectBuilderController(TabToolController tabToolController) {
        this.tabToolController=tabToolController;
    }

    public void attachEngine(RhinoPluginEngine engine) {
        rhinoPluginEngine = engine;
    }

    public void onInit(@NonNull final IMainContext iMainContext, final PluginManifest pluginManifest) {
        scriptProjectTaskBuilder = new ScriptProjectTaskBuilder(rhinoPluginEngine);
        rhinoPluginEngine=null;
        ProjectBuilderPool.getInstance().add(scriptProjectTaskBuilder);
    }

    public void onDestroy(@NonNull IMainContext iMainContext, PluginManifest pluginManifest) {
        ProjectBuilderPool.getInstance().remove(scriptProjectTaskBuilder);
    }
}
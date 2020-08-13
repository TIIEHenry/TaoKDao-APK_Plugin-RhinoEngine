package taokdao.plugins.engine.rhino.controller;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import taokdao.api.main.IMainContext;
import taokdao.api.plugin.bean.PluginManifest;
import taokdao.api.plugin.entrance.BaseDynamicPluginEntrance;
import taokdao.api.project.build.ProjectBuilderPool;
import taokdao.plugins.engine.rhino.builder.projectbuilders.ScriptProjectTaskBuilder;
import taokdao.plugins.engine.rhino.engine.RhinoPluginEngine;

public class ProjectBuilderController extends BaseDynamicPluginEntrance {
    private final TabToolController tabToolController;
    private RhinoPluginEngine rhinoPluginEngine;
    private ScriptProjectTaskBuilder scriptProjectTaskBuilder;

    public ProjectBuilderController(TabToolController tabToolController) {
        this.tabToolController = tabToolController;
    }

    public void attachEngine(RhinoPluginEngine engine) {
        rhinoPluginEngine = engine;
    }

    public void onInit(@NonNull final IMainContext iMainContext, @NotNull final PluginManifest pluginManifest) {
        scriptProjectTaskBuilder = new ScriptProjectTaskBuilder(rhinoPluginEngine);
        rhinoPluginEngine = null;
        ProjectBuilderPool.getInstance().add(scriptProjectTaskBuilder);
    }

    public void onDestroy(@NonNull IMainContext iMainContext, @NotNull PluginManifest pluginManifest) {
        ProjectBuilderPool.getInstance().remove(scriptProjectTaskBuilder);
    }
}
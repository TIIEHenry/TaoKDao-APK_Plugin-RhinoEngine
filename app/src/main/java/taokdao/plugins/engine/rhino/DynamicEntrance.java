package taokdao.plugins.engine.rhino;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import taokdao.api.main.IMainContext;
import taokdao.api.plugin.bean.PluginManifest;
import taokdao.api.plugin.entrance.BaseDynamicPluginEntrance;
import taokdao.plugins.engine.rhino.controller.EngineController;
import taokdao.plugins.engine.rhino.controller.FileBuilderController;
import taokdao.plugins.engine.rhino.controller.ProjectBuilderController;
import taokdao.plugins.engine.rhino.controller.ProjectTemplateController;
import taokdao.plugins.engine.rhino.controller.TabToolController;

@Keep
public class DynamicEntrance extends BaseDynamicPluginEntrance {

    private EngineController engineController = new EngineController();
    private ProjectTemplateController projectTemplateController = new ProjectTemplateController();
    private TabToolController tabToolController = new TabToolController();
    private FileBuilderController fileBuilderController = new FileBuilderController(tabToolController);
    private ProjectBuilderController projectBuilderController = new ProjectBuilderController(tabToolController);

    @Override
    public void onAttach(@NonNull Context pluginContext) {
        tabToolController.onAttach(pluginContext);
        projectTemplateController.onAttach(pluginContext);
    }

    @Override
    public void onUpGrade(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {

    }

    @Override
    public void onDownGrade(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {

    }

    @Override
    public void onCreate(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {
        engineController.onCreate(iMainContext, pluginManifest);
        projectBuilderController.attachEngine(engineController.getEngine());
    }

    @Override
    public void onInit(@NonNull final IMainContext iMainContext, @NonNull final PluginManifest pluginManifest) {
        tabToolController.onInit(iMainContext, pluginManifest);
        fileBuilderController.onInit(iMainContext, pluginManifest);
        projectBuilderController.onInit(iMainContext, pluginManifest);
        projectTemplateController.onInit(iMainContext, pluginManifest);
    }

    @Override
    public void onCall(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {

    }

    @Override
    public void onResume(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {

    }

    @Override
    public void onPause(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {

    }

    @Override
    public void onDestroy(@NonNull IMainContext iMainContext, @NonNull PluginManifest pluginManifest) {
        engineController.onDestroy(iMainContext, pluginManifest);
        fileBuilderController.onDestroy(iMainContext, pluginManifest);
        projectBuilderController.onDestroy(iMainContext, pluginManifest);
        projectTemplateController.onDestroy(iMainContext, pluginManifest);
        tabToolController.onDestroy(iMainContext, pluginManifest);
    }


}
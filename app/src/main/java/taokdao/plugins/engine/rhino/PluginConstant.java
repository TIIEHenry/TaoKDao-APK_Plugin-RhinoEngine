package taokdao.plugins.engine.rhino;

import androidx.annotation.Keep;


@Keep
public class PluginConstant {
    public static String Project_Template_ID = PluginConstant.class.getPackage().getName();

    public static class PluginEngine {
        public static final String RHINO = "taokdao.plugins.engine.js.rhino";
    }

    public static class FileBuilder {
        public static final String RHINO_RUNNER = "taokdao.plugins.engine.rhino.file.builder.rhino.runner";
    }
}

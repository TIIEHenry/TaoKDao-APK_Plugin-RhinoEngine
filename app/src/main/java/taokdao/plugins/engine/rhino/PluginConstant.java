package taokdao.plugins.engine.rhino;

import androidx.annotation.Keep;


@Keep
public class PluginConstant {

    public static class PluginEngine {
        public static final String RHINO = "taokdao.plugins.engine.js.rhino";
    }

    public static class FileBuilder {
        public static final String RHINO_RUNNER = "taokdao.file.builder.rhino.runner";
    }

    public static class ProjectBuilder {
        public static final String RHINO_SCRIPT_TASK = "taokdao.project.builder.rhino.script.task";
        public static final String RHINO_SCRIPT_TASK_RUN = "taokdao.project.builder.rhino.script.task.run";
        public static final String RHINO_SCRIPT_TASK_BUILD = "taokdao.project.builder.rhino.script.task.build";
    }
    public static class ProjectTemplate {
//        public static final String BUILD = "taokdao.project.builder.rhino.script.task.build";
    }
}

package indi.jl.sp.gradle.plugin

import indi.jl.sp.gradle.plugin.task.GeneratorModel
import indi.jl.sp.gradle.plugin.task.module.InitModuleDir
import indi.jl.sp.gradle.plugin.task.module.InitModuleFile
import indi.jl.sp.gradle.plugin.task.project.InitJpaProjectDir
import indi.jl.sp.gradle.plugin.task.project.InitJpaProjectFile
import indi.jl.sp.gradle.plugin.task.project.InitProjectDir
import indi.jl.sp.gradle.plugin.task.project.InitProjectFile
import indi.jl.sp.gradle.plugin.task.querydsl.CleanQuerydslSourcesDir
import indi.jl.sp.gradle.plugin.task.querydsl.InitQuerydslSourcesDir
import indi.jl.sp.gradle.plugin.task.querydsl.QuerydslCompile
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.plugins.JavaPlugin

class GeneratorPlugin implements Plugin<Project> {

    public static final String TASK_GROUP = "generator"

    public static final String ORDER_GROUP = "order"

    private static final Logger LOG = Logging.getLogger(GeneratorPlugin.class)

    @Override
    void apply(final Project project) {
        LOG.info("Applying Querydsl plugin")

        // do nothing if plugin is already applied
        if (project.plugins.hasPlugin(GeneratorPlugin.class)) {
            return
        }

        LOG.info("Applying querydsl plugin")

        // apply core 'java' plugin if not present to make 'sourceSets' available
        if (!project.plugins.hasPlugin(JavaPlugin.class)) {
            project.plugins.apply(JavaPlugin.class)
        }

        // add 'Querydsl' DSL extension
        project.extensions.create(QuerydslPluginExtension.NAME, QuerydslPluginExtension)

        // add new tasks for creating/cleaning the auto-value sources dir
        //生成模块基础信息
        project.task(type: InitModuleDir, "initModuleDir")
        project.task(type: InitModuleFile, "initModuleFile")

        project.tasks.initModuleFile.dependsOn project.tasks.initModuleDir

        //生成项目信息
        project.task(type: InitProjectDir, "initProjectDir")
        project.task(type: InitProjectFile, "initProjectFile")
        project.tasks.initProjectDir.dependsOn project.tasks.initModuleFile
        project.tasks.initProjectFile.dependsOn project.tasks.initProjectDir

        //集成querydsl
        project.task(type: CleanQuerydslSourcesDir, "cleanQuerydslSourcesDir")
        project.task(type: InitQuerydslSourcesDir, "initQuerydslSourcesDir")
        project.task(type: QuerydslCompile, "buildQueryDsl")
        project.tasks.buildQueryDsl.dependsOn project.tasks.initQuerydslSourcesDir

        //jpa
        project.task(type: InitJpaProjectDir, "initJpaProjectDir")
        project.tasks.initJpaProjectDir.dependsOn project.tasks.initProjectDir.doLast() {}
        project.task(type: InitJpaProjectFile, "initJpaProjectFile")
        project.tasks.initJpaProjectFile.dependsOn project.tasks.initJpaProjectDir

        //总体
        project.task(type: GeneratorModel, "generate")
        project.tasks.generate.dependsOn project.tasks.initJpaProjectFile

       project.tasks.compileJava.dependsOn project.tasks.buildQueryDsl

        project.afterEvaluate {
            File querydslSourcesDir = querydslSourcesDir(project)
            addLibrary(project)
            addSourceSet(project, querydslSourcesDir)
            registerSourceAtCompileJava(project, querydslSourcesDir)
            applyCompilerOptions(project)
        }
    }

    private static void applyCompilerOptions(Project project) {
        project.tasks.buildQueryDsl.options.annotationProcessorPath = project.configurations.generator
        project.tasks.buildQueryDsl.options.compilerArgs += [
                "-proc:only",
                "-processor", project.generator.processors()
        ]

        if (project.generator.aptOptions.size() > 0) {
            for (aptOption in project.generator.aptOptions) {
                project.tasks.buildQueryDsl.options.compilerArgs << "-A" + aptOption
            }
        }
    }

    private void registerSourceAtCompileJava(Project project, File querydslSourcesDir) {
        project.compileJava {
            source querydslSourcesDir
        }
    }

    private void addLibrary(Project project) {
        def library = project.extensions.generator.library
        LOG.info("Querydsl library: {}", library)
        project.dependencies {
            compile library
        }
    }

    private void addSourceSet(Project project, File sourcesDir) {
        LOG.info("Create source set 'querydsl'.")

        project.sourceSets {
            generator {
                java.srcDirs = [sourcesDir]
            }
        }
    }

    private static File querydslSourcesDir(Project project) {
        String path = project.extensions.generator.querydslSourcesDir
        File querydslSourcesDir = project.file(path)
        LOG.info("Querydsl sources dir: {}", querydslSourcesDir.absolutePath)
        return querydslSourcesDir
    }
}

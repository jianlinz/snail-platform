package indi.jl.sp.gradle.plugin.task.querydsl

import indi.jl.sp.gradle.plugin.GeneratorPlugin
import org.gradle.api.plugins.WarPlugin
import org.gradle.api.tasks.compile.JavaCompile

class QuerydslCompile extends JavaCompile {

    QuerydslCompile() {
        this.group = GeneratorPlugin.TASK_GROUP
        setSource(project.sourceSets.main.java)

        if (project.plugins.hasPlugin(WarPlugin.class)) {
            project.configurations {
                generator.extendsFrom compile, providedRuntime, providedCompile
            }
        } else {
            project.configurations {
                generator.extendsFrom compileClasspath
            }
        }

        project.afterEvaluate {
            setClasspath(project.configurations.generator)
            File file = project.file(project.generator.querydslSourcesDir)
            setDestinationDir(file)
        }
    }
}

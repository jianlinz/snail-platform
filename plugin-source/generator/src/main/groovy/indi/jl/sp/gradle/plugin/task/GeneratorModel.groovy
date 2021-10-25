package indi.jl.sp.gradle.plugin.task

import indi.jl.sp.gradle.plugin.GeneratorPlugin
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class GeneratorModel extends DefaultTask{

    static final String DESCRIPTION = "生成整个模块"

    GeneratorModel() {
        this.group = GeneratorPlugin.TASK_GROUP
        this.description = DESCRIPTION
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    @TaskAction
    generator() {
       print "================================================开始生成代码================================================"
    }

}

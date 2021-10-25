package indi.jl.sp.gradle.plugin.task.project

import indi.jl.sp.gradle.plugin.GeneratorPlugin
import org.gradle.api.tasks.TaskAction

class InitProjectDir extends BaseInitProject {

    static final String DESCRIPTION = "生成项目文件夹"

    InitProjectDir() {
        this.group = GeneratorPlugin.ORDER_GROUP
        this.description = DESCRIPTION
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    @TaskAction
    createSourceFolders() {

    }

}

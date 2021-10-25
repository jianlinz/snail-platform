package indi.jl.sp.gradle.plugin.task.project

import indi.jl.sp.gradle.plugin.GeneratorPlugin
import indi.jl.sp.gradle.plugin.enums.BuildType
import org.gradle.api.tasks.TaskAction

class InitProjectFile extends BaseInitProject {

    static final String DESCRIPTION = "生成项目文件"

    InitProjectFile() {
        this.group = GeneratorPlugin.ORDER_GROUP
        this.description = DESCRIPTION
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    @TaskAction
    createSourceFolders() {
        switch (project.generator.buildType) {
            case BuildType.JPA:
                break
            case BuildType.MONGODB:
                break
            default:
                throw new RuntimeException("buildType not support")
        }
    }

}

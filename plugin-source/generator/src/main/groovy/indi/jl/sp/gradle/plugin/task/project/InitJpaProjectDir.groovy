package indi.jl.sp.gradle.plugin.task.project

import indi.jl.sp.gradle.plugin.GeneratorPlugin
import org.gradle.api.tasks.TaskAction

class InitJpaProjectDir extends BaseInitJpaProject {

    static final String DESCRIPTION = "生成项目文件夹"

    InitJpaProjectDir() {
        this.group = GeneratorPlugin.ORDER_GROUP
        this.description = DESCRIPTION
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    @TaskAction
    createSourceFolders() {
        if (null != project.generator.subModuleName) {
            return
        }
        mkDir(getProjectMainPath())
        mkDir(getProjectDomainPath())
        mkDir(getProjectDaoPath())
        mkDir(getProjectRepositoryPath())
        mkDir(getProjectServicePath())
        mkDir(getProjectImplPath())
        mkDir(getProjectControllerPath())
        mkDir(getProjectTestPath())
    }

}

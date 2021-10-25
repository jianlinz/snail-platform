package indi.jl.sp.gradle.plugin.task.module

import indi.jl.sp.gradle.plugin.GeneratorPlugin
import org.gradle.api.tasks.TaskAction

class InitModuleDir extends BaseInitModule {

    static final String DESCRIPTION = "生成模块文件夹"

    InitModuleDir() {

        this.group = GeneratorPlugin.ORDER_GROUP
        this.description = DESCRIPTION
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    @TaskAction
    createSourceFolders() {
        if (null != project.generator.subModuleName) {
            return
        }
        def modelBasePath = getModuleBasePath()
        mkModuleBasePath(modelBasePath)
        mkGradleBaseSourcePath()
    }

    /**
     * 构建模块根路径
     * @param moduleBasePath
     * @return 模块根路径
     */
    def mkModuleBasePath(String moduleBasePath) {
        mkDir(moduleBasePath)
    }

    /**
     * 生成 gradle标准文件夹
     * @param moduleBasePath
     */
    def mkGradleBaseSourcePath() {
        mkDir(getGradleSrc())

        mkDir(getGradleMainSrc())
        mkDir(getGradleMainJavaSrc())
        mkDir(getGradleMainResourceSrc())

        mkDir(getGradleTestSrc())
        mkDir(getGradleTestJavaSrc())
        mkDir(getGradleTestResourceSrc())
    }


}

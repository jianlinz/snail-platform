package indi.jl.sp.gradle.plugin.task.module

import indi.jl.sp.gradle.plugin.GeneratorPlugin
import org.gradle.api.tasks.TaskAction

class InitModuleFile extends BaseInitModule {

    static final String DESCRIPTION = "生成模块文件"

    InitModuleFile() {
        this.group = GeneratorPlugin.ORDER_GROUP
        this.description = DESCRIPTION
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    @TaskAction
    createSourceFiles() {
        if (null != project.generator.subModuleName) {
            return
        }
        if (project.file("${getModuleBasePath()}/${getModuleName()}.gradle").exists()) {
            throw new RuntimeException("当前模块已存在")
        }
        mkBuildGradle()
        mkReadME()
    }


    def mkBuildGradle() {
        if(!project.generator.isSubImport){
            project.file("${getModuleBasePath()}/${getModuleName()}.gradle").withWriter {
                it.write("""dependencies {

        api project(':sp-data-jpa')

        testImplementation project(':sp-test')

}""")
            }
        }else{
            project.file("${getModuleBasePath()}/${getModuleName()}.gradle").withWriter {
                it.write("""dependencies {

        api lib.sp_data_jpa

        testImplementation lib.sp_test

}""")
            }
        }
    }

    def mkReadME() {
        project.file("${getModuleBasePath()}/README.md").withWriter {
            it.write("""${getModuleName()}
${"=" * getModuleName().length()}""")
        }
    }

}

package indi.jl.sp.gradle.plugin.task.project

import indi.jl.sp.gradle.plugin.task.module.BaseInitModule
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

class BaseInitProject extends BaseInitModule {

    private static final Logger LOG = Logging.getLogger(BaseInitProject.class)

    def getProjectPackage() {
        return project.generator.projectBasePackage + "." + buildModuleNameToPackage(getModuleName())
    }

    def getProjectMainPath() {
        return getGradleMainJavaSrc() + File.separator + buildPackageToPath(getProjectPackage())
    }

    def getProjectTestPath() {
        return getGradleTestJavaSrc() + File.separator + buildPackageToPath(getProjectPackage())
    }

    def getProjectControllerPath() {
        return getProjectMainPath() + File.separator + "controller"
    }

    def getProjectControllerPackage() {
        return getProjectPackage() + ".controller"
    }

    def getProjectControllerName() {
        return getJavaModuleName() + "Controller"
    }

    def getProjectServicePath() {
        return getProjectMainPath() + File.separator + "service"
    }

    def getProjectServicePackage() {
        return getProjectPackage() + ".service"
    }

    def getProjectServiceName() {
        return getJavaModuleName() + "Service"
    }

    def getProjectImplPath() {
        return getProjectServicePath() + File.separator + "impl"
    }

    def getProjectImplPackage() {
        return getProjectPackage() + ".service.impl"
    }

    def getProjectImplName() {
        return getJavaModuleName() + "ServiceImpl"
    }

    def getJavaModuleName() {
        def nameAttr
        if (null != project.generator.subModuleName) {
            nameAttr = getSubModuleName().split("-")
        } else {
            nameAttr = getModuleName().split("-")
        }
        def javaModule = ""
        for (int i = 0; i < nameAttr.size(); i++) {
            if (i == 0) {
                continue
            }
            javaModule += toFirstUpperCase(nameAttr[i])
        }
        return javaModule
    }

    def getTestPackage() {
        return getProjectPackage()
    }

    def getTestName() {
        return getJavaModuleName() + "Test"
    }

    def getTableName() {
        if (null != getSubModuleName()) {
            return getSubModuleName().toString().replaceAll("-", "_").toLowerCase()
        }
        return getModuleName().toString().replaceAll("-", "_").toLowerCase()
    }

    def fileExist(File file) {
        if (file.exists()) {
            LOG.warn("file：" + file.getAbsolutePath() + " is exist,so we didn't create a new file")
            return true
        }
        return false
    }

}

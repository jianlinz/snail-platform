package indi.jl.sp.gradle.plugin.task.module

import org.gradle.api.DefaultTask

class BaseInitModule extends DefaultTask {
    static final String charset = "UTF-8"

    def getModuleBasePath() {
        def rootDir = getRootDir()
        def baseDir = getBaseDir()
        def parentDir = getParentDir()
        def moduleName = getModuleName()
        return "${rootDir}${File.separator}${baseDir}${File.separator}${parentDir}${File.separator}${moduleName}"
    }

    def getRootDir() {
        return project.rootDir
    }

    def getBaseDir() {
        return project.generator.baseDir
    }

    def getParentDir() {
        return buildParentPathToPath(project.generator.parentPath)
    }

    def getModuleName() {
        return project.generator.moduleName
    }
    def getModuleDesc() {
        return project.generator.moduleDesc
    }

    def getSubModuleName() {
        return project.generator.subModuleName
    }

    def getGradleSrc() {
        return "${getModuleBasePath()}${File.separator}src"
    }

    def getGradleMainSrc() {
        return "${getGradleSrc()}${File.separator}main"
    }

    def getGradleMainJavaSrc() {
        return "${getGradleMainSrc()}${File.separator}java"
    }

    def getGradleMainResourceSrc() {
        return "${getGradleMainSrc()}${File.separator}resources"
    }

    def getGradleTestSrc() {
        return "${getGradleSrc()}${File.separator}test"
    }

    def getGradleTestJavaSrc() {
        return "${getGradleTestSrc()}${File.separator}java"
    }

    def getGradleTestResourceSrc() {
        return "${getGradleTestSrc()}${File.separator}resources"
    }

    def buildParentPathToPath(String parentPath) {
        if (null == parentPath || "".equals(parentPath)) {
            return ""
        }
        def pathAttr = parentPath.split("\\/")
        def filePath = ""
        for (String path : pathAttr) {
            filePath += path + File.separator
        }
        return filePath.substring(0, filePath.length() - 1)
    }


    def buildPackageToPath(String packagePath) {
        if (null == packagePath || "".equals(packagePath)) {
            return ""
        }
        def pathAttr = packagePath.split("\\.")
        def filePath = ""
        for (String path : pathAttr) {
            filePath += path + File.separator
        }
        return filePath.substring(0, filePath.length() - 1)
    }

    def buildModuleNameToPackage(String moduleName) {
        if (null == moduleName || "".equals(moduleName)) {
            throw new RuntimeException("moduleName cant be empty")
        }
        def packagePath = ""
        def packagePathAttr = moduleName.split("-")
        for (String path : packagePathAttr) {
            packagePath += path + "."
        }
        return packagePath.substring(0, packagePath.length() - 1)
    }


    def toFirstUpperCase(str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1)
    }

    def toFirstLowerCase(str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1)
    }

    def toLowerCase(str) {
        return str.toLowerCase()
    }

    /**
     * 生成文件夹
     * @param path 文件夹
     * @return 文件夹
     */
    def mkDir(String path) {
        if (null == path) {
            throw new RuntimeException("path is empty")
        }
        if (project.file(path).exists()) {
            throw new RuntimeException("path:" + path + " is exist")
        }
        project.file(path).mkdirs()
    }

}

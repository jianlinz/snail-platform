package indi.jl.sp.gradle.plugin

import indi.jl.sp.gradle.plugin.enums.BuildType

class GeneratorPluginExtension {

    /**
     * 默认代码地址根路径
     */
    String baseDir = "/subprojects/"

    /**
     * 父模块路径 /分隔  sp-data/sp-aaa/sp-ddd
     */
    String parentPath = ""

    /**
     * 模块名称
     */
    String moduleName

    /**
     * 模块描述
     */
    String moduleDesc

    /**
     * 子模块  场景是与父模块公用文件夹  但是单元测试和增删改查文件均新建
     */
    String subModuleName

    /**
     * 项目构建根包名
     */
    String projectBasePackage = "indi.jl"

    /**
     * 构建类型 默认JPA
     */
    BuildType buildType = BuildType.JPA

    /**
     * 是否为子工程 非sp工程引入
     */
    boolean isSubImport = true

    /**
     * 是否为工具工程  非工具工程会隐藏 生成器 仅保留queryDSl 编译自生成
     */
    boolean isTool = false

}

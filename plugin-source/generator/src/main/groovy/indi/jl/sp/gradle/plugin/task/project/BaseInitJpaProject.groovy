package indi.jl.sp.gradle.plugin.task.project

class BaseInitJpaProject extends BaseInitProject {

    def getProjectDomainPackage() {
        return getProjectPackage() + ".jpa.domain"
    }

    def getProjectDomainPath() {
        return getProjectMainPath() + File.separator + "jpa" + File.separator + "domain"
    }

    def getProjectDomainName() {
        return getJavaModuleName() + "Do"
    }

    def getProjectDomainQName() {
        return "Q" + getProjectDomainName()
    }

    def getProjectDaoPath() {
        return getProjectMainPath() + File.separator + "jpa" + File.separator + "dao"
    }

    def getProjectDaoPackage() {
        return getProjectPackage() + ".jpa.dao"
    }

    def getProjectDaoName() {
        return getJavaModuleName() + "Dao"
    }

    def getProjectRepositoryPath() {
        return getProjectMainPath() + File.separator + "jpa" + File.separator + "repository"
    }

    def getProjectRepositoryPackage() {
        return getProjectPackage() + ".jpa.repository"
    }

    def getProjectRepositoryName() {
        return getJavaModuleName() + "Repository"
    }

    def getProjectConfigurationName() {
        return getJavaModuleName() + "Configuration"
    }
}

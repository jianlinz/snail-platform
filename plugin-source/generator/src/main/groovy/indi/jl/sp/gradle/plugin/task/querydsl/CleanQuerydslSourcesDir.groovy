package indi.jl.sp.gradle.plugin.task.querydsl


import org.gradle.api.DefaultTask
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.TaskAction

class CleanQuerydslSourcesDir extends DefaultTask {

  private static final Logger LOG = Logging.getLogger(CleanQuerydslSourcesDir.class)

  static final String DESCRIPTION = "Cleans the Querydsl sources dir."

  CleanQuerydslSourcesDir() {
    this.description = DESCRIPTION
  }

  @SuppressWarnings("GroovyUnusedDeclaration")
  @TaskAction
  cleanSourceFolders() {
    LOG.info("Clean Querydsl source dir")

    project.sourceSets.generator.java.srcDirs.each { dir ->
      if (dir.exists()) {
        dir.deleteDir()
      }
    }
  }
}

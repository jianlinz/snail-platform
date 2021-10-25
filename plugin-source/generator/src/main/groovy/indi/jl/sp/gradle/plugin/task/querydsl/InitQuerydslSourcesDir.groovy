package indi.jl.sp.gradle.plugin.task.querydsl

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class InitQuerydslSourcesDir extends DefaultTask {

  static final String DESCRIPTION = "Creates the Querydsl sources dir."

  InitQuerydslSourcesDir() {
    this.description = DESCRIPTION
  }

  @SuppressWarnings("GroovyUnusedDeclaration")
  @TaskAction
  createSourceFolders() {
    project.file(project.generator.querydslSourcesDir).mkdirs()
  }
}
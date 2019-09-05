package fr.jmini.htmlchecker.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class HtmlcheckerGradlePlugin implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    project.getTasks().register("htmlchecker", HtmlcheckerTask.class);
  }
}

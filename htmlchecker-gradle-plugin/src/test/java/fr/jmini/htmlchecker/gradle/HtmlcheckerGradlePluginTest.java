package fr.jmini.htmlchecker.gradle;

import static org.junit.Assert.assertNotNull;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Test;

public class HtmlcheckerGradlePluginTest {
  @Test
  public void pluginRegistersATask() {
    // Create a test project and apply the plugin
    Project project = ProjectBuilder.builder().build();
    project.getPlugins().apply("fr.jmini.htmlchecker");

    // Verify the result
    assertNotNull(project.getTasks().findByName("htmlchecker"));
  }
}

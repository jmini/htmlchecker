package fr.jmini.htmlchecker.mvn;

import static io.takari.maven.testing.TestResources.assertFilesPresent;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;

import io.takari.maven.testing.TestMavenRuntime;
import io.takari.maven.testing.TestProperties;
import io.takari.maven.testing.TestResources;

public class HtmlCheckerMojoUnitTest {

  @Rule
  public final TestResources resources = new TestResources("src/test/resources/poms", "build/maven-work");

  @Rule
  public final TestMavenRuntime maven = new TestMavenRuntime();

  private TestProperties properties = new TestProperties();

  @Test
  public void test() throws Exception {
    File basedir = resources.getBasedir("unit");
    maven.executeMojo(basedir, "generate-report", TestMavenRuntime.newParameter("it-plugin.version", properties.getPluginVersion()));

    assertFilesPresent(basedir, "target/report.html");
  }

}

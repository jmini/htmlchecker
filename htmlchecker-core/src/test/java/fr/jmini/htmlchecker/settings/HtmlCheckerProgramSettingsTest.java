package fr.jmini.htmlchecker.settings;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.junit.Test;

public class HtmlCheckerProgramSettingsTest {

  @Test
  public void testGetProgramVersion() throws Exception {
    String version = new HtmlCheckerProgramSettings().getProgramVersion();

    //Read expected version value from 'values.properties' file
    Properties properties = new Properties();
    File file = new File("../gradle.properties");
    try (FileReader reader = new FileReader(file)) {
      properties.load(reader);
    }
    String expectedVersion = properties.getProperty("version").replace("-SNAPSHOT", "");

    assertThat(version).isEqualTo(expectedVersion);
  }
}

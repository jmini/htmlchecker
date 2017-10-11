package fr.jmini.htmlchecker.settings;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.junit.Test;

import com.google.common.io.Resources;

public class HtmlCheckerProgramSettingsTest {

  @Test
  public void testGetProgramVersion() throws Exception {
    String version = new HtmlCheckerProgramSettings().getProgramVersion();

    //Read expected version value from 'values.properties' file
    Properties properties = new Properties();
    File file = new File(Resources.getResource("values.properties").getPath());
    try (FileReader reader = new FileReader(file)) {
      properties.load(reader);
    }
    String expectedVersion = properties.getProperty("version");

    assertThat(version).isEqualTo(expectedVersion);
  }
}

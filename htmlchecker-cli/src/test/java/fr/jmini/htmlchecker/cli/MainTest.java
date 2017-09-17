package fr.jmini.htmlchecker.cli;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.selesse.jxlint.cli.CommandLineOptions;

import fr.jmini.htmlchecker.settings.HtmlCheckerProgramSettings;

public class MainTest {

  @Test
  public void testHelpMessage() throws Exception {
    String helpMessage = CommandLineOptions.getHelpMessage(new HtmlCheckerProgramSettings());

    String expected = Resources.toString(Resources.getResource("htmlchecker-help.txt"), Charsets.UTF_8);
    assertThat(normalizeLineEnds(helpMessage)).isEqualTo(normalizeLineEnds(expected));
  }

  private static String normalizeLineEnds(String s) {
    return s.replace("\r\n", "\n").replace('\r', '\n');
  }
}

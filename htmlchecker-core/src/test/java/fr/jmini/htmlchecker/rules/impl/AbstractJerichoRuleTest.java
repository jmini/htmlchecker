package fr.jmini.htmlchecker.rules.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.io.Resources;
import com.selesse.jxlint.model.rules.LintError;

import fr.jmini.htmlchecker.AbstractHtmlCheckerTest;

public abstract class AbstractJerichoRuleTest<T extends AbstractJerichoRule> extends AbstractHtmlCheckerTest {
  protected static final String SITE_FOLDER = "site/";
  protected static final String PAGE_NAME = "index.html";
  protected T rule;

  @Test
  public void testGetFilesToValidate() throws Exception {
    setupMock();
    List<File> filesToValidate = rule.getFilesToValidate();

    assertThat(filesToValidate).hasSize(2);
    List<String> fileNames = filesToValidate.stream().map(f -> f.getName()).sorted().collect(Collectors.toList());
    assertThat(fileNames).isEqualTo(Arrays.asList(PAGE_NAME, "page1.html"));
  }

  protected static void assertLintError(LintError lintError, String expectedFileName, int expectedLineNumber, String expectedMessage) {
    assertThat(lintError.getFile().getName()).isEqualTo(expectedFileName);
    assertThat(lintError.getLineNumber()).isEqualTo(expectedLineNumber);
    assertThat(lintError.getMessage()).isEqualTo(expectedMessage);
  }

  public void setupMock() {
    rule = Mockito.spy(newRule());

    File sampleTestDirectory = new File(Resources.getResource(SITE_FOLDER).getPath());
    when(rule.getSourceDirectory()).thenReturn(sampleTestDirectory);
  }

  public void setupRule() {
    rule = newRule();
  }

  protected abstract T newRule();

}

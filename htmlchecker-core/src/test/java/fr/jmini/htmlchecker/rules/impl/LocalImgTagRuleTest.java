package fr.jmini.htmlchecker.rules.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.google.common.io.Resources;
import com.selesse.jxlint.model.rules.LintError;

public class LocalImgTagRuleTest extends AbstractLocalRuleTest<LocalImgTagRule> {

  @Test
  public void testGetLintErrors() throws Exception {
    setupRule();
    File file = new File(SITE_FOLDER + PAGE_NAME);
    List<LintError> lintErrors = rule.getLintErrors(file);

    assertThat(lintErrors).hasSize(1);

    for (LintError lintError : lintErrors) {
      switch (lintError.getLineNumber()) {
        case 23:
          assertLintError(lintError, PAGE_NAME, 23, "File 'img/pict2.png' (relative to 'index.html') referenced by the 'src' attribute in the 'img' tag is missing");
          break;
        default:
          fail("Did not expect LintError " + lintError);
      }
    }
  }

  @Test
  public void testGetLintErrorsWithMalformedImgTags() throws Exception {
    setupRule();
    File file = new File(Resources.getResource("malformed-img.html").getPath());
    List<LintError> lintErrors = rule.getLintErrors(file);
    assertThat(lintErrors).hasSize(0);
  }

  @Test
  public void testGetLintErrorsWithLocalDirectory() throws Exception {
    setupRule();
    File file = new File(Resources.getResource("dir-nok-img.html").getPath());
    List<LintError> lintErrors = rule.getLintErrors(file);
    assertThat(lintErrors).hasSize(2);
  }

  @Test
  public void testGetLintErrorsWithRoot() throws Exception {
    setupRule();
    File file = new File(Resources.getResource("root-img.html").getPath());
    List<LintError> lintErrors = rule.getLintErrors(file);
    assertThat(lintErrors).hasSize(0);
  }

  @Test
  public void testLinkWithWhitespace() throws Exception {
    setupRule();
    File file = new File(Resources.getResource("whitespace-img.html").getPath());
    List<LintError> lintErrors = rule.getLintErrors(file);
    assertThat(lintErrors).hasSize(0);
  }

  @Override
  protected LocalImgTagRule newRule() {
    return new LocalImgTagRule();
  }
}

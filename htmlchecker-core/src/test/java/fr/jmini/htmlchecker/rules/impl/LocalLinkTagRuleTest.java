package fr.jmini.htmlchecker.rules.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.google.common.io.Resources;
import com.selesse.jxlint.model.rules.LintError;

public class LocalLinkTagRuleTest extends AbstractLocalRuleTest<LocalLinkTagRule> {

  @Test
  public void testGetLintErrors() throws Exception {
    setupRule();
    File file = new File(SITE_FOLDER + PAGE_NAME);
    List<LintError> lintErrors = rule.getLintErrors(file);

    assertThat(lintErrors).hasSize(2);

    for (LintError lintError : lintErrors) {
      switch (lintError.getLineNumber()) {
        case 5:
          assertLintError(lintError, PAGE_NAME, 5, "File 'css/style2.css' (relative to 'index.html') referenced by the 'href' attribute in the 'link' tag is missing");
          break;
        case 6:
          assertLintError(lintError, PAGE_NAME, 6, "File 'favicon.ico' (relative to 'index.html') referenced by the 'href' attribute in the 'link' tag is missing");
          break;
        default:
          fail("Did not expect LintError " + lintError);
      }
    }
  }

  @Test
  public void testGetLintErrorsWithMalformedImgTags() throws Exception {
    setupRule();
    File file = new File(Resources.getResource("malformed-link.html").getPath());
    List<LintError> lintErrors = rule.getLintErrors(file);
    assertThat(lintErrors).hasSize(0);
  }

  @Test
  public void testGetLintErrorsWithLocalDirectory() throws Exception {
    setupRule();
    File file = new File(Resources.getResource("dir-nok-link.html").getPath());
    List<LintError> lintErrors = rule.getLintErrors(file);
    assertThat(lintErrors).hasSize(2);
  }

  @Test
  public void testGetLintErrorsWithRoot() throws Exception {
    setupRule();
    File file = new File(Resources.getResource("root-link.html").getPath());
    List<LintError> lintErrors = rule.getLintErrors(file);
    assertThat(lintErrors).hasSize(0);
  }

  @Test
  public void testLinkWithWhitespace() throws Exception {
    setupRule();
    File file = new File(Resources.getResource("whitespace-link.html").getPath());
    List<LintError> lintErrors = rule.getLintErrors(file);
    assertThat(lintErrors).hasSize(0);
  }

  @Override
  protected LocalLinkTagRule newRule() {
    return new LocalLinkTagRule();
  }
}

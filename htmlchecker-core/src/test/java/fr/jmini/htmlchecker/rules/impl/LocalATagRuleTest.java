package fr.jmini.htmlchecker.rules.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.google.common.io.Resources;
import com.selesse.jxlint.model.rules.LintError;

public class LocalATagRuleTest extends AbstractLocalRuleTest<LocalATagRule> {

  @Test
  public void testGetLintErrors() throws Exception {
    setupRule();
    File file = new File(SITE_FOLDER + PAGE_NAME);
    List<LintError> lintErrors = rule.getLintErrors(file);

    assertThat(lintErrors).hasSize(4);

    for (LintError lintError : lintErrors) {
      switch (lintError.getLineNumber()) {
        case 15:
          assertLintError(lintError, PAGE_NAME, 15, "File 'page2.html' (relative to 'index.html') referenced by the 'href' attribute in the 'a' tag is missing");
          break;
        case 17:
          assertLintError(lintError, PAGE_NAME, 17, "Anchor 'p2' referenced in the 'href' attribute in the 'a' tag is missing in file  'index.html'");
          break;
        case 19:
          assertLintError(lintError, PAGE_NAME, 19, "Anchor 'xyz' referenced in the 'href' attribute in the 'a' tag is missing in file  'page1.html' (relative to 'index.html')");
          break;
        case 20:
          assertLintError(lintError, PAGE_NAME, 20, "File 'page2.html' (relative to 'index.html') referenced by the 'href' attribute in the 'a' tag is missing");
          break;
        default:
          fail("Did not expect LintError " + lintError);
      }
    }
  }

  @Test
  public void testGetLintErrorsWithMalformedImgTags() throws Exception {
    setupRule();
    File file = new File(Resources.getResource("malformed-a.html").getPath());
    List<LintError> lintErrors = rule.getLintErrors(file);
    assertThat(lintErrors).hasSize(0);
  }

  @Test
  public void testGetLintErrorsWithLocalDirectory() throws Exception {
    setupRule();
    File file = new File(Resources.getResource("dir-nok-a.html").getPath());
    List<LintError> lintErrors = rule.getLintErrors(file);
    assertThat(lintErrors).hasSize(2);
  }

  @Test
  public void testGetLintErrorsWithLocalDirectoryAndIndexFile() throws Exception {
    setupRule();
    File file = new File(Resources.getResource("dir-ok-a.html").getPath());
    List<LintError> lintErrors = rule.getLintErrors(file);
    assertThat(lintErrors).hasSize(0);
  }

  @Override
  protected LocalATagRule newRule() {
    return new LocalATagRule();
  }
}

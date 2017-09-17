package fr.jmini.htmlchecker.rules.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.google.common.io.Resources;
import com.selesse.jxlint.model.rules.LintError;

public class LocalScriptTagRuleTest extends AbstractLocalRuleTest<LocalScriptTagRule> {

  @Test
  public void testGetLintErrors() throws Exception {
    setupRule();
    File file = new File(SITE_FOLDER + PAGE_NAME);
    List<LintError> lintErrors = rule.getLintErrors(file);

    assertThat(lintErrors).hasSize(1);

    for (LintError lintError : lintErrors) {
      switch (lintError.getLineNumber()) {
        case 8:
          assertLintError(lintError, PAGE_NAME, 8, "File 'js/script2.js' (relative to 'index.html') referenced by the 'src' attribute in the 'script' tag is missing");
          break;
        default:
          fail("Did not expect LintError " + lintError);
      }
    }
  }

  @Test
  public void testGetLintErrorsWithMalformedImgTags() throws Exception {
    setupRule();
    File file = new File(Resources.getResource("malformed-script.html").getPath());
    List<LintError> lintErrors = rule.getLintErrors(file);
    assertThat(lintErrors).hasSize(0);
  }

  @Override
  protected LocalScriptTagRule newRule() {
    return new LocalScriptTagRule();
  }
}

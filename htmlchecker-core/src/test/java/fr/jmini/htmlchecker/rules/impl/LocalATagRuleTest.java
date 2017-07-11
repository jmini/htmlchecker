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
    File file = new File(Resources.getResource(SITE_FOLDER + PAGE_NAME).getPath());
    List<LintError> lintErrors = rule.getLintErrors(file);

    assertThat(lintErrors).hasSize(5);

    for (LintError lintError : lintErrors) {
      switch (lintError.getLineNumber()) {
        case 13:
          assertLintError(lintError, PAGE_NAME, 13, "File 'page2.html' (relative to 'index.html') referenced by the 'href' attribute in the 'a' tag is missing");
          break;
        case 15:
          assertLintError(lintError, PAGE_NAME, 15, "Anchor 'p2' referenced in the 'href' attribute in the 'a' tag is missing in file  'index.html'");
          break;
        case 16:
          assertLintError(lintError, PAGE_NAME, 16, "Anchor 'abc' referenced in the 'href' attribute in the 'a' tag is missing in file  'page1.html' (relative to 'index.html')");
          break;
        case 17:
          assertLintError(lintError, PAGE_NAME, 17, "Anchor 'xyz' referenced in the 'href' attribute in the 'a' tag is missing in file  'page1.html' (relative to 'index.html')");
          break;
        case 18:
          assertLintError(lintError, PAGE_NAME, 18, "File 'page2.html' (relative to 'index.html') referenced by the 'href' attribute in the 'a' tag is missing");
          break;
        default:
          fail("Did not expect LintError " + lintError);
      }
    }
  }

  @Override
  protected LocalATagRule newLocalRule() {
    return new LocalATagRule();
  }
}

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
    File file = new File(Resources.getResource(SITE_FOLDER + PAGE_NAME).getPath());
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

  @Override
  protected LocalLinkTagRule newLocalRule() {
    return new LocalLinkTagRule();
  }

}

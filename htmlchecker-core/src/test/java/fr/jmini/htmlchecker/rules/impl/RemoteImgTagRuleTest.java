package fr.jmini.htmlchecker.rules.impl;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.google.common.io.Resources;
import com.selesse.jxlint.model.rules.LintError;

public class RemoteImgTagRuleTest extends AbstractRemoteRuleTest<RemoteImgTagRule> {

  @Test
  public void testGetLintErrors() throws Exception {
    setupRule();
    File file = new File(Resources.getResource("remote-img.html").getPath());
    List<LintError> lintErrors = rule.getLintErrors(file);

//    assertThat(lintErrors).hasSize(1);
//
//    for (LintError lintError : lintErrors) {
//      switch (lintError.getLineNumber()) {
//        case 23:
//          assertLintError(lintError, PAGE_NAME, 23, "File 'img/pict2.png' (relative to 'index.html') referenced by the 'src' attribute in the 'img' tag is missing");
//          break;
//        default:
//          fail("Did not expect LintError " + lintError);
//      }
//    }
  }

  @Override
  protected RemoteImgTagRule newRule() {
    return new RemoteImgTagRule();
  }
}

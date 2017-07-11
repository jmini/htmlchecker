package fr.jmini.htmlchecker;

import org.junit.Before;

import com.selesse.jxlint.model.rules.LintRulesImpl;

import fr.jmini.htmlchecker.rules.HtmlCheckerRules;

public abstract class AbstractHtmlCheckerTest {
  @Before
  public void setup() {
    LintRulesImpl.setInstance(new HtmlCheckerRules());
  }
}

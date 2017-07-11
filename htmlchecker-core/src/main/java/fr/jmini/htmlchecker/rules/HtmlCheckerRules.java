package fr.jmini.htmlchecker.rules;

import com.selesse.jxlint.model.rules.AbstractLintRules;

import fr.jmini.htmlchecker.rules.impl.LocalImgTagRule;

public class HtmlCheckerRules extends AbstractLintRules {
  @Override
  public void initializeLintRules() {
    lintRules.add(new LocalImgTagRule());
  }
}

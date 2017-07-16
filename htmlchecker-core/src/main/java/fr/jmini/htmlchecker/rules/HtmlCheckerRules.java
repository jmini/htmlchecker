package fr.jmini.htmlchecker.rules;

import com.selesse.jxlint.model.rules.AbstractLintRules;

import fr.jmini.htmlchecker.rules.impl.LocalATagRule;
import fr.jmini.htmlchecker.rules.impl.LocalImgTagRule;
import fr.jmini.htmlchecker.rules.impl.LocalLinkTagRule;
import fr.jmini.htmlchecker.rules.impl.LocalScriptTagRule;
import fr.jmini.htmlchecker.rules.impl.RemoteATagRule;
import fr.jmini.htmlchecker.rules.impl.RemoteImgTagRule;
import fr.jmini.htmlchecker.rules.impl.RemoteLinkTagRule;
import fr.jmini.htmlchecker.rules.impl.RemoteScriptTagRule;

public class HtmlCheckerRules extends AbstractLintRules {
  @Override
  public void initializeLintRules() {
    // local rules:
    lintRules.add(new LocalATagRule());
    lintRules.add(new LocalImgTagRule());
    lintRules.add(new LocalLinkTagRule());
    lintRules.add(new LocalScriptTagRule());

    // remote rules:
    lintRules.add(new RemoteATagRule());
    lintRules.add(new RemoteImgTagRule());
    lintRules.add(new RemoteLinkTagRule());
    lintRules.add(new RemoteScriptTagRule());
  }
}

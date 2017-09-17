package fr.jmini.htmlchecker.rules.impl;

import net.htmlparser.jericho.HTMLElementName;

public class LocalScriptTagRule extends AbstractLocalRule {

  public LocalScriptTagRule() {
    super(HTMLElementName.SCRIPT, "src", "SCRIPT_TAG");
  }
}

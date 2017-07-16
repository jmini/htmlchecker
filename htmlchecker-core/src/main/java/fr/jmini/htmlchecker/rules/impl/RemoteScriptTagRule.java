package fr.jmini.htmlchecker.rules.impl;

import net.htmlparser.jericho.HTMLElementName;

public class RemoteScriptTagRule extends AbstractRemoteRule {

  public RemoteScriptTagRule() {
    super(HTMLElementName.SCRIPT, "src", "SCRIPT_TAG");
  }
}

package fr.jmini.htmlchecker.rules.impl;

import net.htmlparser.jericho.HTMLElementName;

public class RemoteLinkTagRule extends AbstractRemoteRule {

  public RemoteLinkTagRule() {
    super(HTMLElementName.LINK, "href", "IMG_TAG");
  }
}

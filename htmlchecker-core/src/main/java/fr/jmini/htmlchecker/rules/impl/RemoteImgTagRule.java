package fr.jmini.htmlchecker.rules.impl;

import net.htmlparser.jericho.HTMLElementName;

public class RemoteImgTagRule extends AbstractRemoteRule {

  public RemoteImgTagRule() {
    super(HTMLElementName.IMG, "src", "IMG_TAG");
  }
}

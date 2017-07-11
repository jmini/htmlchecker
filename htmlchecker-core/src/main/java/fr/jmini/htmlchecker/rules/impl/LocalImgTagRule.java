package fr.jmini.htmlchecker.rules.impl;

import net.htmlparser.jericho.HTMLElementName;

public class LocalImgTagRule extends AbstractLocalRule {

  public LocalImgTagRule() {
    super(HTMLElementName.IMG, "src", "IMG_TAG");
  }
}

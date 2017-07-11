package fr.jmini.htmlchecker.rules.impl;

import net.htmlparser.jericho.HTMLElementName;

public class LocalLinkTagRule extends AbstractLocalRule {

  public LocalLinkTagRule() {
    super(HTMLElementName.LINK, "href", "IMG_TAG");
  }

//  @Override
//  protected boolean isElementRelevant(Element e) {
//    return e.getAttributeValue("rel");
//  }
}

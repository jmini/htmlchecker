package fr.jmini.htmlchecker;

import java.util.List;
import java.util.Optional;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

public final class JerichoHtmlUtility {

  public static boolean containsElementWithId(Source source, String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("id can not be empty or null");
    }
    List<Element> allElements = source.getAllElements("id", id, true);
    if (!allElements.isEmpty()) {
      return true;
    }

    List<Element> allIdAttributes = source.getAllElements("id", null);
    Optional<Element> anyWithIdValueMatching = allIdAttributes.stream()
        .filter(e -> id.equals(HtmlUtility.urlDecode(e.getAttributeValue("id"))))
        .findAny();
    if (anyWithIdValueMatching.isPresent()) {
      return true;
    }

    List<Element> allATags = source.getAllElements(HTMLElementName.A);
    Optional<Element> anyWithNameAttributeMatching = allATags.stream()
        .filter(e -> id.equals(HtmlUtility.urlDecode(e.getAttributeValue("name"))))
        .findAny();
    return anyWithNameAttributeMatching.isPresent();
  }

  private JerichoHtmlUtility() {
  }
}

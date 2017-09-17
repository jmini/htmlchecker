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
    if (allElements.size() > 0) {
      return true;
    }

    allElements = source.getAllElements(HTMLElementName.A);
    Optional<Element> any = allElements.stream()
        .filter(e -> id.equals(e.getAttributeValue("name")))
        .findAny();
    return any.isPresent();
  }

  private JerichoHtmlUtility() {
  }
}

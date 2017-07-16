package fr.jmini.htmlchecker.rules.impl;

import java.io.File;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selesse.jxlint.model.rules.LintError;

import fr.jmini.htmlchecker.HtmlUtility;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;

public class RemoteATagRule extends AbstractRemoteRule {
  private static final Logger LOG = LoggerFactory.getLogger(RemoteATagRule.class);

  public RemoteATagRule() {
    super(HTMLElementName.A, "href", "A_TAG");
  }

  @Override
  protected String computeRemotePath(Element element) {
    String path = super.computeRemotePath(element);
    return HtmlUtility.removeAnchor(path);
  }

  @Override
  protected Optional<LintError> checkAndcreateLintError(File file, Element element) {
    Optional<LintError> error = super.checkAndcreateLintError(file, element);
    if (error.isPresent()) {
      return error;
    }

    String href = element.getAttributeValue("href");
    if (href == null || href.isEmpty() || HtmlUtility.isExternalPath(href)) {
      return Optional.empty();
    }

    int index = href.lastIndexOf("#");
    if (index > -1) {
      String id = href.substring(index + 1);
// TODO: rewrite.
//      Source source;
//      Supplier<String> fileNameSupplier;
//      if (index == 0) {
//        source = element.getSource();
//        fileNameSupplier = () -> " '" + file.getName() + "'";
//      }
//      else {
//        File otherFile = new File(file.getParentFile(), href.substring(0, index));
//        fileNameSupplier = () -> " '" + HtmlUtility.computeRelPath(file, otherFile) + otherFile.getName() + "' (relative to '" + file.getName() + "')";
//        try {
//          source = new Source(otherFile);
//        }
//        catch (IOException e) {
//          LOG.warn("Error by reading the file referenced in the 'a' tag", e);
//          source = null;
//        }
//
//      }
//      if (source != null && !JerichoHtmlUtility.containsElementWithId(source, id)) {
//        String errorMessage =
//            "Anchor '" + id + "' referenced in the 'href' attribute in the 'a' tag is missing in file " + fileNameSupplier.get();
//        return createLintError(file, errorMessage, element.getRowColumnVector().getRow());
//      }
    }
    return Optional.empty();
  }
}

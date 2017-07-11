package fr.jmini.htmlchecker.rules.impl;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selesse.jxlint.model.rules.LintError;

import fr.jmini.htmlchecker.HtmlUtility;
import fr.jmini.htmlchecker.JerichoHtmlUtility;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

public class LocalATagRule extends AbstractLocalRule {
  private static final Logger LOG = LoggerFactory.getLogger(LocalATagRule.class);

  public LocalATagRule() {
    super(HTMLElementName.A, "href", "A_TAG");
  }

  @Override
  protected String computeLocalPath(Element element) {
    String path = super.computeLocalPath(element);
    int index = path.lastIndexOf("#");
    if (index > -1) {
      return path.substring(0, index);
    }
    return path;
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
      Source source;
      Supplier<String> fileNameSupplier;
      if (index == 0) {
        source = element.getSource();
        fileNameSupplier = () -> " '" + file.getName() + "'";
      }
      else {
        File otherFile = new File(file.getParentFile(), href.substring(0, index));
        fileNameSupplier = () -> " '" + HtmlUtility.computeRelPath(file, otherFile) + otherFile.getName() + "' (relative to '" + file.getName() + "')";
        try {
          source = new Source(otherFile);
        }
        catch (IOException e) {
          LOG.warn("Error by reading the file referenced in the 'a' tag", e);
          source = null;
        }

      }
      if (source != null && !JerichoHtmlUtility.containsElementWithId(source, id)) {
        String errorMessage =
            "Anchor '" + id + "' referenced in the 'href' attribute in the 'a' tag is missing in file " + fileNameSupplier.get();
        return Optional.of(LintError.with(this, file)
            .andErrorMessage(errorMessage)
            .andLineNumber(element.getRowColumnVector().getRow())
            .create());
      }

    }
    return Optional.empty();
  }
}

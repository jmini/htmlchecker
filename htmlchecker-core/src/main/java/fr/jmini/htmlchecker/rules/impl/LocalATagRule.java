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
    return HtmlUtility.removeAnchor(path);
  }

  @Override
  protected boolean isLocalFileCorrect(File localFile) {
    if (super.isLocalFileCorrect(localFile)) {
      return true;
    }
    if (localFile.isDirectory()) {
      File indexFile = computeIndexFile(localFile);
      return super.isLocalFileCorrect(indexFile);
    }
    return false;
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
    if (index > -1 && index < href.length() - 1) {
      String id = href.substring(index + 1);
      Source source;
      Supplier<String> fileNameSupplier;
      if (index == 0) {
        source = element.getSource();
        fileNameSupplier = () -> " '" + file.getName() + "'";
      }
      else {
        File otherFile = computeOtherFile(file, href.substring(0, index));
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
        return createLintError(file, errorMessage, element.getRowColumnVector().getRow());
      }
    }
    return Optional.empty();
  }

  private File computeOtherFile(File file, String path) {
    File f = new File(file.getParentFile(), path);
    if (f.isDirectory()) {
      return computeIndexFile(f);
    }
    return f;
  }

  private File computeIndexFile(File folder) {
    return new File(folder, "index.html");
  }
}

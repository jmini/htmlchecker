package fr.jmini.htmlchecker.rules.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.selesse.jxlint.model.rules.LintError;
import com.selesse.jxlint.model.rules.Severity;

import fr.jmini.htmlchecker.HtmlCheckerCategories;
import fr.jmini.htmlchecker.HtmlUtility;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class AbstractRemoteRule extends AbstractJerichoRule {
  private String tagName;
  private String attributeName;

  OkHttpClient client = new OkHttpClient();

  public AbstractRemoteRule(String tagName, String attributeName, String id) {
    super("REMOTE_" + id + "_RULE", "Local file should be present", Severity.WARNING, HtmlCheckerCategories.REMOTE);
    this.tagName = tagName;
    this.attributeName = attributeName;
  }

  @Override
  protected List<LintError> computeLintErrors(Source source, File file) {
    return source.getAllElements(tagName).stream()
        .flatMap(e -> checkAndcreateLintError(file, e).map(Stream::of).orElseGet(Stream::empty))
        .collect(Collectors.toList());
  }

  protected Optional<LintError> checkAndcreateLintError(File file, Element element) {
    String remotePath = computeRemotePath(element);
    if (remotePath == null || remotePath.isEmpty() || !HtmlUtility.isExternalPath(remotePath)) {
      return Optional.empty();
    }
// TODO: remote path
//    File localFile = new File(file.getParentFile(), localPath);
//    if (!localFile.exists()) {
//      String errorMessage =
//          "File '" + HtmlUtility.computeRelPath(file, localFile) + localFile.getName() + "' (relative to '" + file.getName() + "') referenced by the '" + attributeName + "' attribute in the '" + tagName + "' tag is missing";
//      return createLintError(file, errorMessage, element.getRowColumnVector().getRow());
//    }
    return Optional.empty();
  }

  String run(String url) throws IOException {
    Request request = new Request.Builder()
        .url(url)
        .build();

    Response response = client.newCall(request).execute();
    response.close();
    return response.body().string();
  }

  protected String computeRemotePath(Element element) {
    return element.getAttributeValue(attributeName);
  }
}

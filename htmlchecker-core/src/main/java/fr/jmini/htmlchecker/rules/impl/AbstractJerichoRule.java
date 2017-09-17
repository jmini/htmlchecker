package fr.jmini.htmlchecker.rules.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selesse.jxlint.model.rules.LintError;
import com.selesse.jxlint.model.rules.LintRule;
import com.selesse.jxlint.model.rules.Severity;
import com.selesse.jxlint.utils.FileUtils;

import net.htmlparser.jericho.Source;

public abstract class AbstractJerichoRule extends LintRule {
  private static final Logger LOG = LoggerFactory.getLogger(AbstractJerichoRule.class);

  public AbstractJerichoRule(String name, String summary, Severity severity, Enum<?> category) {
    super(name, summary, "", severity, category);
    setDetailedDescription(getMarkdownDescription());
  }

  @Override
  public List<File> getFilesToValidate() {
    return FileUtils.allFilesMatching(getSourceDirectory(), ".*\\.html");
  }

  @Override
  public List<LintError> getLintErrors(File file) {
    try {
      Source source = new Source(file);
      return computeLintErrors(source, file);
    }
    catch (IOException e) {
      LOG.error("Error reading file", e);
    }
    return Collections.emptyList();
  }

  protected Optional<LintError> createLintError(File file, String errorMessage, int lineNumber) {
    return Optional.of(LintError.with(this, file)
        .andErrorMessage(errorMessage)
        .andLineNumber(lineNumber)
        .create());
  }

  protected abstract List<LintError> computeLintErrors(Source source, File file);
}

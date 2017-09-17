package fr.jmini.htmlchecker.mvn;

import org.apache.maven.plugins.annotations.Mojo;

import com.selesse.jxlint.maven.AbstractJxlintMojo;
import com.selesse.jxlint.model.rules.LintRules;
import com.selesse.jxlint.settings.ProgramSettings;

import fr.jmini.htmlchecker.HtmlCheckerCategories;
import fr.jmini.htmlchecker.rules.HtmlCheckerRules;
import fr.jmini.htmlchecker.settings.HtmlCheckerProgramSettings;

@Mojo(name = "generate-report")
public class HtmlCheckerMojo extends AbstractJxlintMojo {

  @Override
  protected ProgramSettings provideProgramSettings() {
    return new HtmlCheckerProgramSettings();
  }

  @Override
  protected LintRules provideLintRules() {
    return new HtmlCheckerRules();
  }

  @Override
  protected Class<? extends Enum<?>> provideCategories() {
    return HtmlCheckerCategories.class;
  }
}

package fr.jmini.htmlchecker.gradle;

import com.selesse.jxlint.gradle.AbstractJxlintTask;
import com.selesse.jxlint.model.rules.LintRules;
import com.selesse.jxlint.settings.ProgramSettings;

import fr.jmini.htmlchecker.HtmlCheckerCategories;
import fr.jmini.htmlchecker.rules.HtmlCheckerRules;
import fr.jmini.htmlchecker.settings.HtmlCheckerProgramSettings;

public class HtmlcheckerTask extends AbstractJxlintTask {

  public HtmlcheckerTask() {
    this.setGroup("Verify");
    this.setDescription("Check HTML files");
  }

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

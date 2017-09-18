package fr.jmini.htmlchecker.settings;

import java.io.File;

import com.selesse.jxlint.settings.ProgramSettings;

public class HtmlCheckerProgramSettings implements ProgramSettings {
  @Override
  public String getProgramVersion() {
    return "1.0.0";
  }

  @Override
  public String getProgramName() {
    return "htmlchecker";
  }

  @Override
  public void initializeForWeb(File projectRoot) {
  }
}
package com.selesse.jxlint.maven;

import org.apache.maven.plugin.MojoExecutionException;

import com.selesse.jxlint.model.AbstractDispatcher;
import com.selesse.jxlint.model.ExitException;
import com.selesse.jxlint.model.ProgramOptions;
import com.selesse.jxlint.settings.ProgramSettings;

// Copied from 'com.selesse:jxlint-maven:2.2.0' because of:
// https://github.com/britter/maven-plugin-development/issues/73#issuecomment-1061509730
public class MojoDispatcher extends AbstractDispatcher {

  MojoDispatcher(ProgramOptions programOptions, ProgramSettings programSettings) {
    super(programOptions, programSettings);
  }

  void dispatch() throws MojoExecutionException {
    try {
      doDispatch();
    }
    catch (ExitException e) {
      throw new MojoExecutionException(e.getMessage());
    }
  }

  @Override
  protected String createHelpMessage(ProgramSettings settings) {
    return "";
  }

  @Override
  protected void startWebServer() {
  }
}

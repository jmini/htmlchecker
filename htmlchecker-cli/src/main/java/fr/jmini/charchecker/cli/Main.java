package fr.jmini.charchecker.cli;

import com.selesse.jxlint.Jxlint;
import com.selesse.jxlint.model.rules.Categories;

import fr.jmini.htmlchecker.HtmlCheckerCategories;
import fr.jmini.htmlchecker.rules.HtmlCheckerRules;
import fr.jmini.htmlchecker.settings.HtmlCheckerProgramSettings;

public class Main {
  public static void main(String[] args) {
    Categories.setCategories(HtmlCheckerCategories.class);

    Jxlint jxlint = new Jxlint(new HtmlCheckerRules(), new HtmlCheckerProgramSettings(), false);
    jxlint.parseArgumentsAndDispatch(args);
  }
}

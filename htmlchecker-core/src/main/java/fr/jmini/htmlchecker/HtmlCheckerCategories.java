package fr.jmini.htmlchecker;

public enum HtmlCheckerCategories {
  LOCAL("Local"),
  ;

  private final String displayName;

  HtmlCheckerCategories(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }

}

package fr.jmini.charchecker.mvn;

/**
 * <h3>{@link XmlIssue}</h3>
 *
 * @author jbr
 */
public class XmlIssue {
  String name;
  String severity;
  String message;
  String category;
  String summary;
  String explanation;
  String location;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSeverity() {
    return severity;
  }

  public void setSeverity(String severity) {
    this.severity = severity;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getExplanation() {
    return explanation;
  }

  public void setExplanation(String explanation) {
    this.explanation = explanation;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public String toString() {
    return "JxlintIssue [name=" + name + ", severity=" + severity + ", message=" + message + ", category=" + category + ", summary=" + summary + ", location=" + location + "]";
  }
}

package fr.jmini.charchecker.mvn;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.common.io.Files;

public class HtmlCheckerMojoTest extends AbstractMojoTestCase {
  protected File tempDirectory;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    tempDirectory = Files.createTempDir();
    tempDirectory.deleteOnExit();
  }

  public void testMinimal() throws Exception {
    File createdFile = executeMojo("target/test-classes/poms/pom-minimal.xml");
    NodeList nodeList = loadIssuesFromXml(createdFile);
    assertThat(nodeList.getLength()).isEqualTo(8);
    List<XmlIssue> issueList = convertToList(nodeList);
    assertThat(issueList).hasSize(8);
    Map<String, List<XmlIssue>> itemsGroupedByName =
        issueList.stream().collect(Collectors.groupingBy(XmlIssue::getName));

    assertThat(itemsGroupedByName).hasSize(4);
    assertThat(itemsGroupedByName.keySet()).containsExactlyInAnyOrder("LOCAL_LINK_TAG_RULE", "LOCAL_SCRIPT_TAG_RULE", "LOCAL_A_TAG_RULE", "LOCAL_IMG_TAG_RULE");
    assertThat(itemsGroupedByName.get("LOCAL_LINK_TAG_RULE")).hasSize(2);
    assertThat(itemsGroupedByName.get("LOCAL_SCRIPT_TAG_RULE")).hasSize(1);
    assertThat(itemsGroupedByName.get("LOCAL_A_TAG_RULE")).hasSize(4);
    assertThat(itemsGroupedByName.get("LOCAL_IMG_TAG_RULE")).hasSize(1);
  }

  public void testCheckOnlyRules() throws Exception {
    File createdFile = executeMojo("target/test-classes/poms/pom-check.xml");
    NodeList nodeList = loadIssuesFromXml(createdFile);
    assertThat(nodeList.getLength()).isEqualTo(1);
    List<XmlIssue> issueList = convertToList(nodeList);
    assertThat(issueList).hasSize(1);
    assertThat(issueList.get(0).getName()).isEqualTo("LOCAL_IMG_TAG_RULE");
  }

  public void testDisableRules() throws Exception {
    File createdFile = executeMojo("target/test-classes/poms/pom-disable.xml");
    NodeList nodeList = loadIssuesFromXml(createdFile);
    assertThat(nodeList.getLength()).isEqualTo(3);
    List<XmlIssue> issueList = convertToList(nodeList);
    assertThat(issueList).hasSize(3);
    Map<String, List<XmlIssue>> itemsGroupedByName =
        issueList.stream().collect(Collectors.groupingBy(XmlIssue::getName));

    assertThat(itemsGroupedByName).hasSize(2);
    assertThat(itemsGroupedByName.keySet()).containsExactlyInAnyOrder("LOCAL_LINK_TAG_RULE", "LOCAL_SCRIPT_TAG_RULE");
    assertThat(itemsGroupedByName.get("LOCAL_LINK_TAG_RULE")).hasSize(2);
    assertThat(itemsGroupedByName.get("LOCAL_SCRIPT_TAG_RULE")).hasSize(1);
  }

  private List<XmlIssue> convertToList(NodeList nodeList) {
    List<XmlIssue> issueList = new ArrayList<>();
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      NamedNodeMap namedNodeMap = node.getAttributes();
      XmlIssue issue = new XmlIssue();
      issue.setName(getAttributeValue(namedNodeMap, "name"));
      issue.setSeverity(getAttributeValue(namedNodeMap, "severity"));
      issue.setMessage(getAttributeValue(namedNodeMap, "message"));
      issue.setCategory(getAttributeValue(namedNodeMap, "category"));
      issue.setSummary(getAttributeValue(namedNodeMap, "summary"));
      issue.setExplanation(getAttributeValue(namedNodeMap, "explanation"));
      issue.setLocation(getAttributeValue(namedNodeMap, "location"));
      issueList.add(issue);
    }
    return issueList;
  }

  private String getAttributeValue(NamedNodeMap namedNodeMap, String attribute) {
    Node node = namedNodeMap.getNamedItem(attribute);
    if (node != null) {
      return node.getNodeValue();
    }
    return null;
  }

  private File executeMojo(String pomRelativePath) throws Exception, IllegalAccessException, MojoExecutionException, MojoFailureException {
    File testPom = new File(getBasedir(), pomRelativePath);
    HtmlCheckerMojo mojo = (HtmlCheckerMojo) lookupMojo("generate-report", testPom);
    assertNotNull(mojo);

    File ouputFile = (File) getVariableValueFromObject(mojo, HtmlCheckerMojo.OUTPUT_FILE);
    ouputFile.delete();
    assertFalse(ouputFile.exists());

    mojo.execute();

    assertTrue(ouputFile.exists());
    return ouputFile;
  }

  private NodeList loadIssuesFromXml(File createdFile) throws ParserConfigurationException, SAXException, IOException {
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    Document document = documentBuilder.parse(createdFile);

    NodeList nodeList = document.getElementsByTagName("issue");
    return nodeList;
  }

}

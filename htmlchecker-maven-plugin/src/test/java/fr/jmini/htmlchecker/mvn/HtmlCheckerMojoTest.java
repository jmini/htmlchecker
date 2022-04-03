package fr.jmini.htmlchecker.mvn;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import io.takari.maven.testing.TestResources;
import io.takari.maven.testing.executor.MavenExecutionResult;
import io.takari.maven.testing.executor.MavenRuntime;
import io.takari.maven.testing.executor.MavenRuntime.MavenRuntimeBuilder;
import io.takari.maven.testing.executor.MavenVersions;
import io.takari.maven.testing.executor.junit.MavenJUnitTestRunner;

@RunWith(MavenJUnitTestRunner.class)
@MavenVersions({"3.6.3"})
public class HtmlCheckerMojoTest {

  @Rule
  public final TestResources resources = new TestResources("src/test/resources/poms", "build/maven-work");

  private final MavenRuntime maven;

  public HtmlCheckerMojoTest(MavenRuntimeBuilder builder) throws Exception {
    this.maven = builder.build();
  }

  @Test
  public void testMinimal() throws Exception {
    File createdFile = executeMojo("minimal");
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

  @Test
  public void testCheckOnlyRules() throws Exception {
    File createdFile = executeMojo("check");
    NodeList nodeList = loadIssuesFromXml(createdFile);
    assertThat(nodeList.getLength()).isEqualTo(1);
    List<XmlIssue> issueList = convertToList(nodeList);
    assertThat(issueList).hasSize(1);
    assertThat(issueList.get(0).getName()).isEqualTo("LOCAL_IMG_TAG_RULE");
  }

  @Test
  public void testDisableRules() throws Exception {
    File createdFile = executeMojo("disable");
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

  private File executeMojo(String pomFolderName) throws IOException, Exception {
    Path siteFolder = Paths.get("../site-example/src/main/resources/site/");
    assertThat(siteFolder).isDirectory();

    File basedir = resources.getBasedir(pomFolderName);
    MavenExecutionResult result = maven.forProject(basedir)
        .execute("verify", "-X", "-Dit.siteFolder=" + siteFolder.normalize().toAbsolutePath());

    for (String l : result.getLog()) {
      System.out.println(l);
    }

    result
        .assertLogText("running 'htmlchecker' version '1.2.4'")
        .assertLogText("BUILD SUCCESS");

    File file = new File(basedir, "target/report_" + pomFolderName + ".xml");
    assertThat(file).isFile();
    return file;
  }

  private NodeList loadIssuesFromXml(File createdFile) throws ParserConfigurationException, SAXException, IOException {
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
    Document document = documentBuilder.parse(createdFile);

    NodeList nodeList = document.getElementsByTagName("issue");
    return nodeList;
  }
}

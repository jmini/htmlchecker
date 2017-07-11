package fr.jmini.htmlchecker;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.htmlparser.jericho.Source;

/**
 * <h3>{@link JerichoHtmlUtilityTest}</h3>
 *
 * @author jbr
 */
public class JerichoHtmlUtilityTest {

  @Test
  public void testIsExternalPathEmpty() throws Exception {
    Source source = new Source(createHtml("<div><p>body</p></div>"));
    assertEquals(false, JerichoHtmlUtility.containsElementWithId(source, "abc"));
  }

  @Test
  public void testIsExternalPathExist() throws Exception {
    Source source = new Source(createHtml("<div id=\"abc\"><p>body</p></div>"));
    assertEquals(true, JerichoHtmlUtility.containsElementWithId(source, "abc"));
  }

  @Test
  public void testIsExternalPathANameExist() throws Exception {
    Source source = new Source(createHtml("<p>body <a name=\"abc\" /></p>"));
    assertEquals(true, JerichoHtmlUtility.containsElementWithId(source, "abc"));
  }

  @Test
  public void testIsExternalPathNotExist() throws Exception {
    Source source = new Source(createHtml("<div id=\"xyz\"><p>body</p></div>"));
    assertEquals(false, JerichoHtmlUtility.containsElementWithId(source, "abc"));
  }

  private CharSequence createHtml(String body) {
    return "<html>\n" +
        "<head>\n" +
        "<title>Test</title>\n" +
        "</head>\n" +
        "<head>\n" +
        "<body>\n" +
        body +
        "</body>\n" +
        "</html>";
  }

}

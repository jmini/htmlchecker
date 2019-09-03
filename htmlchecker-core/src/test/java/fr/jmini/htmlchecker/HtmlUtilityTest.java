package fr.jmini.htmlchecker;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.google.common.io.Files;

public class HtmlUtilityTest {

  protected File tempDirectory;

  @Before
  public void before() throws Exception {
    tempDirectory = Files.createTempDir();
    tempDirectory.deleteOnExit();
  }

  @Test
  public void testComputeRelPathFile() {
    assertEquals("", HtmlUtility.computeRelPath(new File(new File(new File(tempDirectory, "site"), "folder"), "page1.html"), new File(new File(new File(tempDirectory, "site"), "folder"), "page1.html")));
    assertEquals("", HtmlUtility.computeRelPath((File) null, (File) null));
    assertEquals("", HtmlUtility.computeRelPath(null, tempDirectory));
    assertEquals("", HtmlUtility.computeRelPath(tempDirectory, null));

    assertEquals("", HtmlUtility.computeRelPath(new File(tempDirectory, "page1.html"), new File(tempDirectory, "page2.html")));
    assertEquals("", HtmlUtility.computeRelPath(new File(new File(tempDirectory, "folder"), "page1.html"), new File(new File(tempDirectory, "folder"), "page1.html")));

    assertEquals("site/folder/", HtmlUtility.computeRelPath(new File(tempDirectory, "index.html"), new File(new File(new File(tempDirectory, "site"), "folder"), "page1.html")));
    assertEquals("dir/", HtmlUtility.computeRelPath(new File(new File(tempDirectory, "folder"), "page1.html"), new File(new File(new File(tempDirectory, "folder"), "dir"), "page1.html")));
    assertEquals("../", HtmlUtility.computeRelPath(new File(new File(tempDirectory, "folder"), "page1.html"), new File(tempDirectory, "page2.html")));
    assertEquals("../site/folder/", HtmlUtility.computeRelPath(new File(new File(tempDirectory, "archive"), "page1.html"), new File(new File(new File(tempDirectory, "site"), "folder"), "page1.html")));
  }

  @Test
  public void testComputeRelPathString() {
    assertEquals("", HtmlUtility.computeRelPath("site/folder/page1.html", "site/folder/page1.html"));
    assertEquals("", HtmlUtility.computeRelPath("", ""));

    assertEquals("", HtmlUtility.computeRelPath((String) null, (String) null));
    assertEquals("", HtmlUtility.computeRelPath(null, ""));
    assertEquals("", HtmlUtility.computeRelPath("", null));

    assertEquals("", HtmlUtility.computeRelPath("index.html", "page2.html"));
    assertEquals("", HtmlUtility.computeRelPath("index.html", "/page2.html"));
    assertEquals("", HtmlUtility.computeRelPath("/index.html", "page2.html"));
    assertEquals("", HtmlUtility.computeRelPath("/index.html", "/page2.html"));

    assertEquals("", HtmlUtility.computeRelPath("a/index.html", "a/page2.html"));
    assertEquals("", HtmlUtility.computeRelPath("a/index.html", "/a/page2.html"));
    assertEquals("", HtmlUtility.computeRelPath("/a/index.html", "a/page2.html"));
    assertEquals("", HtmlUtility.computeRelPath("/a/index.html", "/a/page2.html"));

    assertEquals("site/folder/", HtmlUtility.computeRelPath("index.html", "site/folder/page1.html"));
    assertEquals("site/folder/", HtmlUtility.computeRelPath("index.html", "/site/folder/page1.html"));
    assertEquals("site/folder/", HtmlUtility.computeRelPath("/index.html", "site/folder/page1.html"));
    assertEquals("site/folder/", HtmlUtility.computeRelPath("/index.html", "/site/folder/page1.html"));

    assertEquals("site/f/", HtmlUtility.computeRelPath("index.html", "site/f/"));
    assertEquals("site/f/", HtmlUtility.computeRelPath("index.html", "/site/f/"));
    assertEquals("site/f/", HtmlUtility.computeRelPath("/index.html", "site/f/"));
    assertEquals("site/f/", HtmlUtility.computeRelPath("/index.html", "/site/f/"));

    assertEquals("dir/", HtmlUtility.computeRelPath("site/page1.html", "site/dir/page1.html"));
    assertEquals("dir/", HtmlUtility.computeRelPath("site/page1.html", "/site/dir/page1.html"));
    assertEquals("dir/", HtmlUtility.computeRelPath("/site/page1.html", "site/dir/page1.html"));
    assertEquals("dir/", HtmlUtility.computeRelPath("/site/page1.html", "/site/dir/page1.html"));

    assertEquals("directory/", HtmlUtility.computeRelPath("site/page1.html", "site/directory/"));
    assertEquals("directory/", HtmlUtility.computeRelPath("site/page1.html", "/site/directory/"));
    assertEquals("directory/", HtmlUtility.computeRelPath("/site/page1.html", "site/directory/"));
    assertEquals("directory/", HtmlUtility.computeRelPath("/site/page1.html", "/site/directory/"));

    assertEquals("../", HtmlUtility.computeRelPath("site/page1.html", "page1.html"));
    assertEquals("../", HtmlUtility.computeRelPath("site/page1.html", "/page1.html"));
    assertEquals("../", HtmlUtility.computeRelPath("/site/page1.html", "page1.html"));
    assertEquals("../", HtmlUtility.computeRelPath("/site/page1.html", "/page1.html"));

    assertEquals("../site/folder/", HtmlUtility.computeRelPath("archive/page1.html", "site/folder/page1.html"));
    assertEquals("../site/folder/", HtmlUtility.computeRelPath("archive/page1.html", "/site/folder/page1.html"));
    assertEquals("../site/folder/", HtmlUtility.computeRelPath("/archive/page1.html", "site/folder/page1.html"));
    assertEquals("../site/folder/", HtmlUtility.computeRelPath("/archive/page1.html", "/site/folder/page1.html"));
  }

  @Test
  public void testIsExternalPath() throws Exception {
    assertEquals(true, HtmlUtility.isExternalPath("http://github.com"));
    assertEquals(true, HtmlUtility.isExternalPath("http://site.net/page.php?some=value#test"));
    assertEquals(true, HtmlUtility.isExternalPath("http://localhost:8080/some"));
    assertEquals(true, HtmlUtility.isExternalPath("ftp://some.com/"));
    assertEquals(true, HtmlUtility.isExternalPath("ftp://use@some.com/"));
    assertEquals(true, HtmlUtility.isExternalPath("ssh:git@github.com:jmini/htmlchecker.git"));

    assertEquals(false, HtmlUtility.isExternalPath(""));
    assertEquals(false, HtmlUtility.isExternalPath("site/folder/page1.html"));
    assertEquals(false, HtmlUtility.isExternalPath("/archive/page1.html"));
  }

  @Test
  public void testRemoveAnchorAndQuery() throws Exception {
    assertEquals("/archive/page1.html", HtmlUtility.removeAnchorAndQuery("/archive/page1.html"));
    assertEquals("/archive/page1.html", HtmlUtility.removeAnchorAndQuery("/archive/page1.html#anchor"));
    assertEquals("/archive/page1.html", HtmlUtility.removeAnchorAndQuery("/archive/page1.html#"));
    assertEquals("/archive/page1.html", HtmlUtility.removeAnchorAndQuery("/archive/page1.html?query"));
    assertEquals("/archive/page1.html", HtmlUtility.removeAnchorAndQuery("/archive/page1.html?"));
    assertEquals("/archive/page1.html", HtmlUtility.removeAnchorAndQuery("/archive/page1.html?query#anchor"));
    assertEquals("/archive/page1.html", HtmlUtility.removeAnchorAndQuery("/archive/page1.html#anchor?"));
    assertEquals("", HtmlUtility.removeAnchorAndQuery(""));
    assertEquals("", HtmlUtility.removeAnchorAndQuery("?id=1"));
    assertEquals("", HtmlUtility.removeAnchorAndQuery("#test"));
    assertEquals(null, HtmlUtility.removeAnchorAndQuery(null));

  }
}

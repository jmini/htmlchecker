package fr.jmini.htmlchecker;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class HtmlUtility {

  public static String computeRelPath(String outputFile, String contentFile) {
    if (outputFile == null || contentFile == null || outputFile.equals(contentFile)) {
      return "";
    }
    Path pathAbsolute = folderPath(contentFile);
    Path pathBase = folderPath(outputFile);
    return relPath(pathAbsolute, pathBase);
  }

  public static String computeRelPath(File outputFile, File contentFile) {
    if (outputFile == null || contentFile == null || outputFile.equals(contentFile)) {
      return "";
    }
    Path pathAbsolute = contentFile.getParentFile().toPath();
    Path pathBase = outputFile.getParentFile().toPath();
    return relPath(pathAbsolute, pathBase);
  }

  private static Path folderPath(String filePath) {
    int index = filePath.lastIndexOf("/");
    if (index > 0) {
      String folder = filePath.substring(0, index);
      if (folder.startsWith("/")) {
        return Paths.get(folder);
      }
      else {
        return Paths.get("/" + folder);
      }
    }
    else {
      return Paths.get("/");
    }
  }

  private static String relPath(Path pathAbsolute, Path pathBase) {
    Path pathRelative = pathBase.relativize(pathAbsolute);
    String result = pathRelative.toString();
    if (result.isEmpty()) {
      return "";
    }
    return result.replaceAll("\\\\", "/") + "/";
  }

  public static boolean isExternalPath(String path) {
    return path.matches("[a-z]+:.*");
  }

  private HtmlUtility() {
  }
}

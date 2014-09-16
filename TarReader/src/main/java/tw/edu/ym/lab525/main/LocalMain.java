package tw.edu.ym.lab525.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import tw.edu.ym.lab525.commons.compress.tar.TarReader;

public class LocalMain {

  public static void main(String[] args) throws IOException {
    InputStream br =
        new FileInputStream(new File(
            "/Users/apple/Documents/TEST_C/TarTest/toCsvTar0.tar.gz"));

    TarReader tr = new TarReader(br, true);
    for (String t : tr.readLinesBetween(0, 2)) {
      System.out.println(t);
    }
  }
}

package tw.edu.ym.lab525.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import tw.edu.ym.lab525.commons.compress.tar.TarReader;

public class LocalMain {

  public static void main(String[] args) throws IOException {
    InputStream is =
        new FileInputStream(new File("src/test/resources/10000.txt.tar"));

    TarReader tr = new TarReader(is, true);
    for (String t : tr.readLinesBetween(0, 2)) {
      System.out.println(t);
    }
  }
}

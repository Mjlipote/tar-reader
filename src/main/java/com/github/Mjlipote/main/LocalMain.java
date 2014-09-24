package com.github.Mjlipote.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.github.Mjlipote.commons.compress.tar.TarReader;

/**
 * 
 * @author Ming-Jheng Li
 * 
 */
public class LocalMain {

  public static final String LOCAL_FILE = "";
  public static int begin;
  public static int end;

  public static void main(String[] args) throws IOException {
    InputStream is = new FileInputStream(new File(LOCAL_FILE));

    TarReader tr = new TarReader(is, true);
    for (String t : tr.readLinesBetween(begin, end)) {
      System.out.println(t);
    }
  }
}

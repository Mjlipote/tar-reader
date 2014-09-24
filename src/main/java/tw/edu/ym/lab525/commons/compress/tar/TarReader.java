package tw.edu.ym.lab525.commons.compress.tar;

/**
 * @author Ming-Jheng Li
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TarReader {

  private static final Logger log = LoggerFactory.getLogger(TarReader.class);
  private final InputStream is;
  private final boolean isArchive;

  /**
   * A Constructor of TarReader by using file name
   * 
   * @param fileName
   * @param isArc
   * @throws FileNotFoundException
   */
  public TarReader(String fileName, boolean isArc) throws FileNotFoundException {
    this.is = new FileInputStream(new File(fileName));
    this.isArchive = isArc;
  }

  /**
   * A Constructor of TarReader by using InputStream
   * 
   * @param inputstream
   * @param isArc
   */
  public TarReader(InputStream inputstream, boolean isArc) {
    is = inputstream;
    isArchive = isArc;
  }

  /**
   * Get bufferedbeader when the file is archive
   * 
   * @return BufferedReader
   * @throws IOException
   */
  private BufferedReader getArchiveBufferedReader() throws IOException {
    TarArchiveInputStream tarFile = new TarArchiveInputStream(is);
    tarFile.getNextTarEntry();
    return new BufferedReader(new InputStreamReader(tarFile, "UTF-8"));
  }

  /**
   * Get bufferedbeader when the file is not archive
   * 
   * @return BufferedReader
   * @throws IOException
   */
  private BufferedReader getBufferedReader() throws IOException {
    return new BufferedReader(new InputStreamReader(is, "UTF-8"));
  }

  /**
   * Read lines between startIndex and ecnIndex
   * 
   * @param startIndex
   * @param endIndex
   * @return lines
   * @throws IOException
   */
  public List<String> readLinesBetween(int startIndex, int endIndex)
      throws IOException {
    List<String> ls = new ArrayList<String>();
    if (endIndex < startIndex) {
      log.error("輸入的範圍有誤！");
    } else {
      BufferedReader br =
          isArchive == true ? getArchiveBufferedReader() : getBufferedReader();

      int i = 0;
      String line = null;

      while ((line = br.readLine()) != null) {
        if (isBetween(i, startIndex, endIndex)) {

          ls.add(line);
        }
        if (i >= endIndex) {
          break;
        }
        i++;
      }

      br.close();

    }
    return ls;
  }

  /**
   * Read at n-th line
   * 
   * @param num
   * @return line
   * @throws IOException
   */
  public String readLineAt(int num) throws IOException {

    List<String> lines = readLinesBetween(num, num);
    return lines.isEmpty() ? null : lines.get(0);

  }

  /**
   * Checks a number whether is between start and end
   * 
   * @param num
   * @param start
   * @param end
   * @return boolean
   */
  public boolean isBetween(int num, int start, int end) {
    boolean bl;
    if (start <= num && num <= end) {
      bl = true;
    } else {
      bl = false;
    }
    return bl;
  };
}

package tw.edu.ym.lab525.commons.compress.tar;

/**
 * @author Ming-Jheng Li
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TarReaderTest {

  TarReader archiveReader;
  TarReader nonArchiveReader;

  List<String> ls = Arrays.asList("1", "2");

  @Before
  public void setUp() throws Exception {

    archiveReader = new TarReader("src/test/resources/10000.txt.tar", true);
    nonArchiveReader = new TarReader("src/test/resources/10000.txt", false);

  }

  @Test
  public void testConstructor() {
    assertTrue(archiveReader instanceof TarReader);
    assertTrue(nonArchiveReader instanceof TarReader);
  }

  @Test
  public void testReadLineAt() throws IOException {
    assertEquals("1", archiveReader.readLineAt(1));
    assertEquals("1", nonArchiveReader.readLineAt(1));
  }

  @Test
  public void testReadLinesBetween() throws IOException {
    assertEquals("1", archiveReader.readLinesBetween(1, 2).get(0));
    assertEquals("1", nonArchiveReader.readLinesBetween(1, 2).get(0));
  }

  @Test
  public void testIsBetween() {
    assertTrue(archiveReader.isBetween(1, 0, 2));
    assertFalse(archiveReader.isBetween(0, 1, 2));
    assertTrue(nonArchiveReader.isBetween(1, 0, 2));
    assertFalse(nonArchiveReader.isBetween(0, 1, 2));
  }

}

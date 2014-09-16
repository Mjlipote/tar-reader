package tw.edu.ym.lab525.sftp.helper;

/**
 * @author Minh-Jheng Li
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import tw.edu.ym.lab525.commons.compress.tar.TarReader;

public class SftpHelperTest {

  SftpHelper sftpHelper;
  TarReader tarReader;

  @Before
  public void setUp() throws Exception {

    sftpHelper =
        new SftpHelper.Builder("192.168.1.122").setUserName("mjli")
            .setPassword("xxxxx").setRemoteFile("toCsvTar.tar.gz").build();
    sftpHelper.connect();
    tarReader = new TarReader(sftpHelper.getInputStream(), true);
  }

  @Test
  public void testConstructor() {
    assertTrue(sftpHelper instanceof SftpHelper);
  }

  @Test
  public void testReadLineAt() throws IOException {
    assertEquals(
        "Local_id1,A123456,0910-123-456,A286640890,TW,F,黃,小宜,10,19,1979,李大華,北榮,,NO",
        tarReader.readLineAt(1));
    assertNull(tarReader.readLineAt(99));
  }
}

package tw.edu.ym.lab525.sftp.connecter;

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

public class SftpConnecterTest {

  SftpConnecter sftpConnecter;
  TarReader tarReader;

  @Before
  public void setUp() throws Exception {

    sftpConnecter =
        SftpConnecter.setRemoteServer("192.168.1.122", "mjli").setPassword("")
            .setRemoteFile("toCsvTar.tar.gz").connect();
    tarReader = new TarReader(sftpConnecter.getInputStream(), true);
  }

  @Test
  public void testConstructor() {
    assertTrue(sftpConnecter instanceof SftpConnecter);
  }

  @Test
  public void testReadLineAt() throws IOException {
    assertEquals(
        "Local_id1,A123456,0910-123-456,A286640890,TW,F,黃,小宜,10,19,1979,李大華,北榮,,NO",
        tarReader.readLineAt(1));
    assertNull(tarReader.readLineAt(99));
  }
}

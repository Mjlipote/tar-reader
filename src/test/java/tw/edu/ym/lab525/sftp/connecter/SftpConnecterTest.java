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

  public static final String REMOTE_SERVER = "";
  public static final String LOGIN_USER_NAME = "";
  public static final String LOGIN_PASSWORD = "";
  public static final String REMOTE_FILE = "";
  public static final String TEST_LINE = "";

  SftpConnecter sftpConnecter;
  TarReader tarReader;

  @Before
  public void setUp() throws Exception {

    sftpConnecter =
        SftpConnecter.setRemoteServer(REMOTE_SERVER, LOGIN_USER_NAME)
            .setPassword(LOGIN_PASSWORD).setRemoteFile(REMOTE_FILE).connect();
    tarReader = new TarReader(sftpConnecter.getInputStream(), true);
  }

  @Test
  public void testConstructor() {
    assertTrue(sftpConnecter instanceof SftpConnecter);
  }

  @Test
  public void testReadLineAt() throws IOException {
    assertEquals(TEST_LINE, tarReader.readLineAt(1));
    assertNull(tarReader.readLineAt(99));
  }
}

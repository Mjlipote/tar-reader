package com.github.Mjlipote.sftp.connecter;

/**
 * @author Minh-Jheng Li
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.github.Mjlipote.commons.compress.tar.TarReader;

public class SftpConnecterTest {

  public static final String REMOTE_SERVER = "remote_server_name";
  public static final String LOGIN_USER_NAME = "login_user_name";
  public static final String LOGIN_PASSWORD = "login_password";
  public static final String REMOTE_FILE = "remote_file_path";
  public static final String TEST_FIRST_LINE = "test_first_line";
  public static String encoding = "UTF-8";

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
    assertEquals(TEST_FIRST_LINE, tarReader.setEncoding(encoding).readLineAt(1));
    assertNull(tarReader.readLineAt(99));
  }
}

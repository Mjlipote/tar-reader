package com.github.Mjlipote.main;

import com.github.Mjlipote.commons.compress.tar.TarReader;
import com.github.Mjlipote.sftp.connecter.SftpConnecter;

/**
 * 
 * @author Ming-Jheng Li
 * 
 */
public class RemoteMain {

  public static final String REMOTE_SERVER = "remote_server_name";
  public static final String LOGIN_USER_NAME = "login_user_name";
  public static final String LOGIN_PASSWORD = "login_password";
  public static final String REMOTE_FILE = "romote_file_path";

  public static int begin;
  public static int end;

  public static void main(String[] args) throws Exception {

    SftpConnecter sftpConnecter =
        SftpConnecter.setRemoteServer(REMOTE_SERVER, LOGIN_USER_NAME)
            .setPassword(LOGIN_PASSWORD).setRemoteFile(REMOTE_FILE).connect();

    TarReader tr = new TarReader(sftpConnecter.getInputStream(), false);

    for (String t : tr.readLinesBetween(begin, end)) {
      System.out.println(t);
    }

    sftpConnecter.close();

  }
}

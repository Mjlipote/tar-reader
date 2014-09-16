package tw.edu.ym.lab525.main;

import java.io.IOException;

import tw.edu.ym.lab525.commons.compress.tar.TarReader;
import tw.edu.ym.lab525.sftp.helper.SftpHelper;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

/**
 * 
 * @author Ming0Jheng Li
 * 
 */
public class RemoteMain {

  public static void main(String[] args) throws JSchException, SftpException,
      IOException {

    SftpHelper sftpHelper =
        new SftpHelper.Builder("120.126.44.113")
            .setUserName("gang")
            .setPassword("xxxxx")
            .setRemoteFile(
                "/storage3/home/bmitest/yhh/songbingwen/Song_20110117/s_1_1_sequence.std.txt.gz")
            .build();

    sftpHelper.connect();
    TarReader tr = new TarReader(sftpHelper.getInputStream(), false);

    for (String t : tr.readLinesBetween(1, 2)) {
      System.out.println(t);
    }

    sftpHelper.disconnect();

  }
}

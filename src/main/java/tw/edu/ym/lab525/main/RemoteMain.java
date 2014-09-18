package tw.edu.ym.lab525.main;

import tw.edu.ym.lab525.commons.compress.tar.TarReader;
import tw.edu.ym.lab525.sftp.connecter.SftpConnecter;

/**
 * 
 * @author Ming0Jheng Li
 * 
 */
public class RemoteMain {

  public static void main(String[] args) throws Exception {

    SftpConnecter sftpConnecter0 =
        SftpConnecter
            .setRemoteServer("120.126.44.113", "gang")
            .setPassword("")
            .setRemoteFile(
                "/storage3/home/bmitest/yhh/songbingwen/Song_20110117/s_1_1_sequence.std.txt.gz")
            .connect();

    SftpConnecter sftpConnecter1 =
        SftpConnecter.setRemoteServer("192.168.1.122", "mjli").setPassword("")
            .setRemoteFile("toCsvTar.tar.gz").connect();

    TarReader tr = new TarReader(sftpConnecter1.getInputStream(), false);

    System.out.println(tr.readLineAt(1));
    for (String t : tr.readLinesBetween(1, 2)) {
      System.out.println(t);
    }

    sftpConnecter1.close();

  }
}

package tw.edu.ym.lab525.sftp.helper;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.InputStream;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * @author Ming-Jheng Li
 */

public final class SftpHelper implements Comparable<SftpHelper> {

  private final String ftpServer;
  private final String ftpUserName;
  private final String ftpPassword;
  private final String remoteFile;
  private Session session = null;
  private ChannelSftp sftpChannel = null;

  private SftpHelper(Builder builder) {
    this.ftpServer = builder.ftpServer;
    this.ftpUserName = builder.ftpUserName;
    this.ftpPassword = builder.ftpPassword;
    this.remoteFile = builder.remoteFile;
  }

  /**
   * Creates a SftpHelper.Builder
   * 
   * @author Ming-Jheng Li
   * 
   */
  public static class Builder {

    private final String ftpServer;
    private String ftpUserName;
    private String ftpPassword;
    private String remoteFile;

    public Builder(String ftpServer) {
      this.ftpServer = checkNotNull(ftpServer, "Server 不可為空值");
    }

    public Builder setUserName(String userName) {
      this.ftpUserName = checkNotNull(userName, "UserName 不可為空值");
      return this;
    }

    public Builder setPassword(String password) {
      this.ftpPassword = checkNotNull(password, "Password 不可為空值");
      return this;
    }

    public Builder setRemoteFile(String remoteFile) {
      this.remoteFile = checkNotNull(remoteFile, "RemoteFile 不可為空值");
      return this;
    }

    public SftpHelper build() {
      return new SftpHelper(this);
    }

  }

  /**
   * Connect sftp channel and session
   * 
   * @throws JSchException
   * @throws SftpException
   */
  public void connect() throws JSchException, SftpException {
    JSch jsch = new JSch();
    session = jsch.getSession(ftpUserName, ftpServer, 22);
    session.setConfig("StrictHostKeyChecking", "no");
    session.setPassword(ftpPassword);
    session.connect();

    sftpChannel = (ChannelSftp) session.openChannel("sftp");
    sftpChannel.connect();
  }

  /**
   * Get InputStream
   * 
   * @return InputStream
   * @throws SftpException
   */
  public InputStream getInputStream() throws SftpException {
    return sftpChannel.get(remoteFile);
  }

  /**
   * Disconnect channel and session
   */

  public void disconnect() {
    if (sftpChannel != null && session != null) {
      sftpChannel.exit();
      session.disconnect();
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof SftpHelper) {
      SftpHelper sftHelper = (SftpHelper) o;
      return Objects.equal(ftpServer, sftHelper.ftpServer)
          && Objects.equal(ftpUserName, sftHelper.ftpUserName)
          && Objects.equal(ftpPassword, sftHelper.ftpPassword)
          && Objects.equal(remoteFile, sftHelper.remoteFile);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(ftpServer, ftpUserName, ftpPassword, remoteFile);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this.getClass()).add("ftpServer", ftpServer)
        .add("ftpUserName", ftpUserName).add("ftpPassword", ftpPassword)
        .add("remoteFile", remoteFile).toString();
  }

  @Override
  public int compareTo(SftpHelper that) {
    return ComparisonChain.start().compare(this.ftpServer, that.ftpServer)
        .compare(this.ftpUserName, that.ftpUserName)
        .compare(this.ftpPassword, that.ftpPassword)
        .compare(this.remoteFile, that.remoteFile).result();
  }

}

package com.github.Mjlipote.sftp.connecter;

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

public final class SftpConnecter implements Comparable<SftpConnecter>,
    AutoCloseable {

  private final String ftpServer;
  private final String ftpUserName;
  private final String ftpPassword;
  private final String remoteFile;
  private final Session session;
  private final ChannelSftp sftpChannel;

  private SftpConnecter(Builder builder) {
    this.ftpServer = builder.ftpServer;
    this.ftpUserName = builder.ftpUserName;
    this.ftpPassword = builder.ftpPassword;
    this.remoteFile = builder.remoteFile;
    this.session = builder.session;
    this.sftpChannel = builder.sftpChannel;
  }

  public static Builder setRemoteServer(String remoteServer, String userName)
      throws JSchException {
    return new Builder(remoteServer, userName);
  }

  /**
   * Creates a SftpConnecter.Builder
   * 
   * @author Ming-Jheng Li
   * 
   */
  public static class Builder {

    private final String ftpServer;
    private final String ftpUserName;
    private String ftpPassword;
    private String remoteFile;
    private Session session = null;
    private ChannelSftp sftpChannel = null;

    private Builder(String remoteServer, String userName) throws JSchException {
      this.ftpServer = checkNotNull(remoteServer, "Server 不可為空值");
      this.ftpUserName = checkNotNull(userName, "UserName 不可為空值");
      JSch jsch = new JSch();
      session = jsch.getSession(userName, remoteServer, 22);
      session.setConfig("StrictHostKeyChecking", "no");
    }

    public Builder setPassword(String password) {
      this.ftpPassword = checkNotNull(password, "Password 不可為空值");
      session.setPassword(password);
      return this;
    }

    public Builder setRemoteFile(String remoteFile) {
      this.remoteFile = checkNotNull(remoteFile, "RemoteFile 不可為空值");
      return this;
    }

    /**
     * Connects sftp channel and session
     * 
     * @throws JSchException
     * @throws SftpException
     */
    private void connectSftp() throws JSchException, SftpException {
      session.connect();
      sftpChannel = (ChannelSftp) session.openChannel("sftp");
      sftpChannel.connect();
    }

    /**
     * Builds the builder and connect to the remote server
     * 
     * @return SftpHelper
     * @throws JSchException
     * @throws SftpException
     */
    public SftpConnecter connect() throws JSchException, SftpException {
      connectSftp();
      return new SftpConnecter(this);
    }

  }

  /**
   * Get InputStream from remote File
   * 
   * @return InputStream
   * @throws SftpException
   */
  public InputStream getInputStream() throws SftpException {
    return sftpChannel.get(remoteFile);
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof SftpConnecter) {
      SftpConnecter sftHelper = (SftpConnecter) o;
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
  public int compareTo(SftpConnecter that) {
    return ComparisonChain.start().compare(this.ftpServer, that.ftpServer)
        .compare(this.ftpUserName, that.ftpUserName)
        .compare(this.ftpPassword, that.ftpPassword)
        .compare(this.remoteFile, that.remoteFile).result();
  }

  /**
   * Disconnect the channel and session
   */

  @Override
  public void close() throws Exception {
    if (sftpChannel != null && session != null) {
      sftpChannel.disconnect();
      session.disconnect();
    }
  }
}

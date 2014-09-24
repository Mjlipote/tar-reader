tar-reader
==========
##Quick start

####SftpConnecter
```java
    SftpConnecter sftpConnecter =
        SftpConnecter.setRemoteServer("remote_server_name", "login_user_name")
            .setPassword("login_password").setRemoteFile("remote_file").connect();
```

####tarReader

######Read line at 
```java
    tarReader = new TarReader(sftpConnecter.getInputStream(), true);
    //Print first line 
    System.out.println(tarReader.readLineAt(1));
```
    
######Read lins between    
 ```java
    tarReader = new TarReader("path_to_tarReader", true);
    //Print 1 up to 3 lines 
    System.out.println(tarReader.readLinesBetween(1, 3));
```

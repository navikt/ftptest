package no.test;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

class ConnectTest {

    @Test
    void foo() throws com.jcraft.jsch.JSchException, com.jcraft.jsch.SftpException {
        System.out.println("LocalDateTime.now() = " + LocalDateTime.now());

        com.jcraft.jsch.JSch javaSecureChannel = new com.jcraft.jsch.JSch();

        String prvKey = "...";

        byte[] bytes = prvKey.getBytes();
        javaSecureChannel.addIdentity("srvOkosynk", bytes, null, null);

        com.jcraft.jsch.Session sftpSession = javaSecureChannel.getSession("srvOkosynk", "b27drvl011.preprod.local", 22);

        sftpSession.setConfig("PreferredAuthentications", "publickey");
        sftpSession.setConfig("StrictHostKeyChecking", "no");
        sftpSession.connect();

        com.jcraft.jsch.ChannelSftp sftpChannel = (com.jcraft.jsch.ChannelSftp) sftpSession.openChannel("sftp");
        sftpChannel.connect();

        InputStream inputStream = sftpChannel.get("/inbound/OS.INPUT");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        List<String> strings = bufferedReader.lines().toList();

        System.out.println("strings = " + strings);

    }
}
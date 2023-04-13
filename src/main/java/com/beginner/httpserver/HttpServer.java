package com.beginner.httpserver;

import com.beginner.httpserver.config.Configuration;
import com.beginner.httpserver.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Driver class for the http server
 * one connection at a time, and is gonna be really dumb
 * always gonna serve the same webpage for a starting point
 * make it multithreaded later. COol stuff
 */

public class HttpServer {
    public static void main(String[] args) {

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        System.out.println("Using Port:"+conf.getPort());
        System.out.println("Using WebRoot:"+conf.getWebroot());

        try {
            ServerSocket serverSocket = new ServerSocket(conf.getPort());
            Socket socket = serverSocket.accept();

            InputStream inputStream=socket.getInputStream();
            OutputStream outputStream  = socket.getOutputStream();
            // do the rea
            String html = "<html><head><title>Simple Java http server</title></head><body><h1>This page was served using my simple java http server</h1></body></html>";
            final String CRLF="\n\r"; //13,10
            String response=
                    "HTTP/1.1 200 OK"+CRLF
                    +"Content-Length: "+ html.getBytes().length+ CRLF +
                    CRLF+html+CRLF+CRLF;
                    ; //Statys Line: HTTP VERSION RESPONSE CODE RESPONSE MESSAGE
            // do the righting
            outputStream.write(response.getBytes());
            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

package com.slongpre.homesense.dataManagement;

import javax.json.JsonObject;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class NetworkWrapper {
    public static final String HOSTNAME = "pi1.deltanet.int";
    public static final int PORT = 6969;

    private Socket clientSocket;
    private OutputStreamWriter out;
    //private BufferedReader in;

    public void startConnection() throws IOException {
        startConnection(HOSTNAME, PORT);
    }

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new OutputStreamWriter(clientSocket.getOutputStream());
        //in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(JsonObject object) throws IOException {
        out.write(object.toString());//.getBytes());
        String resp = "";//in.readLine();
        return resp;
    }

    public String sendMessage(String msg) throws IOException {
        out.write(msg);//.getBytes());
        String resp = "";//in.readLine();
        return resp;
    }

    public void stopConnection() throws IOException {
        //in.close();
        out.close();
        clientSocket.close();
    }
}

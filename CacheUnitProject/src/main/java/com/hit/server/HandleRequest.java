package main.java.com.hit.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.com.hit.dm.DataModel;
import main.java.com.hit.services.CacheUnitController;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;

public class HandleRequest<T> extends Object implements Runnable {
    protected Socket socket;
    protected CacheUnitController controller;
    protected DataOutputStream writer;
    protected DataInputStream reader;

    /**
     * constructor
     *
     * handle request get socket and CacheUnitController from server,
     * socket in order to handle the connection with specific client
     * and CacheUnitController in order to send the client's request to CacheUnitService.
     * @param socket
     * @param controller
     * @throws IOException
     */
    public HandleRequest(Socket socket, CacheUnitController controller) throws IOException {
        this.socket = socket;
        this.controller = controller;
        writer = new DataOutputStream(socket.getOutputStream());
        reader = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    /**
     * called when a new request arrived to the server
     * server send the socket to handleRequest for handling the communication with the client
     */
    @Override
    public void run() {
        String req = null;
        try {
            req = reader.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type ref = new TypeToken<Request<DataModel<T>[]>>() {
        }.getType();
        Request<DataModel<T>[]> request = new Gson().fromJson(req, ref);
        String action = request.getHeaders().get("action");
        switch (action) {
            case "UPDATE":
                try {
                    boolean temp = controller.update(request.getBody());
                    String statsitcs = controller.getStatistics();
                    writer.writeUTF(statsitcs + String.valueOf(temp));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "GET":
                try {
                    StringBuilder msg = new StringBuilder();
                    DataModel[] dm = controller.get(request.getBody());
                    if (dm != null) {
                        for (int i = 0; i < dm.length; i++) {
                            if (dm[i] != null) {
                                msg.append(dm[i].toString()).append("<br/>");
                            } else {
                                msg.append("not exists id #" + i + "<br/>");
                            }

                        }
                    } else {
                        msg = new StringBuilder("null");
                    }
                    String statsitcs = controller.getStatistics();
                    writer.writeUTF(statsitcs + msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "DELETE":
                try {
                    boolean temp = controller.delete((request.getBody()));
                    String statsitcs = controller.getStatistics();
                    writer.writeUTF(statsitcs + String.valueOf(temp));
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        try {
            writer.close();
            reader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package main.java.com.hit.server;

import main.java.com.hit.services.CacheUnitController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Object implements PropertyChangeListener, Runnable {
    protected boolean serverRun;
    private ServerSocket server;
    private Socket socket;
    private CacheUnitController cacheUnitController;

    /**
     * constructor
     */
    public Server() {
        serverRun = true;
        cacheUnitController = new CacheUnitController<String>();
        try {
            server = new ServerSocket(12345);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get the CLI messages by PropertyChangeListener
     * and according to them start/stop server
     *
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String command = (String) evt.getNewValue();
        if (command.equals("start")) {
            Thread serverTread = new Thread(this);
            serverRun = true;
            serverTread.start();
        }
        if (command.equals("stop")) {
            cacheUnitController.saveOnShutdown();
            serverRun = false;
        }
    }


    @Override
    public void run() {
        while (serverRun) {
            try {
                socket = server.accept();
                if (serverRun) {
                    HandleRequest<String> handleRequest = new HandleRequest<String>(socket, cacheUnitController);
                    Thread thread = new Thread(handleRequest);
                    thread.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
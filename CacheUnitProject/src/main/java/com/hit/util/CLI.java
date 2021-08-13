package main.java.com.hit.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.Scanner;

public class CLI extends Object implements Runnable {
    protected Scanner in;
    protected DataOutputStream out;
    protected PropertyChangeSupport support;
    private Boolean hasStart;

    public CLI(InputStream in, OutputStream out) throws IOException {
        this.in = new Scanner(in);
        this.out = new DataOutputStream(out);
        this.support = new PropertyChangeSupport(this);
        hasStart = false;
    }

    /**
     * Listens to the server's requests If "start" is received,
     * the server opens for client requests and if "close" the server closes
     * for client requests and the cache is updated in the file
     */
    @Override
    public void run() {
        while (true) {
            write("Please enter your command\n");
            String nextCommand = in.nextLine();
            if (nextCommand.equals("start") && !hasStart) {
                support.firePropertyChange("command",null, nextCommand);
                write("Starting server.......\n\n");
                hasStart = true;
            } else if (nextCommand.equals("stop")) {
                support.firePropertyChange("command",null, nextCommand);
                write("Shutdown server\n\n");
                hasStart = false;
            } else {
                write("Not a valid command\n\n");
            }
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);

    }

    public void removePropertyChangeListener(java.beans.PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);

    }

    public void write(String string) {
        try {
            out.writeChars(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

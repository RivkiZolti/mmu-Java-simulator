package main.java.com.hit.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * the GUI part
 */
public class CacheUnitView extends Object {
    private PropertyChangeSupport support;
    private JLabel statistics;
    private JLabel resultToShow;
    protected String statisticString;
    private boolean isStatisticsShown = false;

    public CacheUnitView() {
        support = new PropertyChangeSupport(this);
    }

    /**
     *  auxiliary class for the button responsible load request
     */
    public class ButtonOpenFile implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ea) {
            String fileName = null;
            JFileChooser fileChooser = new JFileChooser();
            File workingDirectory = new File(System.getProperty("user.dir"));
            fileChooser.setCurrentDirectory(workingDirectory);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue  == JFileChooser.APPROVE_OPTION) {
                fileName = fileChooser.getSelectedFile().getAbsolutePath();
            }
            File file = new File(fileName);

            try (FileReader fr = new FileReader(file)) {
                char[] chars = new char[(int) file.length()];
                fr.read(chars);

                String fileContent = new String(chars);
                support.firePropertyChange("json", null, fileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  auxiliary class for the button responsible show statistics
     */
    public class ButtonShowStatistics implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isStatisticsShown) {
                resultToShow.setText("");
                statistics.setText(statisticString);
            } else {
                statistics.setText("");
            }
            isStatisticsShown = !isStatisticsShown;
        }
    }
    /**
     *  auxiliary class for the buttons
     */
    public class Buttons extends JPanel {
        protected JButton b1, b2;

        public Buttons() {
            super(new FlowLayout());
            b1 = new JButton("📂 Load a Request");
            b2 = new JButton("⛽ Show Statistics");
            ButtonOpenFile bottonOpenFile = new ButtonOpenFile();
            ButtonShowStatistics buttonShowStatistics = new ButtonShowStatistics();
            b1.addActionListener(bottonOpenFile);
            b2.addActionListener(buttonShowStatistics);
            add(b1);
            add(b2);
        }
    }

    /**
     * responsible create and show the GUI
     */
    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("CachUnitUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        Buttons newContentPane = new Buttons();
        newContentPane.setOpaque(true);

        statistics = new JLabel();
        statisticString = "<html>capacity:<br/>algorithm:<br/>numRequests:<br/>numDM:<br/>numSwaps:<br/></html>";

        resultToShow = new JLabel();
        //Display the window.
        JPanel buttons = new JPanel();
        buttons.add(newContentPane);

        JPanel text = new JPanel();
        text.add(statistics);

        JPanel status = new JPanel();
        status.add(resultToShow);
        frame.setContentPane(buttons);
        frame.add(text);
        frame.add(status);

        frame.pack();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(java.beans.PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void start() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * calls from CacheUnitClientObserver in order to show the new statistics
     * @param t new statistics to show
     * @param <T>
     */
    public <T> void updateUIData(T t) {
        String[] parts = ((String) t).split(";");
        String ifSuccess = new String("");
        String getResultParts = new String("");
        if (parts[5].equals("null") || parts[5].equals("false")) {
            ifSuccess = "Failed";
        }
        else if (parts[5].equals("true")){
            ifSuccess = "Succeeded";
        }
        else {
           ifSuccess="<html>Succeeded<br/><br/>result of get:<br/>"+parts[5]+"</html>";
        }
        statistics.setText("");
        isStatisticsShown = false;
        resultToShow.setText(ifSuccess);
        statisticString = "<html><br/>capacity: " + parts[0] + "<br/>algorithm: " + parts[1] + "<br/>numRequests: " + parts[2] + "<br/>numDM: " + parts[3] + "<br/>numSwaps: " + parts[4] + "<br/></html>";
    }
}
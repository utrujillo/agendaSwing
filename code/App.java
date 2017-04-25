package code;

import javax.swing.*;

/**
 * Created by codehero on 25/04/17.
 */
public class App {
    public JPanel panelMain;
    private JLabel welcomeLabel;

    public static void main(String[] args) {
        new App().createWindow();
    }

    public void createWindow(){
        JFrame mainframe = new JFrame("Agenda - App");
        mainframe.setContentPane( new App().panelMain );
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setSize(300, 400);

        mainframe.setVisible(true);
    }
}

package dbApp.view.gui;

import javax.swing.JFrame;

public class MainWindow extends JFrame {

    public MainWindow() {
        super("Database App");
//        this.setBounds(100, 100, 960, 640);
        this.setSize(960, 640);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}

package dbApp.gui;

import java.awt.Dimension;
import javax.swing.JFrame;

public class SideWindow extends JFrame {

    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 400;

    public SideWindow(String title) {
        super(title);
        init(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    public SideWindow(String title, Dimension dimension) {
        super(title);
        init(dimension);
    }

    private void init(Dimension dimension) {
        this.setSize(dimension);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}

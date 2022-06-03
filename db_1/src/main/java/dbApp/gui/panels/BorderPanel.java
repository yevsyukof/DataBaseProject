package dbApp.gui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import javax.swing.JPanel;

public class BorderPanel extends JPanel {

    // Обычно используется только одно из значений
    // Т.к. панель используется в совокупности с BorderLayout
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;

    public BorderPanel(LayoutManager layoutManager) {
        super(layoutManager);
        init(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    public BorderPanel(LayoutManager layoutManager, Dimension preferredSize) {
        super(layoutManager);
        init(preferredSize);
    }

    private void init(Dimension dimension) {
        this.setPreferredSize(dimension);
//        this.setBackground(Color.BLUE);
    }
}

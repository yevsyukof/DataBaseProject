package dbApp.view.gui.panels.admin;

import dbApp.model.db.entities.AbstractTable;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JPanel;

public class TablesListPanel extends JPanel {

    public TablesListPanel(List<AbstractTable> tables) {
        super(new FlowLayout(FlowLayout.CENTER));
        init(tables);
    }

    private void init(List<AbstractTable> tables) {
        for (AbstractTable table : tables) {
            TableInfoPanel tableInfoPanel = new TableInfoPanel(table);
            this.add(tableInfoPanel);
        }

        this.setBackground(Color.LIGHT_GRAY);
    }
}

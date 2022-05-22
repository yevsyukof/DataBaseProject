package dbApp.view.gui.panels.admin;

import dbApp.model.db.entities.Table;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JPanel;

public class TablesListPanel extends JPanel {

    public TablesListPanel(List<Table> tables) {
        super(new FlowLayout(FlowLayout.CENTER));
        init(tables);
    }

    private void init(List<Table> tables) {
        for (Table table : tables) {
            TableInfoPanel tableInfoPanel = new TableInfoPanel(table);
            this.add(tableInfoPanel);
        }

        this.setBackground(Color.LIGHT_GRAY);
    }
}

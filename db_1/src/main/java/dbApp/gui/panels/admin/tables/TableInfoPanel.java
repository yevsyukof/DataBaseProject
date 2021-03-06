package dbApp.gui.panels.admin.tables;

import dbApp.gui.SideWindow;
import dbApp.db.entities.AbstractTable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class TableInfoPanel extends JPanel {

    private static final int DEFAULT_WIDTH = 280;
    private static final int DEFAULT_HEIGHT = 200;

    public TableInfoPanel(AbstractTable table) {
        super(new BorderLayout());
        init(table);
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    private void init(AbstractTable table) {
        JLabel tableName = new JLabel("Таблица:    " + table.getTranslatedName());
//        tableName.setFont(new Font("Verdana", Font.PLAIN, 14));
        this.add(tableName, BorderLayout.NORTH);

        JLabel columnsNamesListLabel = new JLabel("Столбцы: ");
        this.add(columnsNamesListLabel, BorderLayout.WEST);

        this.add(createColumnsNamesPanel(table), BorderLayout.CENTER);

        JButton openTableWindowButton = new JButton("Открыть");
        openTableWindowButton.addActionListener(e -> {
            ViewTablePanel viewTablePanel = new ViewTablePanel(
                new SideWindow(table.getTranslatedName()), table);
            viewTablePanel.run();
        });
        this.add(openTableWindowButton, BorderLayout.SOUTH);

        Border border = BorderFactory.createLineBorder(Color.GRAY);
        this.setBorder(border);
    }

    private JPanel createColumnsNamesPanel(AbstractTable table) {
        JPanel columnsPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        // определяет отступы между элементами сетки на этой панели
        gbc.insets = new Insets(4, 4, 4, 4);

        List<String> columnsList = table.getTranslatedColumnsNames();

        for (String columnName : columnsList) {
            JLabel columnNameLabel = new JLabel(columnName);
//            columnNameLabel.setHorizontalTextPosition(SwingConstants.LEFT);
            columnNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
            columnNameLabel.setFont(new Font("Verdana", Font.PLAIN, 14));

            columnsPanel.add(columnNameLabel, gbc);
            gbc.gridy++;
        }

        return columnsPanel;
    }
}

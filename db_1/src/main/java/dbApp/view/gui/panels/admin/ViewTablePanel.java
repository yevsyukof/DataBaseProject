package dbApp.view.gui.panels.admin;

import dbApp.model.db.entities.AbstractTable;
import dbApp.model.db.entities.AbstractTableRow;
import dbApp.view.gui.SideWindow;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewTablePanel extends JPanel {

    private static final int MIN_TABLE_WIDTH = 450;
    private static final int MIN_TABLE_HEIGHT = 220;

    private final SideWindow tableWindow;

    private final AbstractTable table;
    private List<AbstractTableRow> tableRows;

    private JTable tableModel;
    private JScrollPane tableScrollPane;
    private JLabel infoLabel;

    public ViewTablePanel(SideWindow tableWindow, AbstractTable table) {
        this.tableWindow = tableWindow;
        this.table = table;
    }

    private void init() {
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(8, 8, 8, 8);

        this.add(new JLabel(table.getTranslatedName()), gbc);
        gbc.gridy++;

        tableModel = new JTable();
        tableModel.setEnabled(false);
        tableScrollPane = new JScrollPane(tableModel);
        this.add(tableScrollPane, gbc);

        JPanel editButtonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 0;

        JButton addRowButton = new JButton("Добавить новый ряд");
        addRowButton.addActionListener(e -> {
            AddRowPanel addRowPanel = new AddRowPanel(tableWindow, this, table);
            addRowPanel.run();
        });
        editButtonsPanel.add(addRowButton, gbcButtons);

        gbcButtons.gridx++;
        JButton deleteRowButton = new JButton("Удалить выделенный ряд");
        deleteRowButton.addActionListener(e -> {
            if (tableModel.getSelectedRow() == -1) {
                setTextOnInfoLabel("Ряд не выбран", true);
            } else {
                int rowToDeleteIdx = tableModel.getSelectedRow();

                try {
                    table.deleteRow(tableRows.get(rowToDeleteIdx).getPrimaryKeyValue());

                    setTextOnInfoLabel("Ряд удален", false);
                    ((AdminViewTableModel) tableModel.getModel()).removeRow(
                        tableModel.getSelectedRow());
                } catch (SQLException ex) {
                    setTextOnInfoLabel(ex.getMessage(), true);
                }
            }
        });
        editButtonsPanel.add(deleteRowButton, gbcButtons);

        gbc.gridy++;
        this.add(editButtonsPanel, gbc);

        gbc.gridy++;
        infoLabel = new JLabel("\n");
        this.add(infoLabel, gbc);

        try {
            infoLabel.setForeground(Color.BLACK);
            infoLabel.setText("Получение данных таблицы...");
            tableWindow.revalidate();

            tableRows = table.getAllRows();

            tableModel = new JTable(
                new AdminViewTableModel(this, table, table.getAllRows()));
            tableModel.setEnabled(true);

            tableModel.setAutoCreateRowSorter(true);
            tableScrollPane.getViewport().removeAll();
            tableScrollPane.getViewport().add(tableModel);

            tableScrollPane.setMinimumSize(new Dimension(MIN_TABLE_WIDTH, MIN_TABLE_HEIGHT));
            tableScrollPane.setPreferredSize(new Dimension(
                MIN_TABLE_WIDTH + 400,
                MIN_TABLE_HEIGHT + 300));

            infoLabel.setText("\n");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTextOnInfoLabel(String labelText, boolean isError) {
        infoLabel.setForeground(
            isError
                ? Color.RED
                : Color.BLACK);

        infoLabel.setText(labelText);

        if (!isError) {
            tableWindow.revalidate();
            update(getGraphics());
        }
    }

    public void run() {
        this.removeAll();
        init();
        tableWindow.getContentPane().removeAll();
        tableWindow.getContentPane().add(this);
        this.update(getGraphics());
        tableWindow.revalidate();
    }
}

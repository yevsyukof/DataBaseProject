package dbApp.view.gui.panels.admin;

import dbApp.model.db.entities.Table;
import dbApp.view.gui.panels.SideWindow;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewTablePanel extends JPanel {

    private static final int MIN_TABLE_WIDTH = 450;
    private static final int MIN_TABLE_HEIGHT = 220;

    private final SideWindow tableWindow;

    private final Table table;

    private JTable tableModel;
    private JScrollPane tableScrollPane;
    private JLabel infoLabel;

    public ViewTablePanel(SideWindow tableWindow, Table table) {
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

        JPanel editButtons = new JPanel(new GridBagLayout());
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 0;

        JButton addRowButton = new JButton("Добавить новый ряд");
        addRowButton.addActionListener(e -> {
//            AddRowPanel addRowPanel = new AddRowPanel(mainFrame, this);
//            addRowPanel.start();
        });

        editButtons.add(addRowButton, gbcButtons);


        gbcButtons.gridx++;
        JButton deleteRowButton = new JButton("Удалить выделенный ряд");

//        deleteRowButton.addActionListener(e -> {      ///jTable == tableModel
//            if (tableModel.getSelectedRow() == -1) {
//                updateInfoLabel("Ряд не выбран", true);
//            }
//            else {
////                Row row = new Row();
////                for (int i = 0; i < jTable.getColumnCount(); ++i) {
////                    row.add(new Value(table.findFieldByName(jTable.getColumnName(i)),
////                        jTable.getModel().getValueAt(jTable.getSelectedRow(), i)));
////                }
////                String error = sqlExecutor.deleteRow(table, row);
////                if (!error.isEmpty()) {
////                    updateInfoLabel(error, true);
////                }
////                else {
////                    updateInfoLabel("Ряд удален", false);
////                    ((CustomTableModel)jTable.getModel()).removeRow(jTable.getSelectedRow());
////                }
//            }
//        });
        editButtons.add(deleteRowButton, gbcButtons);


        gbc.gridy++;
        this.add(editButtons, gbc);

        gbc.gridy++;
        infoLabel = new JLabel("\n");
        this.add(infoLabel, gbc);

        try {
            infoLabel.setForeground(Color.BLACK);
            infoLabel.setText("Получение данных таблицы...");
            tableWindow.revalidate();

            tableModel = new JTable(new AdminViewTableModel(this, table));
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
        } catch (NullPointerException ex) {
            System.err.println("нул поинтер ексепшен");
            ex.printStackTrace();
        }
    }

    public void updateInfoLabel(String labelText, boolean isError) {
        if (isError) {
            infoLabel.setForeground(Color.RED);
        }
        else {
            infoLabel.setForeground(Color.BLACK);
        }
        infoLabel.setText(labelText);
        if (!isError) {
            tableWindow.revalidate();
            update(getGraphics());
        }
    }

    public void run() {
        init();
        tableWindow.getContentPane().removeAll();
        tableWindow.getContentPane().add(this);
        this.update(getGraphics());
        tableWindow.revalidate();
    }
}

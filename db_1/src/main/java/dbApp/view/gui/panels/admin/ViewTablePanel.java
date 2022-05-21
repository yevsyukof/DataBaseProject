package dbApp.view.gui.panels.admin;

import dbApp.model.db.entities.Table;
import dbApp.view.gui.panels.SideWindow;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewTablePanel extends JPanel {

    private final SideWindow viewTableWindow;
    private final AdminMainMenuPanel adminMainMenuPanel;

    private final Table table;
    private ResultSet resultSet;
    private JTable jTable;
    private JScrollPane jScrollPane;
    private JLabel infoLabel;

    public ViewTablePanel(SideWindow viewTableWindow, AdminMainMenuPanel adminMainMenuPanel,
                                Table table) {
        this.viewTableWindow = viewTableWindow;
        this.adminMainMenuPanel = adminMainMenuPanel;
        this.table = table;
    }

    private void init() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(8, 8, 8, 8);

        add(new JLabel(table.getName()), gbc);
        gbc.gridy++;

        jTable = new JTable();
        jTable.setEnabled(false);
        jScrollPane = new JScrollPane(jTable);
        add(jScrollPane, gbc);

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

//        deleteRowButton.addActionListener(e -> {
//            if (jTable.getSelectedRow() == -1) {
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
        add(editButtons, gbc);

        gbc.gridy++;
        infoLabel = new JLabel("\n");
        add(infoLabel, gbc);

//        gbc.gridy++;
//        JButton closeButton = new JButton("Вернуться к выбору таблицы");
//        add(closeButton, gbc);
//        closeButton.addActionListener(e -> {
////            closeViewPanelWindow();
//        });

        try {
            infoLabel.setForeground(Color.BLACK);
            infoLabel.setText("Получение данных таблицы...");
            viewTableWindow.revalidate();
//            resultSet = sqlExecutor.getAllTableValues(table);
//            jTable = new JTable(new CustomTableModel(resultSet, table, sqlExecutor, this));
//            if (sqlExecutor.getLogin().equals(SecretProperties.DB_BUYER_LOGIN) ||
//                sqlExecutor.getLogin().equals(SecretProperties.DB_MANAGER_LOGIN) &&
//                    table.getAccess() != 1) {
//                jTable.setEnabled(false);
//            }
            jTable.setAutoCreateRowSorter(true);
            jScrollPane.getViewport().removeAll();
            jScrollPane.getViewport().add(jTable);
            infoLabel.setText("\n");
        } catch (RuntimeException e) {

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
            viewTableWindow.revalidate();
            update(getGraphics());
        }
    }

    public void start() {
        init();
        viewTableWindow.getContentPane().removeAll();
        viewTableWindow.getContentPane().add(this);
        this.update(getGraphics());
        viewTableWindow.revalidate();
    }

//    private void closeViewPanelWindow() {
//        viewTableWindow.
//        chooseTablePanel.start();
//    }
}

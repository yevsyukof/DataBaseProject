package dbApp.gui.panels.admin.tables;

import dbApp.gui.SideWindow;
import dbApp.db.entities.AbstractTable;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AddRowPanel extends JPanel {

    private final SideWindow addRowWindow;
    private final ViewTablePanel viewTablePanel;

    private final AbstractTable table;

    public AddRowPanel(SideWindow addRowWindow, ViewTablePanel viewTablePanel,
            AbstractTable table) {
        this.addRowWindow = addRowWindow;
        this.viewTablePanel = viewTablePanel;
        this.table = table;
    }

    private void init() {
        setLayout(new GridBagLayout());

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(8, 8, 8, 8);

        this.add(new JLabel(table.getTranslatedName()), gbc);
        gbc.gridy++;

        ArrayList<JTextField> textFields = new ArrayList<>();

        for (String translatedColumnName : table.getTranslatedColumnsNames()) {
            if (table.getPrimaryKeyComponentsNames().contains(translatedColumnName)) {
                continue;
            }

            gbc.gridx = 0;
            fieldsPanel.add(new JLabel(translatedColumnName), gbc);
            gbc.gridx++;
            JTextField textField = new JTextField(20);
            textFields.add(textField);
            fieldsPanel.add(textField, gbc);
            gbc.gridy++;
        }

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(fieldsPanel, gbc);
        JLabel infoLabel = new JLabel("\n");

        gbc.gridy++;
        JButton addButton = new JButton("Добавить");
        addButton.addActionListener(e -> {
            infoLabel.setForeground(Color.BLACK);
            infoLabel.setText("Запрос выполняется...");

            addRowWindow.revalidate();
            this.update(getGraphics());

            ArrayList<String> fieldsValues = new ArrayList<>();
            for (JTextField textField : textFields) {
                fieldsValues.add(textField.getText());
            }

            try {
                table.addRow(fieldsValues);
                infoLabel.setText("Данные добавлены");

                if (viewTablePanel != null) {
                    viewTablePanel.run();
                } else {
                    addRowWindow.dispatchEvent(
                        new WindowEvent(addRowWindow, WindowEvent.WINDOW_CLOSING));
                }
            } catch (SQLException ex) {
                infoLabel.setForeground(Color.RED);
                infoLabel.setText(ex.getMessage());
            }
        });
        this.add(addButton, gbc);

        gbc.gridy++;
        this.add(infoLabel, gbc);

        gbc.gridy++;
    }

    public void run() {
        this.removeAll();
        init();
        addRowWindow.getContentPane().removeAll();
        addRowWindow.getContentPane().add(this);
        this.update(getGraphics());
        addRowWindow.revalidate();
    }
}
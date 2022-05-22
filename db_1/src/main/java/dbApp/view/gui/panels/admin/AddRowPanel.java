package dbApp.view.gui.panels.admin;

import dbApp.model.db.entities.AbstractTable;
import dbApp.view.gui.SideWindow;
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

            Row row = new Row();
            int i = 0;
            for (Field field : table.getFields()) {
                if (field.getName().equals("id")) {
                    continue;
                }
                String fieldText = textFields.get(i).getText();
                if (fieldText.isEmpty()) {
                    i++;
                    continue;
                }
                row.add(new Value(field, fieldText));
                i++;
            }
            String error = sqlExecutor.insertValue(table, row);
            if (error.isEmpty()) {
                infoLabel.setText("Данные добавлены");
            }
            else {
                infoLabel.setForeground(Color.RED);
                infoLabel.setText(error);
            }
        });
        this.add(addButton, gbc);

        gbc.gridy++;
        this.add(infoLabel, gbc);

        gbc.gridy++;
    }

    public void run() {
        removeAll();
        init();
        addRowWindow.getContentPane().removeAll();
        addRowWindow.getContentPane().add(this);
        update(getGraphics());
        addRowWindow.revalidate();
    }
}
package dbApp.view.gui.panels.admin.tables;

import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTable;
import dbApp.model.db.entities.AbstractTableRow;
import dbApp.view.gui.SideWindow;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UpdateRowPanel extends JPanel {

    private final SideWindow updateRowWindow;
    private final ViewTablePanel viewTablePanel;

    private final AbstractTable table;
    private AbstractTableRow rowToUpdate;

    public UpdateRowPanel(SideWindow updateRowWindow, ViewTablePanel viewTablePanel,
        AbstractTable table, AbstractTableRow rowToUpdate) {
        this.updateRowWindow = updateRowWindow;
        this.viewTablePanel = viewTablePanel;
        this.table = table;
        this.rowToUpdate = rowToUpdate;
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
        List<Object> rowFieldsValues = rowToUpdate.getFieldsValues();
        List<String> translatedColumnsNames = table.getTranslatedColumnsNames();

        for (int i = 0; i < translatedColumnsNames.size(); ++i) {
            gbc.gridx = 0;
            fieldsPanel.add(new JLabel(translatedColumnsNames.get(i)), gbc);
            gbc.gridx++;

            JTextField textField = new JTextField(20);
            textField.setText(String.valueOf(rowFieldsValues.get(i)));

            textFields.add(textField);
            fieldsPanel.add(textField, gbc);
            gbc.gridy++;
        }

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(fieldsPanel, gbc);
        JLabel infoLabel = new JLabel("\n");

        gbc.gridy++;
        JButton updateButton = new JButton("Обновить");
        updateButton.addActionListener(e -> {
            infoLabel.setForeground(Color.BLACK);
            infoLabel.setText("Запрос выполняется...");

            updateRowWindow.revalidate();
            this.update(getGraphics());

            ArrayList<String> textFieldsValues = new ArrayList<>();
            for (JTextField textField : textFields) {
                textFieldsValues.add(textField.getText());
            }

            try {
                AbstractPrimaryKey rowToUpdatePK = rowToUpdate.getPrimaryKeyValue();

                AbstractTableRow updatedRow = rowToUpdate.buildUpdatedCopy(textFieldsValues);

                table.updateRow(rowToUpdatePK, updatedRow);

                rowToUpdate = updatedRow;

                infoLabel.setText("Данные обновлены");

                viewTablePanel.run();
            } catch (SQLException ex) {
                infoLabel.setForeground(Color.RED);
                infoLabel.setText(ex.getMessage());
            }
        });
        this.add(updateButton, gbc);

        gbc.gridy++;
        this.add(infoLabel, gbc);

        gbc.gridy++;
    }

    public void run() {
        this.removeAll();
        init();
        updateRowWindow.getContentPane().removeAll();
        updateRowWindow.getContentPane().add(this);
        this.update(getGraphics());
        updateRowWindow.revalidate();
    }
}
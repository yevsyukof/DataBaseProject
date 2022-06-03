package dbApp.gui.panels.admin.actions;

import dbApp.db.DataBase;
import dbApp.db.actions.AddDrugAction;
import dbApp.gui.SideWindow;
import dbApp.gui.panels.BorderPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddDrugActionPanel extends JPanel {

    private final SideWindow addRowWindow;

    private final Map<String, Integer> possibleComponents;
    private final Map<String, Integer> possibleManufacturers;
    private final Map<String, Integer> possibleReleaseForms;

    private final Map<JComboBox<String>, JTextField> requiredComponentVolume
        = new LinkedHashMap<>();


    private final AddDrugAction addDrugAction;

    private JTextField drugNameTextField;
    private JTextField criticalRateTextField;
    private JTextField priceTextField;

    public AddDrugActionPanel(SideWindow addRowWindow, DataBase dataBase) throws SQLException {
        this.addRowWindow = addRowWindow;

        addDrugAction = new AddDrugAction(dataBase);

        possibleComponents = new LinkedHashMap<>();
        possibleComponents.put("Выберите компонент", null);
        possibleComponents.putAll(dataBase.getDrugs().getDrugsMap());


        possibleManufacturers = new LinkedHashMap<>();
        possibleManufacturers.put("Выберите производителя", null);
        possibleManufacturers.putAll(dataBase.getDrugManufacturers().getDrugManufacturersMap());

        possibleReleaseForms = new LinkedHashMap<>();
        possibleReleaseForms.put("Выберите форму выпуска", null);
        possibleReleaseForms.putAll(dataBase.getReleaseForms().getReleaseFormsMap());
    }

    private void init() {
        this.setLayout(new BorderLayout());

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new Insets(8, 8, 8, 8);
        initCentre(fieldsPanel, gbc);
        this.add(fieldsPanel, BorderLayout.CENTER);


        BorderPanel southBorder = new BorderPanel(
            new FlowLayout(FlowLayout.RIGHT, 20, 10),
            new Dimension(100, 50));
        initSouthBorder(southBorder, fieldsPanel, gbc);
        this.add(southBorder, BorderLayout.SOUTH);

    }

    private void initCentre(JPanel centre, GridBagConstraints gbc) {
        centre.add(new JLabel("Введите название"), gbc);
        gbc.gridx++;
        drugNameTextField = new JTextField(20);
        centre.add(drugNameTextField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JComboBox<String> releaseFromBox = new JComboBox<>(
            possibleReleaseForms.keySet().toArray(new String[0]));
        releaseFromBox.addActionListener(actionEvent -> {
            addDrugAction.setReleaseFormId(
                possibleReleaseForms.get((String) releaseFromBox.getSelectedItem()));
        });
        centre.add(releaseFromBox, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JComboBox<String> manufacturerBox = new JComboBox<>(
            possibleManufacturers.keySet().toArray(new String[0]));
        manufacturerBox.addActionListener(actionEvent -> {
            addDrugAction.setDrugManufacturerId(
                possibleReleaseForms.get((String) manufacturerBox.getSelectedItem()));
        });
        centre.add(manufacturerBox, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        centre.add(new JLabel("Введите нижнюю границу запаса"), gbc);
        gbc.gridx++;
        criticalRateTextField = new JTextField(20);
        centre.add(criticalRateTextField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        centre.add(new JLabel("Введите цену"), gbc);
        gbc.gridx++;
        priceTextField = new JTextField(20);
        centre.add(priceTextField, gbc);


        gbc.gridy++;
        gbc.gridx = 0;
        centre.add(new JLabel("Выберете компоненты"), gbc);

        gbc.gridx++;
        JComboBox<String> component1Box = new JComboBox<>(
            possibleComponents.keySet().toArray(new String[0]));
        component1Box.addActionListener(actionEvent -> {
            addDrugAction.addComponent(
                possibleReleaseForms.get((String) component1Box.getSelectedItem()));
        });
        centre.add(component1Box, gbc);

        component1Box.get

        gbc.gridx++;
        centre.add(new JLabel("Требуемое колво"));
        gbc.gridx++;
//        J
//        requiredComponentVolume.put()
    }

    private void initSouthBorder(BorderPanel southBorder,
                                     JPanel centre, GridBagConstraints gbc) {
        JLabel infoLabel = new JLabel("\n");
        southBorder.add(infoLabel);

        JButton addNewComponentButton = new JButton("Добавить компонент");
        addNewComponentButton.addActionListener(e -> {
            gbc.gridy++;
            gbc.gridx = 0;

            JLabel chooseComponentNameLabel = new JLabel("Выберите компонент: ");
            centre.add(chooseComponentNameLabel, gbc);

            gbc.gridx++;
            JComboBox<String> possibleComponentBox = new JComboBox<>(
                possibleComponents.keySet().toArray(new String[0]));
            possibleComponentBox.addActionListener(actionEvent -> {
                addDrugAction.addComponent(
                    possibleComponents.get((String) possibleComponentBox.getSelectedItem()));
            });
            centre.add(possibleComponentBox, gbc);

            addRowWindow.revalidate(); /// TODO: ВАЖНАЯ ШТУКЕНЦИЯ
        });
        southBorder.add(addNewComponentButton);

        JButton addButton = new JButton("Добавить");
        addButton.addActionListener(e -> {
            infoLabel.setForeground(Color.BLACK);
            infoLabel.setText("Запрос выполняется...");
            addRowWindow.revalidate();
            this.update(getGraphics());

            try {
                addDrugAction.setDrugName(drugNameTextField.getText());
                addDrugAction.setCriticalRate(Integer.valueOf(criticalRateTextField.getText()));
                addDrugAction.setPrice(Integer.valueOf(priceTextField.getText()));

                addDrugAction.addDrugIntoBase();
                infoLabel.setText("Данные добавлены");
            } catch (SQLException | IllegalArgumentException ex) {
                infoLabel.setForeground(Color.RED);
                infoLabel.setText(ex.getMessage());
            }
        });
        southBorder.add(addButton);
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

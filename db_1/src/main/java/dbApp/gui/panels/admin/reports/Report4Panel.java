package dbApp.gui.panels.admin.reports;

import dbApp.db.reports.ReportTable4;
import dbApp.gui.SideWindow;
import dbApp.db.DataBase;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Report4Panel extends JPanel implements Runnable {

    private final SideWindow reportWindow;

    private HashMap<String, Integer> possibleReleaseFormsMap;

    private final ReportTable4 reportTable4;

    public Report4Panel(SideWindow reportWindow, DataBase dataBase) {
        this.reportWindow = reportWindow;
        reportTable4 = new ReportTable4("Отчет 4", dataBase);
    }

    private void init() throws SQLException {
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(8, 8, 8, 8);

        ////////////////////////////// дальше scare
        JLabel info = new JLabel("Выберете тип вещества");
        this.add(info, gbc);

        possibleReleaseFormsMap = new LinkedHashMap<>();
        possibleReleaseFormsMap.put("Любой", null);
        possibleReleaseFormsMap.putAll(reportTable4.getPossibleReleaseForms());

        gbc.gridy++;
        JComboBox<String> possibleBox = new JComboBox<>(
            possibleReleaseFormsMap.keySet().toArray(new String[0]));
        possibleBox.addActionListener(e -> {
            reportTable4.setRequiredReleaseFormId(
                possibleReleaseFormsMap.get((String) possibleBox.getSelectedItem()));
        });
        this.add(possibleBox, gbc);
        ///////////////////////////

        gbc.gridy++;
        JLabel info1 = new JLabel("Выберете промежутки запроса");
        this.add(info1, gbc);

        gbc.gridy++;
        DatePickerPanel datePickerPanel = new DatePickerPanel(reportTable4);
        this.add(datePickerPanel, gbc);

        gbc.gridy++;
        JButton openQueryButton = new JButton("Выбрать");
        openQueryButton.addActionListener(e -> {
            try {
                datePickerPanel.setChosenDateParams();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                return;
            }
            ViewReportTablePanel viewReportTablePanel = new ViewReportTablePanel(
                reportWindow, reportTable4);
            viewReportTablePanel.run();
        });
        this.add(openQueryButton, gbc);

        Border border = BorderFactory.createLineBorder(Color.GRAY);
        this.setBorder(border);
    }

    @Override
    public void run() {
        this.removeAll();
        try {
            init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        reportWindow.getContentPane().removeAll();
        reportWindow.getContentPane().add(this);
        this.update(getGraphics());
        reportWindow.revalidate();
    }
}

package dbApp.gui.panels.admin.reports;

import dbApp.db.reports.ReportTable2;
import dbApp.db.DataBase;
import dbApp.gui.SideWindow;
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

public class Report2Panel extends JPanel implements Runnable {

    private final DataBase dataBase;
    private final SideWindow reportWindow;

    private HashMap<String, Integer> possibleReleaseFormsMap;

    public Report2Panel(SideWindow reportWindow, DataBase dataBase) {
        this.dataBase = dataBase;
        this.reportWindow = reportWindow;
    }

    private void init() throws SQLException {
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel queryName = new JLabel("Выберите тип лекарства");
        this.add(queryName, gbc);

        ////////////////////////////// дальше scare
        ReportTable2 reportTable2 = new ReportTable2("Клиенты, ждущие поставок лекарств",
            dataBase);

        possibleReleaseFormsMap = new LinkedHashMap<>();
        possibleReleaseFormsMap.put("Любой", null);
        possibleReleaseFormsMap.putAll(reportTable2.getPossibleReleaseForms());

        gbc.gridy++;
        JComboBox<String> possibleBox = new JComboBox<>(
            possibleReleaseFormsMap.keySet().toArray(new String[0]));
        possibleBox.addActionListener(e -> {
            reportTable2.setReleaseFrom(
                    possibleReleaseFormsMap.get((String) possibleBox.getSelectedItem()));
        });
        this.add(possibleBox, gbc);
        ///////////////////////////

        gbc.gridy++;
        JButton openQueryButton = new JButton("Выбрать");
        openQueryButton.addActionListener(e -> {
            ViewReportTablePanel viewReportTablePanel = new ViewReportTablePanel(
                reportWindow, reportTable2);
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

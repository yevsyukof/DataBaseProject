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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Report4Panel extends JPanel implements Runnable {

    private final SideWindow reportWindow;

    private HashMap<String, Integer> indexes;

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
        gbc.gridy++;

        var possibleQueryParams = reportTable4.getPossibleQueryParameters();

        indexes = new HashMap<>();
        String[] params = new String[possibleQueryParams.size() + 1];
        params[0] = "Любой";
        indexes.put(params[0], null);
        for (int i = 1; i <= possibleQueryParams.size(); i++) {
            params[i] = possibleQueryParams.get(i - 1).first();

            indexes.put(possibleQueryParams.get(i - 1).first(),
                (Integer) possibleQueryParams.get(i - 1).second());
        }

        JComboBox<String> possibleBox = new JComboBox<>(params);
        possibleBox.addActionListener(e -> {
            reportTable4.setRequiredReleaseFormId(indexes.get((String) possibleBox.getSelectedItem()));
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

package dbApp.gui.panels.admin.reports;

import dbApp.db.reports.ReportTable;
import dbApp.gui.SideWindow;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewReportTablePanel extends JPanel {

    private static final int MIN_TABLE_WIDTH = 450;
    private static final int MIN_TABLE_HEIGHT = 220;

    protected final SideWindow tableWindow;

    protected final ReportTable reportTable;

    public ViewReportTablePanel(SideWindow tableWindow, ReportTable reportTable) {
        this.tableWindow = tableWindow;
        this.reportTable = reportTable;
    }

    protected void init() {
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(8, 8, 8, 8);

        this.add(new JLabel(reportTable.getTranslatedName()), gbc);
        gbc.gridy++;

        JTable reportTableModel = new JTable();
        reportTableModel.setEnabled(false);
        JScrollPane tableScrollPane = new JScrollPane(reportTableModel);
        this.add(tableScrollPane, gbc);

        try {
            tableWindow.revalidate();

            reportTableModel = new JTable(
                new ReportTableModel(reportTable, reportTable.getReportRows()));
            reportTableModel.setEnabled(true);

            reportTableModel.setAutoCreateRowSorter(true);
            tableScrollPane.getViewport().removeAll();
            tableScrollPane.getViewport().add(reportTableModel);

            tableScrollPane.setMinimumSize(new Dimension(MIN_TABLE_WIDTH, MIN_TABLE_HEIGHT));
            tableScrollPane.setPreferredSize(new Dimension(
                MIN_TABLE_WIDTH + 400,
                MIN_TABLE_HEIGHT + 300));
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
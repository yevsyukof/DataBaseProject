package dbApp.gui.panels.admin.reports;

import dbApp.db.reports.ReportTable1;
import dbApp.gui.SideWindow;
import dbApp.db.reports.ReportTable;
import dbApp.db.DataBase;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Report1Panel extends JPanel implements Runnable {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 150;

    private final DataBase dataBase;
    private final SideWindow reportWindow;

    public Report1Panel(SideWindow reportWindow, DataBase dataBase) {
        this.reportWindow = reportWindow;
        this.dataBase = dataBase;
    }

    private void init() {
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel queryName = new JLabel("Клиенты не забравшие вовремя заказ");
        this.add(queryName, gbc);

        gbc.gridy++;
        JButton openQueryButton = new JButton("Открыть");
        openQueryButton.addActionListener(e -> {
            ReportTable reportTable = new ReportTable1(dataBase);

            ViewReportTablePanel viewTablePanel = new ViewReportTablePanel(reportWindow,
                reportTable);
            viewTablePanel.run();
        });
        this.add(openQueryButton, gbc);

        Border border = BorderFactory.createLineBorder(Color.GRAY);
        this.setBorder(border);
    }

    @Override
    public void run() {
        this.removeAll();
        init();
        reportWindow.getContentPane().removeAll();
        reportWindow.getContentPane().add(this);
        this.update(getGraphics());
        reportWindow.revalidate();
    }
}

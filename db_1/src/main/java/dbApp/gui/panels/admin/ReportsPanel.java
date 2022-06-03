package dbApp.gui.panels.admin;

import dbApp.gui.MainWindow;
import dbApp.gui.SideWindow;
import dbApp.db.reports.ReportTable;
import dbApp.gui.panels.admin.reports.ViewReportTablePanel;
import dbApp.gui.panels.admin.reports.Report2Panel;
import dbApp.gui.panels.admin.reports.Report3Panel;
import dbApp.gui.panels.admin.reports.Report4Panel;
import dbApp.db.DataBase;
import dbApp.db.reports.ReportTable1;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ReportsPanel extends JPanel implements Runnable {

    private final MainWindow mainWindow;
    private final AdminMainMenuPanel adminMainMenuPanel;

    private final DataBase dataBase;


    public ReportsPanel(MainWindow mainWindow, AdminMainMenuPanel adminMainMenuPanel,
            DataBase dataBase) {
        this.mainWindow = mainWindow;
        this.adminMainMenuPanel = adminMainMenuPanel;
        this.dataBase = dataBase;
    }

    private void init() {
        this.setLayout(new BorderLayout());

        JPanel centrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.add(centrePanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.add(southPanel, BorderLayout.SOUTH);
        JButton exitButton = new JButton("Назад");
        exitButton.addActionListener(e -> {
            adminMainMenuPanel.run();
        });
        southPanel.add(exitButton);

        JButton report1Button = new JButton("Отчет 1");
        report1Button.addActionListener(e -> {
            ReportTable reportTable = new ReportTable1(dataBase);

            ViewReportTablePanel viewTablePanel = new ViewReportTablePanel(
                new SideWindow("Отчет 1"), reportTable);
            viewTablePanel.run();
        });
        centrePanel.add(report1Button);

        JButton report2Button = new JButton("Отчет 2");
        report2Button.addActionListener(e -> {
            Report2Panel report2Panel = new Report2Panel(
                new SideWindow("Отчет 2"), dataBase);
            report2Panel.run();
        });
        centrePanel.add(report2Button);

        JButton report3Button = new JButton("Отчет 3");
        report3Button.addActionListener(e -> {
            Report3Panel report3Panel = new Report3Panel(
                new SideWindow("Отчет 3"), dataBase);
            report3Panel.run();
        });
        centrePanel.add(report3Button);

        JButton report4Button = new JButton("Отчет 4");
        report4Button.addActionListener(e -> {
            Report4Panel report4Panel = new Report4Panel(
                new SideWindow("Отчет 4"), dataBase
            );
            report4Panel.run();
        });
        centrePanel.add(report4Button);

        for (int i = 5; i <= 13; ++i) {
            centrePanel.add(new JButton("Отчет " + i));
        }
    }

    @Override
    public void run() {
        this.removeAll();
        init();
        mainWindow.getContentPane().removeAll();
        mainWindow.getContentPane().add(this);
        this.update(this.getGraphics());
        mainWindow.revalidate();
    }
}

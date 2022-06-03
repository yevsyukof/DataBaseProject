package dbApp.gui.panels.admin;

import dbApp.db.reports.ReportTable6;
import dbApp.db.reports.ReportTable8;
import dbApp.db.reports.ReportTable9;
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

        JButton report1Button = new JButton(
            "Покупатели, которые не пришли вовремя забрать заказ");
        report1Button.addActionListener(e -> {
            ReportTable reportTable = new ReportTable1(dataBase);

            ViewReportTablePanel viewTablePanel = new ViewReportTablePanel(
                new SideWindow("Отчет 1"), reportTable);
            viewTablePanel.run();
        });
        centrePanel.add(report1Button);

        JButton report2Button = new JButton(
            "Покупатели, ждущие поставок медикаментов");
        report2Button.addActionListener(e -> {
            Report2Panel report2Panel = new Report2Panel(
                new SideWindow("Отчет 2"), dataBase);
            report2Panel.run();
        });
        centrePanel.add(report2Button);

        JButton report3Button = new JButton(
            "10 наиболее часто используемых медикаментов");
        report3Button.addActionListener(e -> {
            Report3Panel report3Panel = new Report3Panel(
                new SideWindow("Отчет 3"), dataBase);
            report3Panel.run();
        });
        centrePanel.add(report3Button);

        JButton report4Button = new JButton(
            "Статистика использования веществ");
        report4Button.addActionListener(e -> {
            Report4Panel report4Panel = new Report4Panel(
                new SideWindow("Отчет 4"), dataBase
            );
            report4Panel.run();
        });
        centrePanel.add(report4Button);

        JButton report6Button = new JButton(
            "Лекарства, достигшие критической нормы");
        report6Button.addActionListener(e -> {
            ReportTable reportTable = new ReportTable6(dataBase);

            ViewReportTablePanel viewTablePanel = new ViewReportTablePanel(
                new SideWindow("Отчет 6"), reportTable);
            viewTablePanel.run();
        });
        centrePanel.add(report6Button);

        JButton report8Button = new JButton(
            "Заказы в производстве");
        report8Button.addActionListener(e -> {
            ReportTable reportTable = new ReportTable8(dataBase);

            ViewReportTablePanel viewTablePanel = new ViewReportTablePanel(
                new SideWindow("Отчет 8"), reportTable);
            viewTablePanel.run();
        });
        centrePanel.add(report8Button);

        JButton report9Button = new JButton(
            "Отчет по недостающим заказам препаратам");
        report9Button.addActionListener(e -> {
            ReportTable reportTable = new ReportTable9(dataBase);

            ViewReportTablePanel viewTablePanel = new ViewReportTablePanel(
                new SideWindow("Отчет 9"), reportTable);
            viewTablePanel.run();
        });
        centrePanel.add(report9Button);

//        for (int i = 5; i <= 13; ++i) {
//            centrePanel.add(new JButton("Отчет " + i));
//        }
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

package dbApp.view.gui.panels.admin;

import dbApp.model.db.DataBase;
import dbApp.view.gui.SideWindow;
import dbApp.view.gui.panels.admin.reports.ViewReportTablePanel;
import dbApp.view.gui.panels.admin.reports.ReportTable;
import dbApp.view.gui.MainWindow;
import dbApp.view.gui.panels.admin.reports.report_1.EnterReport1Panel;
import dbApp.view.gui.panels.admin.reports.report_1.ReportTable1;
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
        this.setLayout(new FlowLayout());

//        JButton report1 = new JButton("Отчет по клиентам");
//        report1.addActionListener(e -> {
//            ReportTable reportTable = new ReportTable1(dataBase);
//
//            ViewReportTablePanel viewTablePanel = new ViewReportTablePanel(
//                new SideWindow(reportTable.getTranslatedName()), reportTable);
//            viewTablePanel.run();
//        });
//        this.add(report1);

        EnterReport1Panel report1Panel = new EnterReport1Panel(dataBase);
        this.add(report1Panel);

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

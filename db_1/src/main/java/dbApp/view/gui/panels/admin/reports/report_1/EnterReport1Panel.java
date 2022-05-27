package dbApp.view.gui.panels.admin.reports.report_1;

import dbApp.model.db.DataBase;
import dbApp.model.db.entities.AbstractTable;
import dbApp.view.gui.SideWindow;
import dbApp.view.gui.panels.admin.reports.ReportTable;
import dbApp.view.gui.panels.admin.reports.ViewReportTablePanel;
import dbApp.view.gui.panels.admin.tables.ViewTablePanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class EnterReport1Panel extends JPanel {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 150;

    private final DataBase dataBase;

    public EnterReport1Panel(DataBase dataBase) {
        super(new BorderLayout());
        this.dataBase = dataBase;
        init();
        this.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    private void init() {
        JLabel queryName = new JLabel("Клиенты не забравшие вовремя заказ");
        this.add(queryName, BorderLayout.NORTH);

        JButton openQueryButton = new JButton("Открыть");
        openQueryButton.addActionListener(e -> {
            ReportTable reportTable = new ReportTable1(dataBase);

            ViewReportTablePanel viewTablePanel = new ViewReportTablePanel(
                new SideWindow(reportTable.getTranslatedName()), reportTable);
            viewTablePanel.run();
        });
        this.add(openQueryButton, BorderLayout.SOUTH);

        Border border = BorderFactory.createLineBorder(Color.GRAY);
        this.setBorder(border);
    }
}

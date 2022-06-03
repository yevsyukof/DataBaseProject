package dbApp.gui.panels.admin;

import dbApp.gui.MainWindow;
import dbApp.gui.SideWindow;
import dbApp.gui.panels.BorderPanel;
import dbApp.gui.panels.admin.actions.AddDrugActionPanel;
import dbApp.gui.panels.admin.tables.AddRowPanel;
import dbApp.gui.panels.admin.tables.TablesListPanel;
import dbApp.db.DataBase;
import dbApp.gui.panels.LoginPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdminMainMenuPanel extends JPanel implements Runnable {

    private final MainWindow mainWindow;
    private final LoginPanel loginPanel;

    private final DataBase dataBase;

    public AdminMainMenuPanel(MainWindow mainWindow, DataBase dataBase, LoginPanel loginPanel) {
        this.mainWindow = mainWindow;
        this.dataBase = dataBase;
        this.loginPanel = loginPanel;

        this.setLayout(new BorderLayout());
    }

    private void init() {
        BorderPanel northBorder = new BorderPanel(
            new FlowLayout(FlowLayout.CENTER, 100, 10),
            new Dimension(100, 30));
        initNorthBorder(northBorder);
        this.add(northBorder, BorderLayout.NORTH);

        BorderPanel southBorder = new BorderPanel(
            new FlowLayout(FlowLayout.RIGHT, 20, 10),
            new Dimension(100, 50));
        initSouthBorder(southBorder);
        this.add(southBorder, BorderLayout.SOUTH);

        TablesListPanel tablesListPanel = new TablesListPanel(dataBase.getTables());
        this.add(tablesListPanel, BorderLayout.CENTER);
    }

    private void initNorthBorder(BorderPanel northBorder) {
        JLabel roleLabel = new JLabel("Ваша роль: Администратор");
        roleLabel.setHorizontalAlignment(JLabel.LEFT);
        roleLabel.setFont(new Font("Verdana", Font.PLAIN, 16));
        northBorder.add(roleLabel);

        JLabel label = new JLabel("Система управления аптекой");
        label.setFont(new Font("Verdana", Font.PLAIN, 12));
        northBorder.add(label);
    }

    private void initSouthBorder(BorderPanel southBorder) {
        JButton addNewDrugButton = new JButton("Внести новое лекарство в базу");
        addNewDrugButton.addActionListener(e -> {
            try {
                SideWindow addDrugWindow = new SideWindow("Внесение лекарства");
                AddDrugActionPanel addDrugActionPanel = new AddDrugActionPanel(
                    addDrugWindow, dataBase);
                addDrugActionPanel.run();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        southBorder.add(addNewDrugButton);


        JButton reportsButton = new JButton("Отчеты по работе аптеки");
        reportsButton.addActionListener(e -> {
            ReportsPanel reportsPanel = new ReportsPanel(mainWindow, this,
                dataBase);
            reportsPanel.run();
        });
        southBorder.add(reportsButton);


        JButton exitButton = new JButton("Выйти");
        exitButton.addActionListener(e -> {
            dataBase.close();
            loginPanel.run();
        });
        southBorder.add(exitButton);
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

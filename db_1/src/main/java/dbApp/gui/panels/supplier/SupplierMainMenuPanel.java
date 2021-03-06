package dbApp.gui.panels.supplier;

import dbApp.gui.MainWindow;
import dbApp.gui.ViewConstants;
import dbApp.gui.panels.LoginPanel;
import dbApp.db.DataBase;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SupplierMainMenuPanel extends JPanel implements Runnable {

    private final MainWindow mainWindow;
    private final DataBase dataBase;
    private final LoginPanel loginPanel;

    public SupplierMainMenuPanel(MainWindow mainWindow, DataBase dataBase, LoginPanel loginPanel) {
        this.mainWindow = mainWindow;
        this.dataBase = dataBase;
        this.loginPanel = loginPanel;

        this.setLayout(new GridBagLayout());
    }

    private void init() {
        JPanel tablesBlock = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(
            ViewConstants.INSETS,
            ViewConstants.INSETS,
            ViewConstants.INSETS,
            ViewConstants.INSETS);

        JLabel infoBlock = new JLabel("");
        tablesBlock.add(infoBlock, gbc);
        this.add(tablesBlock, gbc);

        gbc.gridy++;
        JButton readRowCountButton = new JButton("Узнать количество строк в таблице");
        this.add(readRowCountButton, gbc);

        gbc.gridy++;
        JButton exitButton = new JButton("Выйти");
        this.add(exitButton, gbc);

        readRowCountButton.addActionListener(e -> {
//            infoBlock.setForeground(Color.RED);
//            infoBlock.setText("Текущее количество записей в таблице: "
//                + dataBase.getTechnologiesTable().getAllRows().size());
        });

        exitButton.addActionListener(e -> {
            dataBase.close();
            loginPanel.run();
        });
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

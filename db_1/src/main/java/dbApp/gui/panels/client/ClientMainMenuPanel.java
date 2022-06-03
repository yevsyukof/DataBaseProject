package dbApp.gui.panels.client;

import dbApp.gui.MainWindow;
import dbApp.gui.ViewConstants;
import dbApp.db.DataBase;
import dbApp.gui.panels.LoginPanel;
import dbApp.gui.panels.admin.AdminMainMenuPanel;
import dbApp.gui.panels.supplier.SupplierMainMenuPanel;
import dbApp.utils.PropertiesService;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ClientMainMenuPanel extends JPanel implements Runnable {

    private final MainWindow mainWindow;
    private final DataBase dataBase;
    private final LoginPanel loginPanel;

    public ClientMainMenuPanel(MainWindow mainWindow, DataBase dataBase, LoginPanel loginPanel) {
        this.mainWindow = mainWindow;
        this.dataBase = dataBase;
        this.loginPanel = loginPanel;

        this.setLayout(new GridBagLayout());
    }

    private void init() {
//        this.setLayout(new GridBagLayout());
//
//        JPanel authorizationBlock = new JPanel(new GridBagLayout());
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        // определяет отступы между элементами сетки на этой панели
//        gbc.insets = new Insets(
//            ViewConstants.INSETS,
//            ViewConstants.INSETS,
//            ViewConstants.INSETS,
//            ViewConstants.INSETS);
//
//        authorizationBlock.add(new JLabel("DATABASE SERVER URL"), gbc);
//        gbc.gridy++;
//        authorizationBlock.add(new JLabel("Введите ваш номер телефона"), gbc);
//        gbc.gridy++;
//        authorizationBlock.add(new JLabel("Пароль"), gbc);
//
//        gbc.gridy = 0;
//        gbc.gridx++;
//
//        JTextField urlField = new JTextField(ViewConstants.URL_FIELD_WIDTH);
//        authorizationBlock.add(urlField, gbc);
//        gbc.gridy++;
//        JTextField loginField = new JTextField(ViewConstants.LOGIN_FIELD_WIDTH);
//        authorizationBlock.add(loginField, gbc);
//        gbc.gridy++;
//        JPasswordField passwordField = new JPasswordField(ViewConstants.PASSWORD_FIELD_WIDTH);
//        authorizationBlock.add(passwordField, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        this.add(authorizationBlock, gbc);
//
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        JButton loginButton = new JButton("Войти");
//        this.add(loginButton, gbc);
//
//        gbc.gridy++;
//        JLabel infoBlock = new JLabel("\n");
//        this.add(infoBlock, gbc);
//
//        gbc.gridy++;
//        JButton setDefaultUrlButton = new JButton("Вставить дефолтный URL");
//        this.add(setDefaultUrlButton, gbc);
//
//        gbc.gridy++;
//        JButton setDefaultProfileButton = new JButton("Вставить данные профиля читателя");
//        this.add(setDefaultProfileButton, gbc);
//
//
//        setDefaultUrlButton.addActionListener(e -> {
//            urlField.setText(PropertiesService.DEFAULT_DB_URL);
//        });
//
//        setDefaultProfileButton.addActionListener(e -> {
//            loginField.setText(PropertiesService.READER_USERNAME);
//            passwordField.setText(PropertiesService.READER_PASSWORD);
//        });
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

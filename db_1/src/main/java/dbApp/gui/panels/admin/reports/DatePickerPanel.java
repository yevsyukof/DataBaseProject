package dbApp.gui.panels.admin.reports;


import dbApp.db.reports.ReportTable4;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

import org.jdatepicker.model.UtilDateModel;
import org.jdatepicker.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

public class DatePickerPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel lblFilter = null;
    private JLabel lblFrom = null;
    private JLabel lblTo = null;
    private JDatePickerImpl dtFrom = null;
    private JDatePickerImpl dtTo = null;

    private final Locale locale = new Locale("ru");
    private final String SPAN_normal = "<html><span style=\"font-weight:normal\">";
    private final String PATTERN_DATE = "dd.MM.yyyy";

    private final int FIRST_DAY_IDX = 1;

    private String from = null;
    private String to = null;

    private final ReportTable4 reportTable4;

    public DatePickerPanel(ReportTable4 reportTable4) {
        this.reportTable4 = reportTable4;
        this.setSize(440, 180);
        createGUI();
        this.setVisible(true);
    }


    private void createGUI() {
        final int WIDTH_PANEL = 268;
        JPanel pnlFilter = new JPanel();
        pnlFilter.setLayout(new BorderLayout());
        pnlFilter.setPreferredSize(new Dimension(WIDTH_PANEL, 80));
        Border border = BorderFactory.createCompoundBorder(
            BorderFactory.createEtchedBorder(),
            BorderFactory.createEmptyBorder(15, 10, 15, 4));
        pnlFilter.setBorder(border);

        lblFilter = new JLabel();
        lblFilter.setText(SPAN_normal + "Интересуемые периоды для выбора статистики");

        UtilDateModel modelFrom = new UtilDateModel();
        UtilDateModel modelTo = new UtilDateModel();
        JDatePanelImpl datePanelFrom = new JDatePanelImpl(modelFrom, locale);
        JDatePanelImpl datePanelTo = new JDatePanelImpl(modelTo, locale);

        Dimension dim = new Dimension(100, 20);
        Dimension dimLabel = new Dimension(30, 20);

        //-----------------------------------------------------
        lblFrom = new JLabel();
        lblFrom.setText(SPAN_normal + "c");
        lblFrom.setPreferredSize(dimLabel);

        dtFrom = new JDatePickerImpl(datePanelFrom, new DateLabelFormatter(PATTERN_DATE));
        dtFrom.setPreferredSize(new Dimension(145, 20));
        dtFrom.getJFormattedTextField().setBackground(Color.WHITE);
        dtFrom.setTextEditable(true);

        SpringLayout layoutFilter = new SpringLayout();
        JPanel pnlFrom = new JPanel(layoutFilter);

        pnlFrom.add(lblFrom);
        pnlFrom.add(dtFrom);
        pnlFrom.setPreferredSize(dim);
        pnlFrom.setSize(dim);
        layoutFilter.putConstraint(SpringLayout.WEST, dtFrom, 30, SpringLayout.EAST, lblFrom);
        //-----------------------------------------------------
        lblTo = new JLabel();
        lblTo.setText(SPAN_normal + "по");
        lblTo.setPreferredSize(dimLabel);

        dtTo = new JDatePickerImpl(datePanelTo, new DateLabelFormatter(PATTERN_DATE));
        dtTo.setPreferredSize(new Dimension(145, 20));
        dtTo.getJFormattedTextField().setBackground(Color.WHITE);

        JPanel pnlTo = new JPanel(layoutFilter);

        pnlTo.add(lblTo);
        pnlTo.add(dtTo);
        pnlTo.setPreferredSize(dim);
        pnlTo.setSize(dim);
        layoutFilter.putConstraint(SpringLayout.WEST, dtTo, 30, SpringLayout.EAST, lblTo);
        //-----------------------------------------------------
        pnlFilter.add(pnlFrom, BorderLayout.NORTH);
        pnlFilter.add(pnlTo, BorderLayout.SOUTH);
        //-----------------------------------------------------

        String CHOOSE = "Выбор";
        JButton chooseButton = new JButton(SPAN_normal + CHOOSE);
//        chooseButton.addActionListener(controller);

        final int WIDTH_BUTTON = 130;
        dim = new Dimension(WIDTH_BUTTON, 28);
        chooseButton.setPreferredSize(dim);

        // Панель кнопок управления
        JPanel pnlButtons = new JPanel(); // new GridLayout(3, 1, 5, 12));
//        pnlButtons.add(chooseButton);

        this.add(lblFilter, BorderLayout.NORTH);
        this.add(pnlFilter, BorderLayout.CENTER);
        this.add(pnlButtons, BorderLayout.SOUTH);
    }

    public void setChosenDateParams() throws SQLException {
        getChosenDates();
        reportTable4.setFrom(Date.valueOf(from));
        reportTable4.setTo(Date.valueOf(to));
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private void getChosenDates() throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (dtFrom.getModel().getValue() != null) {
            from = sdf.format(dtFrom.getModel().getValue());
        }
        if (dtTo.getModel().getValue() != null) {
            to = sdf.format(dtTo.getModel().getValue());
        }

        if (from == null || to == null) {
            JOptionPane.showMessageDialog(DatePickerPanel.this,
                "Вы не выбрали дату");
            throw new SQLException("Вы не выбрали дату");
        }

        dtFrom.getModel().setValue(null);
        dtTo.getModel().setValue(null);
    }
}

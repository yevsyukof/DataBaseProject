package dbApp.gui.panels.admin.reports;

import dbApp.db.reports.ReportTable;
import dbApp.db.reports.ReportTableRow;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ReportTableModel extends AbstractTableModel {

    private final ReportTable table;

    private final List<ReportTableRow> tableRows;

    public ReportTableModel(ReportTable table, List<ReportTableRow> tableRows) {
        this.table = table;
        this.tableRows = tableRows;
    }

    @Override
    public int getRowCount() {
        return tableRows.size();
    }

    @Override
    public int getColumnCount() {
        return table.getTranslatedColumnsNames().size();
    }

    @Override
    public String getColumnName(int column) {
        return table.getTranslatedColumnsNames().get(column);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return tableRows.get(rowIndex).getField(columnIndex);
    }
}

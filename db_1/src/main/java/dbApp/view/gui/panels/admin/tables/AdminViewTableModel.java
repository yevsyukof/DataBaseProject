package dbApp.view.gui.panels.admin.tables;

import dbApp.model.db.entities.AbstractTableRow;
import dbApp.model.db.entities.AbstractTable;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class AdminViewTableModel extends AbstractTableModel {

    private final AbstractTable table;

    private List<AbstractTableRow> tableRows;

    public AdminViewTableModel(AbstractTable table, List<AbstractTableRow> tableRows)
            throws SQLException {
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

    public void removeRow(int rowIndex) {
        tableRows.remove(rowIndex);
        super.fireTableRowsDeleted(rowIndex, rowIndex);
        super.fireTableDataChanged();
    }
}

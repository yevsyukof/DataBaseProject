package dbApp.view.gui.panels.admin;

import dbApp.model.db.entities.TableRow;
import dbApp.model.db.entities.Table;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class AdminViewTableModel extends AbstractTableModel {

    private final Table table;

    private List<TableRow> tableRows;

    private final ViewTablePanel viewTablePanel;

    public AdminViewTableModel(ViewTablePanel viewTablePanel, Table table) throws SQLException {
        this.viewTablePanel = viewTablePanel;
        this.table = table;
        this.tableRows = table.readAll();
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

//    @Override
//    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//        viewTablePanel.updateInfoLabel("Значение в таблице обновляется...", false);
//
//        Row row = new Row();
//        for (int i = 0; i < columnNames.size(); ++i) {
//            row.add(new Value(table.findFieldByName(columnNames.get(i)), rows.get(rowIndex).get(i)));
//        }
//        try {
//            sqlExecutor.updateValue(table,
//                new Value(table.findFieldByName(columnNames.get(columnIndex)), aValue),
//                row);
//            rows.get(rowIndex).set(columnIndex, aValue);
//            super.setValueAt(aValue, rowIndex, columnIndex);
//            viewTablePanel.updateInfoLabel("Значение в таблице обновлено", false);
//        }
//        catch (SQLException ex) {
//            String error = ex.getMessage();
//            if (error.length() > SecretProperties.MAX_ERROR_LENGTH) {
//                error = error.substring(0, SecretProperties.MAX_ERROR_LENGTH) + "...";
//            }
//            viewTablePanel.updateInfoLabel(error, true);
//        }
//    }

//    @Override
//    public boolean isCellEditable(int rowIndex, int columnIndex) {
//        try {
//            return metaData.isWritable(columnIndex + 1);
//        }
//        catch (SQLException ex) {
//            return false;
//        }
//    }

//    public void removeRow(int rowIndex) {
//        rows.remove(rowIndex);
//        super.fireTableRowsDeleted(rowIndex, rowIndex);
//        super.fireTableDataChanged();
//    }
}

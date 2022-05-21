package dbApp.model.db.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface Table {

    void createTable() throws SQLException;

    void dropTable() throws SQLException;

    void addRow(TableRow newRow) throws SQLException;

    void deleteRow(PrimaryKey primaryKey) throws SQLException;

    void updateRow(PrimaryKey primaryKey, TableRow newRowValue) throws SQLException;

    List<TableRow> readAll() throws SQLException;

    List<String> getColumnsNames();

    String getName();
}

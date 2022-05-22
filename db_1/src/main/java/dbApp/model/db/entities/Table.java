package dbApp.model.db.entities;

import dbApp.model.db.DataBase.DBService;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Сервисный родительский класс, куда вынесена реализация общих действий для всех таблиц
public abstract class Table {

    protected final Set<String> columnsNames;
    protected final Set<String> translatedColumnsNames;
    protected final Map<String, Integer> columnsIndexes;

    protected final DBService dbService;

    protected final String tableName;

    public Table(String tableName, DBService dbService) throws SQLException {
        this.tableName = tableName;
        this.dbService = dbService;

        columnsNames = new LinkedHashSet<>();
        translatedColumnsNames = new LinkedHashSet<>();
        columnsIndexes = new HashMap<>();
        loadColumns();
    }

    protected void loadColumns() {  }

    public void createTable() throws SQLException {  }

    public void dropTable() throws SQLException {  }

    public void addRow(TableRow newRow) throws SQLException {  }

    public void deleteRow(PrimaryKey primaryKey) throws SQLException {  }

    public void updateRow(PrimaryKey primaryKey, TableRow updatedRow) throws SQLException {  }

    public List<TableRow> readAll() throws SQLException {
        return null;
    }

    public List<String> getColumnsNames() {
        return columnsNames.stream().toList();
    }

    public List<String> getTranslatedColumnsNames() {
        return translatedColumnsNames.stream().toList();
    }

    public int getColumIndex(String columnName) {
        return columnsIndexes.get(columnName);
    }

    public String getName() {
        return tableName;
    }

    public String getTranslatedName() {
        return null;
    }
}

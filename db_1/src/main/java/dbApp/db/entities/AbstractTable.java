package dbApp.db.entities;

import dbApp.db.DataBase.DBService;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

// Сервисный родительский класс, куда вынесена реализация общих действий для всех таблиц
public abstract class AbstractTable {

    protected final Set<String> primaryKeyComponentsNames;
    protected final Set<String> columnsNames;
    protected final Set<String> translatedColumnsNames;

//    protected final Map<String, Integer> columnsIndexes;

    protected final DBService dbService;

    protected final String tableName;

    public AbstractTable(String tableName, DBService dbService) {
        this.tableName = tableName;
        this.dbService = dbService;

        primaryKeyComponentsNames = new LinkedHashSet<>();
        columnsNames = new LinkedHashSet<>();
        translatedColumnsNames = new LinkedHashSet<>();

//        columnsIndexes = new HashMap<>();

        loadColumns();
    }

    protected abstract void loadColumns();

    public abstract void addRow(List<String> fieldsValues) throws SQLException;

    public abstract void deleteRow(AbstractPrimaryKey primaryKeyValue) throws SQLException;

    public abstract void updateRow(AbstractPrimaryKey primaryKeyValue,
        AbstractTableRow updatedRow) throws SQLException;

    public abstract List<AbstractTableRow> getAllRows() throws SQLException;

    public List<String> getColumnsNames() {
        return columnsNames.stream().toList();
    }

    public List<String> getTranslatedColumnsNames() {
        return translatedColumnsNames.stream().toList();
    }

    public int getColumIndex(String columnName) {
//        return columnsIndexes.get(columnName);
        return -1; // TODO
    }

    public String getName() {
        return tableName;
    }

    public abstract String getTranslatedName();

    public Set<String> getPrimaryKeyComponentsNames() {
        return primaryKeyComponentsNames;
    }
}

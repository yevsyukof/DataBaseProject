package dbApp.db.reports;

import dbApp.db.DataBase;
import dbApp.utils.Pair;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Господи, лучше бы код к этом пакете никто в своей жизни никогда не видел!
public abstract class ReportTable {

    protected final DataBase dataBase;

    protected final String tableName;

    protected final Set<String> columnsNames;
    protected final Set<String> translatedColumnsNames;

    public ReportTable(String tableName, DataBase dataBase) {
        this.tableName = tableName;
        this.dataBase = dataBase;

        columnsNames = new LinkedHashSet<>();
        translatedColumnsNames = new LinkedHashSet<>();
    }

    protected void setColumnsNames(List<String> columnsNames) {
        this.columnsNames.addAll(columnsNames);
    }

    protected void setTranslatedColumnsNames(List<String> translatedColumnsNames) {
        this.translatedColumnsNames.addAll(translatedColumnsNames);
    }

    public List<String> getColumnsNames() {
        return columnsNames.stream().toList();
    }

    public List<String> getTranslatedColumnsNames() {
        return translatedColumnsNames.stream().toList();
    }

    public String getTranslatedName() {
        return tableName;
    }

    public abstract List<String> getPossibleQueryParameters() throws SQLException;

    public abstract List<ReportTableRow> getReportRows() throws SQLException;
}

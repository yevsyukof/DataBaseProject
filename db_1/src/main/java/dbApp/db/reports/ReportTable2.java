package dbApp.db.reports;

import dbApp.db.DataBase;
import dbApp.db.entities.AbstractTableRow;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Setter;

public class ReportTable2 extends ReportTable {

    @Setter
    private Integer releaseFrom = null;

    public ReportTable2(String tableName, DataBase dataBase) {
        super(tableName, dataBase);

        setTranslatedColumnsNames(dataBase.getClientsTable().getTranslatedColumnsNames());
        translatedColumnsNames.remove("id");

//        setColumnsNames(dataBase.getClientsTable().getColumnsNames());
//        columnsNames.remove("id");
    }

    public Map<String, Integer> getPossibleReleaseForms() throws SQLException {
        List<AbstractTableRow> rows = dataBase.getReleaseForms().getAllRows();

        Map<String, Integer> possibleReleaseForms = new LinkedHashMap<>();
        for (AbstractTableRow row : rows) {
            possibleReleaseForms.put(
                String.valueOf(row.getField(1)),
                (Integer) row.getField(0));
        }
        return possibleReleaseForms;
    }

    @Override
    public List<ReportTableRow> getReportRows() throws SQLException {
        ResultSet resultSet;

        if (releaseFrom == null) {
            String sql = """
            SELECT DISTINCT
                first_name, last_name, address, phone_number
            FROM order_to_missing_drugs otmd
            JOIN orders o on o.id = otmd.order_id
            JOIN clients c on c.id = o.client_id
                """;

            Statement statement = dataBase.getDbService().getDbConnection().createStatement();
            resultSet = statement.executeQuery(sql);
        } else {
            String sql = """
                SELECT DISTINCT
                    first_name, last_name, address, phone_number
                FROM order_to_missing_drugs otmd
                JOIN drug_to_component dtc on otmd.drug_id = dtc.drug_id
                JOIN drugs d on dtc.component_id = d.id
                JOIN release_forms rf on d.release_form_id = rf.id
                JOIN orders o on otmd.order_id = o.id
                JOIN clients c on c.id = o.client_id
                WHERE release_form_id = ?
                """;

            PreparedStatement preparedStatement = dataBase.getDbService().getDbConnection()
                .prepareStatement(sql);

            preparedStatement.setInt(1, releaseFrom);
            resultSet = preparedStatement.executeQuery();
        }

        int columnsCount = resultSet.getMetaData().getColumnCount();
        ArrayList<ReportTableRow> reportTableRows = new ArrayList<>();
        while (resultSet.next()) {
            ReportTableRow reportTableRow = new ReportTableRow();
            for (int i = 1; i <= columnsCount; ++i) {
                reportTableRow.addField(resultSet.getObject(i));
            }
            reportTableRows.add(reportTableRow);
        }
        return reportTableRows;
    }
}

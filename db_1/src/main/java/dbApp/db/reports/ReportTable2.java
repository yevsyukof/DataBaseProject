package dbApp.db.reports;

import dbApp.db.DataBase;
import dbApp.utils.Pair;
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
    private Integer queryParam = null;

    public ReportTable2(String tableName, DataBase dataBase) {
        super(tableName, dataBase);

        setTranslatedColumnsNames(dataBase.getClientsTable().getTranslatedColumnsNames());
        translatedColumnsNames.remove("id");
        setColumnsNames(dataBase.getClientsTable().getColumnsNames());
        columnsNames.remove("id");
    }


    @Override
    public List<String> getPossibleQueryParameters() throws SQLException {
        String sql = """
            SELECT * FROM release_forms
            """;

        Statement statement = dataBase.getDbService().getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        List<String> possibleParameters = new ArrayList<>();
        while (resultSet.next()) {
            possibleParameters.add(resultSet.getString(2));
        }

        statement.close();
        return possibleParameters;
    }

    @Override
    public List<ReportTableRow> getReportRows() throws SQLException {
        ResultSet resultSet;

        if (queryParam == null) {
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
            FROM order_to_drugs otd
            JOIN order_to_missing_drugs otmd ON otd.drug_id = otmd.drug_id
            JOIN drugs d on otmd.drug_id = d.id
            JOIN orders o on o.id = otd.order_id
            JOIN clients c on c.id = o.client_id
            WHERE release_form_id = ?
                """;

            PreparedStatement preparedStatement = dataBase.getDbService().getDbConnection()
                .prepareStatement(sql);
            preparedStatement.setInt(1, queryParam);
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

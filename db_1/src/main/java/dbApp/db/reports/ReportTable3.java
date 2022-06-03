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

public class ReportTable3 extends ReportTable {

    @Setter
    private Integer queryParam = null;

    public ReportTable3(String tableName, DataBase dataBase) {
        super(tableName, dataBase);

        setTranslatedColumnsNames(List.of(new String[]{"Название лекарства", "статистика исп."}));
    }

    @Override
    public Map<String, Object> getPossibleQueryParameters() throws SQLException {
        String sql = """
            SELECT * FROM release_forms
            """;

        Statement statement = dataBase.getDbService().getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        Map<String, Object> possibleParameters = new LinkedHashMap<>();
        while (resultSet.next()) {
            possibleParameters.put(
                resultSet.getString(2), resultSet.getInt(1));
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
                name, sum(volume) OVER (PARTITION BY drug_id) as cnt
            FROM drugs_use_statistics
            JOIN drugs d on d.id = drugs_use_statistics.drug_id
            ORDER BY cnt DESC
            LIMIT 10
                """;

            Statement statement = dataBase.getDbService().getDbConnection().createStatement();
            resultSet = statement.executeQuery(sql);
        } else {
            String sql = """
            SELECT DISTINCT
                name, sum(volume) OVER (PARTITION BY drug_id) as cnt
            FROM drugs_use_statistics
            JOIN drugs d on d.id = drugs_use_statistics.drug_id
            JOIN release_forms rf on d.release_form_id = rf.id
            WHERE release_form_id = ?
            ORDER BY cnt DESC
            LIMIT 10
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

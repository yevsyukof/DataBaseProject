package dbApp.db.reports;

import dbApp.db.DataBase;
import dbApp.db.entities.AbstractTableRow;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Setter;

public class ReportTable4 extends ReportTable {

    @Setter
    private Integer requiredReleaseFormId = null;
    @Setter
    private Date from = null;
    @Setter
    private Date to = null;

    public ReportTable4(String tableName, DataBase dataBase) {
        super(tableName, dataBase);

        setTranslatedColumnsNames(List.of(new String[]{"Название лекарства", "статистика исп."}));
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
        if (from == null || to == null) {
            throw new SQLException("Не заданы параметры запроса");
        }

        String sql = """
            SELECT DISTINCT
                name, sum(volume) OVER (PARTITION BY drug_id) as cnt
            FROM drugs_use_statistics
            JOIN drugs d on d.id = drugs_use_statistics.drug_id
            JOIN release_forms rf on d.release_form_id = rf.id
            WHERE release_form_id = ? AND record_date >= ? AND record_date <= ?
            ORDER BY cnt DESC
                """;
        if (requiredReleaseFormId == null) {
            sql = sql.replaceFirst("\\?", "release_form_id");
        }
        PreparedStatement preparedStatement = dataBase.getDbService().getDbConnection()
            .prepareStatement(sql);

        if (requiredReleaseFormId != null) {
            preparedStatement.setInt(1, requiredReleaseFormId);
            preparedStatement.setDate(2, from);
            preparedStatement.setDate(3, to);
        } else {
            preparedStatement.setDate(1, from);
            preparedStatement.setDate(2, to);
        }
        ResultSet resultSet = preparedStatement.executeQuery();

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

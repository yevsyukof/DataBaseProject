package dbApp.db.reports;

import dbApp.db.DataBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReportTable9 extends ReportTable {

    public ReportTable9(DataBase dataBase) {
        super("Отчет по заказам", dataBase);

        setTranslatedColumnsNames(List.of(new String[]{
            "Название лекарства", "Требуемое количество"}));
    }

    @Override
    public List<ReportTableRow> getReportRows() throws SQLException {
        String sql = """
            SELECT DISTINCT
                name, sum(deficit) OVER (PARTITION BY otmd.drug_id)
            FROM order_to_missing_drugs otmd
            JOIN drugs d on otmd.drug_id = d.id
            """;

        Statement statement = dataBase.getDbService().getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        int columnsCount = resultSet.getMetaData().getColumnCount();
        ArrayList<ReportTableRow> reportTableRows = new ArrayList<>();
        while (resultSet.next()) {
            ReportTableRow reportTableRow = new ReportTableRow();
            for (int i = 1; i <= columnsCount; ++i) {
                reportTableRow.addField(resultSet.getObject(i));
            }
            reportTableRows.add(reportTableRow);
        }

        statement.close();
        return reportTableRows;
    }
}

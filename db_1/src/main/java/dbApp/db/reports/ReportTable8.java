package dbApp.db.reports;

import dbApp.db.DataBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReportTable8 extends ReportTable {

    public ReportTable8(DataBase dataBase) {
        super("Отчет по заказам", dataBase);

        setTranslatedColumnsNames(List.of(new String[]{
            "ID клиента", "Дата заказа", "Дата исполнения", "Дата получения"}));

//        setColumnsNames(List.of(new String[]{
//            "client_id", "order_date", "ready_date", "issue_date"}));
    }

    @Override
    public List<ReportTableRow> getReportRows() throws SQLException {
        String sql = """
            SELECT
                client_id, order_date, ready_date, issue_date
            FROM orders
            WHERE ready_date > now()
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

package dbApp.db.reports;

import dbApp.db.DataBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Таблица отчета о клиентах, пропустивших заказ
public class ReportTable1 extends ReportTable {

    public ReportTable1(DataBase dataBase) {
        super("Отчет о клиентах", dataBase);

        setTranslatedColumnsNames(dataBase.getClientsTable().getTranslatedColumnsNames());
        translatedColumnsNames.remove("id");

//        setColumnsNames(dataBase.getClientsTable().getColumnsNames());
//        columnsNames.remove("id");
    }

    @Override
    public List<ReportTableRow> getReportRows() throws SQLException {
        String sql = """
            SELECT DISTINCT first_name, last_name, address, phone_number FROM Orders o
            JOIN clients c on c.id = o.client_id
            WHERE ready_date IS NOT NULL AND issue_date IS NOT NULL AND ready_date < issue_date
            """;

        Statement statement = dataBase.getDbService().getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        ArrayList<ReportTableRow> reportTableRows = new ArrayList<>();
//        int columnCounts = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            reportTableRows.add(
                new ReportTableRow(
                    new Object[]{
                        resultSet.getObject("first_name"),
                        resultSet.getObject("last_name"),
                        resultSet.getObject("address"),
                        resultSet.getObject("phone_number")
                    })
            );
        }
        statement.close();

        return reportTableRows;
    }
}

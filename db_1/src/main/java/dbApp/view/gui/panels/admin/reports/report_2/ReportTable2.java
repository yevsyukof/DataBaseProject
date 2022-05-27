package dbApp.view.gui.panels.admin.reports.report_2;

import dbApp.model.db.DataBase;
import dbApp.view.gui.panels.admin.reports.ReportTable;
import dbApp.view.gui.panels.admin.reports.ReportTableRow;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReportTable2 extends ReportTable {

    private String queryParam;

    public ReportTable2(String tableName, DataBase dataBase, String queryParam) {
        super(tableName, dataBase);

        this.queryParam = queryParam;

        setTranslatedColumnsNames(dataBase.getClientsTable().getTranslatedColumnsNames());
        translatedColumnsNames.remove("id");
        setColumnsNames(dataBase.getClientsTable().getColumnsNames());
        columnsNames.remove("id");
    }


    @Override
    public List<ReportTableRow> getReportRows() throws SQLException {
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

        preparedStatement.setString(1,
            Objects.requireNonNullElse(queryParam, "release_form_id"));

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
        preparedStatement.close();
        return reportTableRows;
    }
}

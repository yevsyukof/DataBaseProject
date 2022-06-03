package dbApp.db.reports;

import dbApp.db.DataBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReportTable6 extends ReportTable {

    public ReportTable6(DataBase dataBase) {
        super("Отчет по складу лекарств", dataBase);


        setTranslatedColumnsNames(List.of(new String[]{
            "Название лекарства", "Тип"}));

//        setColumnsNames(List.of(new String[]{
//            "name", "drug_type"}));
    }

    @Override
    public List<ReportTableRow> getReportRows() throws SQLException {
        String sql = """
            SELECT
                name, drug_type
            FROM drugs d
            JOIN release_forms rf on rf.id = d.release_form_id
            WHERE inventory_volume <= critical_rate
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

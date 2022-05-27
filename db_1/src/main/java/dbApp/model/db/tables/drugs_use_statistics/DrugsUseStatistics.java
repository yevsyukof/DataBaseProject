package dbApp.model.db.tables.drugs_use_statistics;

import dbApp.model.db.DataBase.DBService;
import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTable;
import dbApp.model.db.entities.AbstractTableRow;
import dbApp.model.db.tables.drugs.DrugsRow;
import dbApp.model.db.tables.drugs.DrugsRowPrimaryKey;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DrugsUseStatistics extends AbstractTable {

    public DrugsUseStatistics(DBService dbService) throws SQLException {
        super("drugs_use_statistics", dbService);
    }

    @Override
    protected void loadColumns() {
        primaryKeyComponentsNames.add("id");

        columnsNames.add("id");
        columnsNames.add("drug_id");
        columnsNames.add("order_id");
        columnsNames.add("record_date");
        columnsNames.add("volume");

        translatedColumnsNames.add("id");
        translatedColumnsNames.add("ID лекарства");
        translatedColumnsNames.add("ID заказа");
        translatedColumnsNames.add("Дата исп.");
        translatedColumnsNames.add("Количество шт.");
    }

    @Override
    public void addRow(List<String> fieldsValues) throws SQLException {
        String sql = """
            INSERT INTO drugs_use_statistics
                (drug_id, order_id, record_date, volume)
            VALUES
                (?, ?, ?, ?)
                """;

        PreparedStatement preparedStatement = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setInt(1, Integer.parseInt(fieldsValues.get(0)));
            preparedStatement.setInt(2, Integer.parseInt(fieldsValues.get(1)));
            preparedStatement.setDate(3, Date.valueOf(fieldsValues.get(2)));
            preparedStatement.setInt(4, Integer.parseInt(fieldsValues.get(3)));
        } catch (IllegalArgumentException e) {
            throw new SQLException("Введен невалидный аргумент: " + e.getLocalizedMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void deleteRow(AbstractPrimaryKey primaryKeyValue) throws SQLException {
        String sql = "DELETE FROM drugs_use_statistics WHERE id = ?";

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        preparedStatement.setInt(1,
            ((DrugsUseStatisticsRowPrimaryKey) primaryKeyValue).getId());

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void updateRow(AbstractPrimaryKey primaryKeyValue, AbstractTableRow updatedRow)
            throws SQLException {
        DrugsUseStatisticsRowPrimaryKey pk = (DrugsUseStatisticsRowPrimaryKey) primaryKeyValue;
        DrugsUseStatisticsRow row = (DrugsUseStatisticsRow) updatedRow;

        String sql = """
            UPDATE drugs_use_statistics
            SET id = ?, drug_id = ?, order_id = ?, record_date = ?, volume = ?
            WHERE id = ?
            """;

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setInt(1, row.getId());
            preparedStatement.setInt(2, row.getDrugId());
            preparedStatement.setLong(3, row.getOrderId());
            preparedStatement.setInt(4, row.getVolume());
            preparedStatement.setDate(5, row.getRecordDate());

            preparedStatement.setInt(6, pk.getId());
        } catch (IllegalArgumentException e) {
            throw new SQLException(e.getMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public List<AbstractTableRow> getAllRows() throws SQLException {
        String sql = "SELECT * FROM drugs_use_statistics";

        Statement statement = dbService.getDbConnection().createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<AbstractTableRow> allTableRows = new ArrayList<>();

        while (resultSet.next()) {
            allTableRows.add(
                new DrugsUseStatisticsRow(
                    resultSet.getInt("id"),
                    resultSet.getInt("drug_id"),
                    resultSet.getLong("order_id"),
                    resultSet.getDate("record_date"),
                    resultSet.getInt("volume")
                )
            );
        }

        statement.close();
        return allTableRows;
    }

    @Override
    public String getTranslatedName() {
        return "Статистика использования лекарств";
    }
}

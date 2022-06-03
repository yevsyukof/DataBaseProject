package dbApp.db.tables.order_to_missing_drugs;

import dbApp.db.DataBase.DBService;
import dbApp.db.entities.AbstractTable;
import dbApp.db.entities.AbstractTableRow;
import dbApp.db.entities.AbstractPrimaryKey;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderToMissingDrugs extends AbstractTable {

    public OrderToMissingDrugs(DBService dbService) throws SQLException {
        super("order_to_missing_drugs", dbService);
    }

    @Override
    protected void loadColumns() {
        columnsNames.add("order_id");
        columnsNames.add("drug_id");
        columnsNames.add("deficit");

        translatedColumnsNames.add("ID заказа");
        translatedColumnsNames.add("ID компонента");
        translatedColumnsNames.add("Недостаток шт.");
    }

    @Override
    public void addRow(List<String> fieldsValues) throws SQLException {
        String sql = """
            INSERT INTO order_to_missing_drugs
                (order_id, drug_id, deficit)
            VALUES
                (?, ?, ?)
                """;

        PreparedStatement preparedStatement = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setLong(1, Long.parseLong(fieldsValues.get(0)));
            preparedStatement.setInt(2, Integer.parseInt(fieldsValues.get(1)));
            preparedStatement.setInt(3, Integer.parseInt(fieldsValues.get(2)));
        } catch (IllegalArgumentException e) {
            throw new SQLException("Введен невалидный аргумент: " + e.getLocalizedMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void deleteRow(AbstractPrimaryKey primaryKeyValue) throws SQLException {
        String sql = "DELETE FROM order_to_missing_drugs WHERE order_id = ?";

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        preparedStatement.setLong(1,
            ((OrderToMissingDrugsRowUniqueKey) primaryKeyValue).getOrderId());

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void updateRow(AbstractPrimaryKey primaryKeyValue, AbstractTableRow updatedRow)
            throws SQLException {
        OrderToMissingDrugsRowUniqueKey pk = (OrderToMissingDrugsRowUniqueKey) primaryKeyValue;
        OrderToMissingDrugsRow row = (OrderToMissingDrugsRow) updatedRow;

        String sql = """
            UPDATE order_to_missing_drugs
            SET order_id = ?, drug_id = ?, deficit = ?
            WHERE order_id = ?
            """;

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setLong(1, row.getOrderId());
            preparedStatement.setInt(2, row.getDrugId());
            preparedStatement.setInt(3, row.getDeficit());

            preparedStatement.setLong(4, pk.getOrderId());
        } catch (IllegalArgumentException e) {
            throw new SQLException(e.getMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public List<AbstractTableRow> getAllRows() throws SQLException {
        String sql = "SELECT * FROM order_to_missing_drugs";

        Statement statement = dbService.getDbConnection().createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<AbstractTableRow> allTableRows = new ArrayList<>();

        while (resultSet.next()) {
            allTableRows.add(
                new OrderToMissingDrugsRow(
                    resultSet.getLong("order_id"),
                    resultSet.getInt("drug_id"),
                    resultSet.getInt("deficit")
                )
            );
        }

        statement.close();
        return allTableRows;
    }

    @Override
    public String getTranslatedName() {
        return "Недостающие заказу компоненты";
    }
}

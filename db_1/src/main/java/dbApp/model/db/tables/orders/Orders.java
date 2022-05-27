package dbApp.model.db.tables.orders;

import dbApp.model.db.DataBase.DBService;
import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTable;
import dbApp.model.db.entities.AbstractTableRow;
import dbApp.model.db.tables.order_to_missing_drugs.OrderToMissingDrugsRow;
import dbApp.model.db.tables.order_to_missing_drugs.OrderToMissingDrugsRowUniqueKey;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Orders extends AbstractTable {

    public Orders(DBService dbService) throws SQLException {
        super("orders", dbService);
    }

    @Override
    protected void loadColumns() {
        primaryKeyComponentsNames.add("id");

        columnsNames.add("id");
        columnsNames.add("client_id");
        columnsNames.add("order_date");
        columnsNames.add("ready_date");
        columnsNames.add("issue_date");

        translatedColumnsNames.add("id");
        translatedColumnsNames.add("ID клиента");
        translatedColumnsNames.add("Дата заказа");
        translatedColumnsNames.add("Дата выполнения");
        translatedColumnsNames.add("Дата получения");
    }

    @Override
    public void addRow(List<String> fieldsValues) throws SQLException {
        String sql = """
            INSERT INTO orders
                (client_id, order_date, ready_date, issue_date)
            VALUES
                (?, ?, ?, ?)
                """;

        PreparedStatement preparedStatement = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setInt(1, Integer.parseInt(fieldsValues.get(0)));
            preparedStatement.setDate(2, Date.valueOf(fieldsValues.get(1)));
            preparedStatement.setDate(3, Date.valueOf(fieldsValues.get(2)));
            preparedStatement.setDate(4, Date.valueOf(fieldsValues.get(3)));
        } catch (IllegalArgumentException e) {
            throw new SQLException("Введен невалидный аргумент: " + e.getLocalizedMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void deleteRow(AbstractPrimaryKey primaryKeyValue) throws SQLException {
        String sql = "DELETE FROM orders WHERE id = ?";

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        preparedStatement.setInt(1,
            ((OrdersRowPrimaryKey) primaryKeyValue).getId());

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void updateRow(AbstractPrimaryKey primaryKeyValue, AbstractTableRow updatedRow)
            throws SQLException {
        OrdersRowPrimaryKey pk = (OrdersRowPrimaryKey) primaryKeyValue;
        OrdersRow row = (OrdersRow) updatedRow;

        String sql = """
            UPDATE orders
            SET id = ?, client_id = ?, order_date = ?, ready_date = ?, issue_date = ?
            WHERE id = ?
            """;

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setLong(1, row.getId());
            preparedStatement.setInt(2, row.getClientId());
            preparedStatement.setDate(3, row.getOrderDate());
            preparedStatement.setDate(4, row.getReadyDate());
            preparedStatement.setDate(5, row.getIssueDate());

            preparedStatement.setLong(6, pk.getId());
        } catch (IllegalArgumentException e) {
            throw new SQLException(e.getMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public List<AbstractTableRow> getAllRows() throws SQLException {
        String sql = "SELECT * FROM orders";

        Statement statement = dbService.getDbConnection().createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<AbstractTableRow> allTableRows = new ArrayList<>();

        while (resultSet.next()) {
            allTableRows.add(
                new OrdersRow(
                    resultSet.getLong("id"),
                    resultSet.getInt("client_id"),
                    resultSet.getDate("order_date"),
                    resultSet.getDate("ready_date"),
                    resultSet.getDate("issue_date")
                )
            );
        }

        statement.close();
        return allTableRows;
    }

    @Override
    public String getTranslatedName() {
        return "Заказы";
    }
}

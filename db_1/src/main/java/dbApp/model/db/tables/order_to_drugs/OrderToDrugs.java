package dbApp.model.db.tables.order_to_drugs;

import dbApp.model.db.DataBase.DBService;
import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTable;
import dbApp.model.db.entities.AbstractTableRow;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderToDrugs extends AbstractTable {

    public OrderToDrugs(DBService dbService) throws SQLException {
        super("order_to_drugs", dbService);
    }

    @Override
    protected void loadColumns() {
        columnsNames.add("order_id");
        columnsNames.add("drug_id");
        columnsNames.add("volume");
        columnsNames.add("drug_price");

        translatedColumnsNames.add("ID заказа");
        translatedColumnsNames.add("ID лекарства");
        translatedColumnsNames.add("количество");
        translatedColumnsNames.add("цена лекарства за шт.");
    }

    @Override
    public void addRow(List<String> fieldsValues) throws SQLException {
        String sql = """
            INSERT INTO order_to_drugs
                (order_id, drug_id, volume, drug_price)
            VALUES
                (?, ?, ?, ?)
                """;

        PreparedStatement preparedStatement = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setLong(1, Long.parseLong(fieldsValues.get(0)));
            preparedStatement.setInt(2, Integer.parseInt(fieldsValues.get(1)));
            preparedStatement.setInt(3, Integer.parseInt(fieldsValues.get(2)));
            preparedStatement.setInt(4, Integer.parseInt(fieldsValues.get(3)));
        } catch (IllegalArgumentException e) {
            throw new SQLException("Введен невалидный аргумент: " + e.getLocalizedMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void deleteRow(AbstractPrimaryKey primaryKeyValue) throws SQLException {
        String sql = "DELETE FROM order_to_drugs WHERE order_id = ?";

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        preparedStatement.setLong(1,
            ((OrderToDrugsRowUniqueKey) primaryKeyValue).getOrderId());

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void updateRow(AbstractPrimaryKey primaryKeyValue,
        AbstractTableRow updatedRow) throws SQLException {
    }

    @Override
    public List<AbstractTableRow> getAllRows() throws SQLException {
        String sql = "SELECT * FROM order_to_drugs";

        Statement statement = dbService.getDbConnection().createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<AbstractTableRow> allTableRows = new ArrayList<>();

        while (resultSet.next()) {
            allTableRows.add(
                new OrderToDrugsRow(
                    resultSet.getLong("order_id"),
                    resultSet.getInt("drug_id"),
                    resultSet.getInt("volume"),
                    resultSet.getInt("drug_price")
                )
            );
        }

        statement.close();
        return allTableRows;
    }

    @Override
    public String getTranslatedName() {
        return "Составы заказов";
    }
}

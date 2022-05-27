package dbApp.model.db.tables.purchase_requests;

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

public class PurchaseRequests extends AbstractTable {

    public PurchaseRequests(DBService dbService) throws SQLException {
        super("purchase_requests", dbService);
    }

    @Override
    protected void loadColumns() {
        primaryKeyComponentsNames.add("id");

        columnsNames.add("id");
        columnsNames.add("required_drug_id");
        columnsNames.add("volume");
        columnsNames.add("creation_date");

        translatedColumnsNames.add("id");
        translatedColumnsNames.add("ID лекарства");
        translatedColumnsNames.add("Количество шт.");
        translatedColumnsNames.add("Дата заявки");
    }

    @Override
    public void addRow(List<String> fieldsValues) throws SQLException {
        String sql = """
            INSERT INTO purchase_requests
                (required_drug_id, volume, creation_date)
            VALUES
                (?, ?, ?)
                """;

        PreparedStatement preparedStatement = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setInt(1, Integer.parseInt(fieldsValues.get(0)));
            preparedStatement.setInt(2, Integer.parseInt(fieldsValues.get(1)));
            preparedStatement.setDate(3, Date.valueOf(fieldsValues.get(2)));
        } catch (IllegalArgumentException e) {
            throw new SQLException("Введен невалидный аргумент: " + e.getLocalizedMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void deleteRow(AbstractPrimaryKey primaryKeyValue) throws SQLException {
        String sql = "DELETE FROM purchase_requests WHERE id = ?";

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        preparedStatement.setInt(1,
            ((PurchaseRequestsRowPrimaryKey) primaryKeyValue).getId());

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void updateRow(AbstractPrimaryKey primaryKeyValue, AbstractTableRow updatedRow)
            throws SQLException {
        PurchaseRequestsRowPrimaryKey pk = (PurchaseRequestsRowPrimaryKey) primaryKeyValue;
        PurchaseRequestsRow row = (PurchaseRequestsRow) updatedRow;

        String sql = """
            UPDATE purchase_requests
            SET id = ?, required_drug_id = ?, volume = ?, creation_date = ?
            WHERE id = ?
            """;

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setInt(1, row.getId());
            preparedStatement.setInt(2, row.getRequiredDrugId());
            preparedStatement.setInt(3, row.getVolume());
            preparedStatement.setDate(4, row.getCreationDate());

            preparedStatement.setLong(5, pk.getId());
        } catch (IllegalArgumentException e) {
            throw new SQLException(e.getMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public List<AbstractTableRow> getAllRows() throws SQLException {
        String sql = "SELECT * FROM purchase_requests";

        Statement statement = dbService.getDbConnection().createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<AbstractTableRow> allTableRows = new ArrayList<>();

        while (resultSet.next()) {
            allTableRows.add(
                new PurchaseRequestsRow(
                    resultSet.getInt("id"),
                    resultSet.getInt("required_drug_id"),
                    resultSet.getInt("volume"),
                    resultSet.getDate("creation_date")
                )
            );
        }

        statement.close();
        return allTableRows;
    }

    @Override
    public String getTranslatedName() {
        return "Заказы на покупку лекарств";
    }
}

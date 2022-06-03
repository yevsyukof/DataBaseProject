package dbApp.db.tables.drug_manufacturers;

import dbApp.db.DataBase.DBService;
import dbApp.db.entities.AbstractPrimaryKey;
import dbApp.db.entities.AbstractTable;
import dbApp.db.entities.AbstractTableRow;
import dbApp.db.tables.drugs.DrugsRow;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DrugManufacturers extends AbstractTable {

    public DrugManufacturers(DBService dbService) throws SQLException {
        super("drug_manufacturers", dbService);
    }

    @Override
    protected void loadColumns() {
        primaryKeyComponentsNames.add("id");

        columnsNames.add("id");
        columnsNames.add("name");

        translatedColumnsNames.add("id");
        translatedColumnsNames.add("Название производителя");
    }

    @Override
    public void addRow(List<String> fieldsValues) throws SQLException {
        String sql = """
            INSERT INTO drug_manufacturers
                (name)
            VALUES
                (?)
                """;

        PreparedStatement preparedStatement = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setString(1, fieldsValues.get(0));
        } catch (IllegalArgumentException e) {
            throw new SQLException(e.getMessage());
        }
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void deleteRow(AbstractPrimaryKey primaryKeyValue) throws SQLException {
        String sql = "DELETE FROM drug_manufacturers WHERE id = ?";

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        preparedStatement.setInt(1,
            ((DrugManufacturersRowPrimaryKey) primaryKeyValue).getId());

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void updateRow(AbstractPrimaryKey primaryKeyValue, AbstractTableRow updatedRow)
            throws SQLException {
        DrugManufacturersRowPrimaryKey pk = (DrugManufacturersRowPrimaryKey) primaryKeyValue;
        DrugManufacturersRow row = (DrugManufacturersRow) updatedRow;

        String sql = """
            UPDATE drug_manufacturers SET id = ?, name = ? WHERE id = ?
            """;

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setInt(1, row.getId());
            preparedStatement.setString(2, row.getName());

            preparedStatement.setInt(3, pk.getId());
        } catch (IllegalArgumentException e) {
            throw new SQLException(e.getMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public List<AbstractTableRow> getAllRows() throws SQLException {
        String sql = "SELECT * FROM drug_manufacturers";

        Statement statement = dbService.getDbConnection().createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<AbstractTableRow> allTableRows = new ArrayList<>();

        while (resultSet.next()) {
            allTableRows.add(
                new DrugManufacturersRow(
                    resultSet.getInt("id"),
                    resultSet.getString("name"))
            );
        }

        statement.close();
        return allTableRows;
    }

    @Override
    public String getTranslatedName() {
        return "Производители лекарств";
    }

    public Map<String, Integer> getDrugManufacturersMap() throws SQLException {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (AbstractTableRow row : getAllRows()) {
            DrugManufacturersRow drugManufacturersRow = (DrugManufacturersRow) row;
            map.put(drugManufacturersRow.getName(),
                drugManufacturersRow.getId());
        }
        return map;
    }
}

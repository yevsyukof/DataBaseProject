package dbApp.db.tables.drugs;

import dbApp.db.DataBase.DBService;
import dbApp.db.entities.AbstractTable;
import dbApp.db.entities.AbstractTableRow;
import dbApp.db.entities.AbstractPrimaryKey;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Drugs extends AbstractTable {

    public Drugs(DBService dbService) throws SQLException {
        super("drugs", dbService);
    }

    @Override
    protected void loadColumns() {
        primaryKeyComponentsNames.add("id");

        columnsNames.add("id");
        columnsNames.add("name");
        columnsNames.add("release_form_id");
        columnsNames.add("drug_manufacturer_id");
        columnsNames.add("inventory_volume");
        columnsNames.add("critical_rate");
        columnsNames.add("price");

        translatedColumnsNames.add("id");
        translatedColumnsNames.add("Название");
        translatedColumnsNames.add("ID Формы выпуска");
        translatedColumnsNames.add("ID производителя");
        translatedColumnsNames.add("Количество на складе");
        translatedColumnsNames.add("Минимальный запас");
        translatedColumnsNames.add("Цена за штуку");
    }

    @Override
    public void addRow(List<String> fieldsValues) throws SQLException {
        String sql = """
            INSERT INTO drugs
                (name, release_form_id, drug_manufacturer_id, inventory_volume, critical_rate, price)
            VALUES
                (?, ?, ?, ?, ?, ?)
                """;

        PreparedStatement preparedStatement = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setString(1, fieldsValues.get(0));
            preparedStatement.setInt(2, Integer.parseInt(fieldsValues.get(1)));
            preparedStatement.setInt(3, Integer.parseInt(fieldsValues.get(2)));
            preparedStatement.setInt(4, Integer.parseInt(fieldsValues.get(3)));
            preparedStatement.setInt(5, Integer.parseInt(fieldsValues.get(4)));
            preparedStatement.setInt(6, Integer.parseInt(fieldsValues.get(5)));
        } catch (IllegalArgumentException e) {
            throw new SQLException(e.getMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void deleteRow(AbstractPrimaryKey primaryKeyValue) throws SQLException {
        String sql = "DELETE FROM drugs WHERE id = ?";

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        preparedStatement.setInt(1,
            ((DrugsRowPrimaryKey) primaryKeyValue).getId());

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void updateRow(AbstractPrimaryKey primaryKeyValue, AbstractTableRow updatedRow)
            throws SQLException {
        DrugsRowPrimaryKey pk = (DrugsRowPrimaryKey) primaryKeyValue;
        DrugsRow row = (DrugsRow) updatedRow;

        String sql = """
            UPDATE drugs
            SET id = ?, name = ?, release_form_id = ?, drug_manufacturer_id = ?,
                inventory_volume = ?, critical_rate = ?, price = ?
            WHERE id = ?
            """;

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setInt(1, row.getId());
            preparedStatement.setString(2, row.getName());
            preparedStatement.setInt(3, row.getReleaseFormId());
            preparedStatement.setInt(4, row.getDrugManufacturerId());
            preparedStatement.setInt(5, row.getInventoryVolume());
            preparedStatement.setInt(6, row.getCriticalRate());
            preparedStatement.setInt(7, row.getPrice());

            preparedStatement.setInt(8, pk.getId());
        } catch (IllegalArgumentException e) {
            throw new SQLException(e.getMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public List<AbstractTableRow> getAllRows() throws SQLException {
        String sql = "SELECT * FROM drugs";

        Statement statement = dbService.getDbConnection().createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<AbstractTableRow> allTableRows = new ArrayList<>();

        while (resultSet.next()) {
            allTableRows.add(
                new DrugsRow(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("release_form_id"),
                    resultSet.getInt("drug_manufacturer_id"),
                    resultSet.getInt("inventory_volume"),
                    resultSet.getInt("critical_rate"),
                    resultSet.getInt("price")
                )
            );
        }

        statement.close();
        return allTableRows;
    }

    @Override
    public String getTranslatedName() {
        return "Лекарства";
    }

    public Map<String, Integer> getDrugsMap() throws SQLException {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (AbstractTableRow row : getAllRows()) {
            DrugsRow drugsRow = (DrugsRow) row;
            map.put(drugsRow.getName(), drugsRow.getId());
        }
        return map;
    }

    public Integer addNewDrugIntoBase(List<Object> args) throws SQLException {
        String sql = """
            INSERT INTO drugs
                (name, release_form_id, drug_manufacturer_id, inventory_volume, critical_rate, price)
            VALUES
                (?, ?, ?, 0, ?, ?)
            RETURNING id
                """;

        PreparedStatement preparedStatement = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setObject(1, args.get(0));
            preparedStatement.setObject(2, args.get(1));
            preparedStatement.setObject(3, args.get(2));
            preparedStatement.setObject(4, args.get(3));
            preparedStatement.setObject(5, args.get(4));
        } catch (IllegalArgumentException e) {
            throw new SQLException(e.getMessage());
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        Integer returnedId;
        if (resultSet.next()) {
            returnedId = resultSet.getInt(1);
        } else {
            preparedStatement.close();
            throw new SQLException("ID не вернулся");
        }

        preparedStatement.close();
        return returnedId;
    }
}

package dbApp.db.tables.release_forms;

import dbApp.db.DataBase.DBService;
import dbApp.db.entities.AbstractPrimaryKey;
import dbApp.db.entities.AbstractTable;
import dbApp.db.entities.AbstractTableRow;
import dbApp.db.tables.drug_manufacturers.DrugManufacturersRow;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReleaseForms extends AbstractTable {

    public ReleaseForms(DBService dbService) throws SQLException {
        super("release_forms", dbService);
    }

    @Override
    protected void loadColumns() {
        primaryKeyComponentsNames.add("id");

        columnsNames.add("id");
        columnsNames.add("drug_type");

        translatedColumnsNames.add("id");
        translatedColumnsNames.add("Форма выпуска");
    }

    @Override
    public void addRow(List<String> fieldsValues) throws SQLException {
        String sql = """
            INSERT INTO release_forms
                (drug_type)
            VALUES
                (?)
                """;

        PreparedStatement preparedStatement = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setString(1, fieldsValues.get(0));
        } catch (IllegalArgumentException e) {
            throw new SQLException("Введен невалидный аргумент: " + e.getLocalizedMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void deleteRow(AbstractPrimaryKey primaryKeyValue) throws SQLException {
        String sql = "DELETE FROM release_forms WHERE id = ?";

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        preparedStatement.setInt(1,
            ((ReleaseFormsRowPrimaryKey) primaryKeyValue).getId());

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void updateRow(AbstractPrimaryKey primaryKeyValue, AbstractTableRow updatedRow)
            throws SQLException {
        ReleaseFormsRowPrimaryKey pk = (ReleaseFormsRowPrimaryKey) primaryKeyValue;
        ReleaseFormsRow row = (ReleaseFormsRow) updatedRow;

        String sql = """
            UPDATE release_forms
            SET id = ?, drug_type = ?
            WHERE id = ?
            """;

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setInt(1, row.getId());
            preparedStatement.setString(2, row.getDrugType());

            preparedStatement.setLong(3, pk.getId());
        } catch (IllegalArgumentException e) {
            throw new SQLException(e.getMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public List<AbstractTableRow> getAllRows() throws SQLException {
        String sql = "SELECT * FROM release_forms";

        Statement statement = dbService.getDbConnection().createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<AbstractTableRow> allTableRows = new ArrayList<>();

        while (resultSet.next()) {
            allTableRows.add(
                new ReleaseFormsRow(
                    resultSet.getInt("id"),
                    resultSet.getString("drug_type")
                )
            );
        }

        statement.close();
        return allTableRows;
    }

    @Override
    public String getTranslatedName() {
        return "Формы выпуска лекарств";
    }

    public Map<String, Integer> getReleaseFormsMap() throws SQLException {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (AbstractTableRow row : getAllRows()) {
            ReleaseFormsRow releaseFormsRow = (ReleaseFormsRow) row;
            map.put(releaseFormsRow.getDrugType(),
                releaseFormsRow.getId());
        }
        return map;
    }
}

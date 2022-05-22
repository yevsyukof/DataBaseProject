package dbApp.model.db.tables.technologies;

import dbApp.model.db.DataBase.DBService;
import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;
import dbApp.model.db.entities.AbstractTable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Technologies extends AbstractTable {

    public Technologies(DBService dbService) throws SQLException {
        super("technologies", dbService);
    }

    @Override
    protected void loadColumns() {
        primaryKeyComponentsNames.add("id");

        columnsNames.add("id");
        columnsNames.add("drug_id");
        columnsNames.add("make_duration");
        columnsNames.add("technology_desc");

        translatedColumnsNames.add("id");
        translatedColumnsNames.add("ID Лекарства");
        translatedColumnsNames.add("Время изготовления");
        translatedColumnsNames.add("Описание технологии");

        columnsIndexes.put("id", 1);
        columnsIndexes.put("drug_id", 2);
        columnsIndexes.put("make_duration", 3);
        columnsIndexes.put("technology_desc", 4);

        columnsIndexes.put("ID Лекарства", 2);
        columnsIndexes.put("Время изготовления", 3);
        columnsIndexes.put("Описание технологии", 4);
    }

    @Override
    public void createTable() { }

    @Override
    public void dropTable() { }

    @Override
    public void addRow(AbstractTableRow newRow) throws SQLException {
        TechnologiesRow newTechnologiesRow = (TechnologiesRow) newRow;

        String sql = """
            INSERT INTO drug_manufacturers
                (drug_id, make_duration, technology_desc)
            VALUES
                (?, ?, ?)
                """;

        PreparedStatement preparedStatement = dbService.getDbConnection().prepareStatement(sql);
        preparedStatement.setInt(1, newTechnologiesRow.getDrugId());
        preparedStatement.setInt(2, newTechnologiesRow.getMakeDuration());
        preparedStatement.setString(3, newTechnologiesRow.getTechnologyDesc());

        preparedStatement.execute();
    }

    @Override
    public void deleteRow(AbstractPrimaryKey primaryKeyValue) throws SQLException {
        String sql = "DELETE FROM Technologies WHERE id = ?";

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        preparedStatement.setInt(1,
            ((TechnologiesRowPrimaryKey)primaryKeyValue).getId());

        preparedStatement.execute();

        preparedStatement.close();
    }

    @Override
    public void updateRow(AbstractPrimaryKey primaryKeyValue,
            AbstractTableRow updatedRow) throws SQLException {
        // TODO
    }

    @Override
    public List<AbstractTableRow> getAllRows() throws SQLException {
        String sql = "SELECT * FROM Technologies";

        Statement statement = dbService.getDbConnection().createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<AbstractTableRow> allTableRows = new ArrayList<>();

        while (resultSet.next()) {
            allTableRows.add(
                new TechnologiesRow(
                    resultSet.getInt("id"),
                    resultSet.getInt("drug_id"),
                    resultSet.getInt("make_duration"),
                    resultSet.getString("technology_desc"))
            );
        }

        statement.close();
        return allTableRows;
    }

    @Override
    public String getTranslatedName() {
        return "Технологии";
    }
}

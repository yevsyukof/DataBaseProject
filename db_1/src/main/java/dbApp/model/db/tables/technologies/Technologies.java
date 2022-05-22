package dbApp.model.db.tables.technologies;

import dbApp.model.db.DataBase.DBService;
import dbApp.model.db.entities.PrimaryKey;
import dbApp.model.db.entities.TableRow;
import dbApp.model.db.entities.Table;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Technologies extends Table {

    public Technologies(DBService dbService) throws SQLException {
        super("technologies", dbService);
    }

    @Override
    protected void loadColumns() {
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
    public void addRow(TableRow newRow) throws SQLException {
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
    public void deleteRow(PrimaryKey primaryKey) {
    }

    @Override
    public void updateRow(PrimaryKey primaryKey, TableRow updatedRow) {

    }

    @Override
    public List<TableRow> readAll() {
        String sql = "SELECT * FROM Technologies";

        try (Statement statement = dbService.getDbConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            ArrayList<TableRow> allTableRows = new ArrayList<>();

            while (resultSet.next()) {
                allTableRows.add(
                    new TechnologiesRow(
                        new TechnologiesRowPrimaryKey(resultSet.getInt("id")),
                        resultSet.getInt("drug_id"),
                        resultSet.getInt("make_duration"),
                        resultSet.getString("technology_desc"))
                );
            }

            return allTableRows;
        } catch (SQLException e) {
            System.err.println("НЕ УДАЛОСЬ ПОЛУЧИТЬ ВСЕ СТРОКИ ИЗ ТАБЛИЦЫ technologies");
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getTranslatedName() {
        return "Технологии";
    }
}
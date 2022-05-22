package dbApp.model.db.tables.clients;

import dbApp.model.db.DataBase.DBService;
import dbApp.model.db.entities.PrimaryKey;
import dbApp.model.db.entities.TableRow;
import dbApp.model.db.entities.Table;
import dbApp.model.db.tables.technologies.TechnologiesRow;
import dbApp.model.db.tables.technologies.TechnologiesRowPrimaryKey;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Clients extends Table {

    public Clients(DBService dbService) throws SQLException {
        super("clients", dbService);
    }

    @Override
    protected void loadColumns() {
        columnsNames.add("id");
        columnsNames.add("first_name");
        columnsNames.add("last_name");
        columnsNames.add("address");
        columnsNames.add("phone_number");

        translatedColumnsNames.add("id");
        translatedColumnsNames.add("Имя");
        translatedColumnsNames.add("Фамилия");
        translatedColumnsNames.add("Адрес");
        translatedColumnsNames.add("Телефонный номер");
    }

    @Override
    public void createTable() { }

    @Override
    public void dropTable() { }

    @Override
    public void addRow(TableRow newRow) throws SQLException {
        ClientsRow newClientsRow = (ClientsRow) newRow;

        String sql = """
            INSERT INTO clients
                (first_name, last_name, address, phone_number)
            VALUES
                (?, ?, ?, ?)
                """;

        PreparedStatement preparedStatement = dbService.getDbConnection().prepareStatement(sql);
//        preparedStatement.setInt(1, newTechnologiesRow.getDrugId());
//        preparedStatement.setInt(2, newTechnologiesRow.getMakeDuration());
//        preparedStatement.setString(3, newTechnologiesRow.getTechnologyDesc());

        preparedStatement.execute();
    }

    @Override
    public void deleteRow(PrimaryKey primaryKey) {}

    @Override
    public void updateRow(PrimaryKey primaryKey, TableRow updatedRow) {

    }

    @Override
    public List<TableRow> readAll() {
        String sql = "SELECT * FROM clients";

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
        return "Клиенты";
    }
}

package dbApp.model.db.tables.clients;

import dbApp.model.db.DataBase.DBService;
import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;
import dbApp.model.db.entities.AbstractTable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Clients extends AbstractTable {

    public Clients(DBService dbService) throws SQLException {
        super("clients", dbService);
    }

    @Override
    protected void loadColumns() {
        primaryKeyComponentsNames.add("id");

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
    public void createTable() {  }

    @Override
    public void dropTable() {  }

    @Override
    public void addRow(AbstractTableRow newRow) throws SQLException {
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
    public void deleteRow(AbstractPrimaryKey primaryKeyValue) throws SQLException {  }

    @Override
    public void updateRow(AbstractPrimaryKey primaryKeyValue,
            AbstractTableRow updatedRow) throws SQLException {  }

    @Override
    public List<AbstractTableRow> getAllRows() throws SQLException {
        String sql = "SELECT * FROM clients";

        Statement statement = dbService.getDbConnection().createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<AbstractTableRow> allTableRows = new ArrayList<>();

        while (resultSet.next()) {
            allTableRows.add(
                new ClientsRow(
                    resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("address"),
                    resultSet.getString("phone_number"))
            );
        }

        statement.close();
        return allTableRows;
    }

    @Override
    public String getTranslatedName() {
        return "Клиенты";
    }
}

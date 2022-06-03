package dbApp.db.tables.clients;

import dbApp.db.DataBase.DBService;
import dbApp.db.entities.AbstractPrimaryKey;
import dbApp.db.entities.AbstractTable;
import dbApp.db.entities.AbstractTableRow;
import dbApp.db.errors.InvalidRowFormatException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
    public void addRow(List<String> fieldsValues) throws SQLException {
        if (fieldsValues.size() != 4) {
            StringBuilder sb = new StringBuilder();
            for (String str : fieldsValues) {
                sb.append(str);
                sb.append(", ");
            }

            throw new InvalidRowFormatException("""
                Попытка создать строку в таблице "Технологии" в неправильном формате
                Строка: """ + sb);
        }

        String sql = """
            INSERT INTO clients
                (first_name, last_name, address, phone_number)
            VALUES
                (?, ?, ?, ?)
                """;

        PreparedStatement preparedStatement = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setString(1, fieldsValues.get(0));
            preparedStatement.setString(2, fieldsValues.get(1));
            preparedStatement.setString(3, fieldsValues.get(2));
            preparedStatement.setString(4, fieldsValues.get(3));
        } catch (IllegalArgumentException e) {
            throw new SQLException(e.getMessage());
        }
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void deleteRow(AbstractPrimaryKey primaryKeyValue) throws SQLException {
        String sql = "DELETE FROM clients WHERE id = ?";

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        preparedStatement.setInt(1,
            ((ClientsRowPrimaryKey) primaryKeyValue).getId());

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void updateRow(AbstractPrimaryKey primaryKeyValue, AbstractTableRow updatedRow)
            throws SQLException {
        ClientsRowPrimaryKey pk = (ClientsRowPrimaryKey) primaryKeyValue;
        ClientsRow row = (ClientsRow) updatedRow;

        String sql = """
            UPDATE clients SET id = ?, first_name = ?, last_name = ?, address = ?, phone_number = ?
            WHERE id = ?
            """;

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setInt(1, row.getId());
            preparedStatement.setString(2, row.getFirstName());
            preparedStatement.setString(3, row.getLastName());
            preparedStatement.setString(4, row.getAddress());
            preparedStatement.setString(5, row.getPhoneNumber());

            preparedStatement.setInt(6, pk.getId());
        } catch (IllegalArgumentException e) {
            throw new SQLException(e.getMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

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

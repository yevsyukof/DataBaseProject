package dbApp.model.db.tables.technologies;

import dbApp.model.db.DataBase.DBService;
import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;
import dbApp.model.db.entities.AbstractTable;
import dbApp.model.db.errors.InvalidRowFormatException;
import dbApp.model.db.tables.release_forms.ReleaseFormsRow;
import dbApp.model.db.tables.release_forms.ReleaseFormsRowPrimaryKey;
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
    }

    @Override
    public void addRow(List<String> fieldsValues) throws SQLException {
        if (fieldsValues.size() != 3) {
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
            INSERT INTO technologies
                (drug_id, make_duration, technology_desc)
            VALUES
                (?, ?, ?)
                """;

        PreparedStatement preparedStatement = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setInt(1, Integer.parseInt(fieldsValues.get(0)));
            preparedStatement.setInt(2, Integer.parseInt(fieldsValues.get(1)));
            preparedStatement.setString(3, fieldsValues.get(2));
        } catch (IllegalArgumentException e) {
            throw new SQLException(e.getMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
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
    public void updateRow(AbstractPrimaryKey primaryKeyValue, AbstractTableRow updatedRow)
            throws SQLException {
        TechnologiesRowPrimaryKey pk = (TechnologiesRowPrimaryKey) primaryKeyValue;
        TechnologiesRow row = (TechnologiesRow) updatedRow;

        String sql = """
            UPDATE technologies
            SET id = ?, drug_id = ?, make_duration = ?, technology_desc = ?
            WHERE id = ?
            """;

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setInt(1, row.getId());
            preparedStatement.setInt(2, row.getDrugId());
            preparedStatement.setInt(3, row.getMakeDuration());
            preparedStatement.setString(4, row.getTechnologyDesc());

            preparedStatement.setLong(5, pk.getId());
        } catch (IllegalArgumentException e) {
            throw new SQLException(e.getMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
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

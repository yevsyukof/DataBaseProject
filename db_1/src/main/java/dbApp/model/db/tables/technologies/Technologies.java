package dbApp.model.db.tables.technologies;

import dbApp.model.db.DataBase.DBService;
import dbApp.model.db.entities.PrimaryKey;
import dbApp.model.db.entities.TableRow;
import dbApp.model.db.tables.AbstractTable;
import dbApp.model.db.entities.Table;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Technologies extends AbstractTable implements Table {

    public Technologies(DBService dbService) throws SQLException {
        super("technologies", dbService);
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
    public void updateRow(PrimaryKey primaryKey, TableRow newRowValue) {

    }

    @Override
    public List<TableRow> readAll() {
        String query = "SELECT * FROM Technologies";

        try (Statement statement = dbService.getDbConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<TableRow> allRows = new ArrayList<>();

            while (resultSet.next()) {
                allRows.add(
                    new TechnologiesRow(
                        new TechnologiesPrimaryKey(resultSet.getInt("id")),
                        resultSet.getInt("drug_id"),
                        resultSet.getInt("make_duration"),
                        resultSet.getString("technology_desc"))
                );
            }

            return allRows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getColumnsNames() {
        return columns.stream().toList();
    }

    @Override
    public String getName() {
        return "technologies";
    }
}

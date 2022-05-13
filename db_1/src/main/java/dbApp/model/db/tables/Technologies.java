package dbApp.model.db.tables;

import dbApp.model.db.DataBase.DBService;
import dbApp.model.db.entities.TableRow;
import dbApp.model.db.entities.TechnologiesRow;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Technologies extends AbstractTable implements TableOperation {

    public Technologies(DBService dbService) {
        super("Technologies", dbService);
    }

    @Override
    public boolean createTable() {
        return false;
    }

    @Override
    public boolean dropTable() {
        return false;
    }

    @Override
    public boolean addRow() {
        return false;
    }

    @Override
    public boolean deleteRow() {
        return false;
    }

    @Override
    public boolean updateRow() {
        return false;
    }

    @Override
    public List<TableRow> readAll() {
        String query = "SELECT * FROM Technologies";

        try (Statement statement = dbService.getDbConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<TableRow> allRows = new ArrayList<>();

            while (resultSet.next()) {
                allRows.add(new TechnologiesRow(
                    resultSet.getBigDecimal("id"),
                    resultSet.getString("technology_name"),
                    resultSet.getString("technology_desc"))
                );
            }

            return allRows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

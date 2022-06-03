package dbApp.db.tables.drug_to_component;

import dbApp.db.DataBase.DBService;
import dbApp.db.entities.AbstractPrimaryKey;
import dbApp.db.entities.AbstractTable;
import dbApp.db.entities.AbstractTableRow;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DrugToComponent extends AbstractTable {

    public DrugToComponent(DBService dbService) throws SQLException {
        super("drug_to_component", dbService);
    }

    @Override
    protected void loadColumns() {
        columnsNames.add("drug_id");
        columnsNames.add("component_id");
        columnsNames.add("required_volume");

        translatedColumnsNames.add("ID лекарства");
        translatedColumnsNames.add("ID компонента");
        translatedColumnsNames.add("Требуемое количество");
    }

    @Override
    public void addRow(List<String> fieldsValues) throws SQLException {
        String sql = """
            INSERT INTO drug_to_component
                (drug_id, component_id, required_volume)
            VALUES
                (?, ?, ?)
                """;

        PreparedStatement preparedStatement = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setInt(1, Integer.parseInt(fieldsValues.get(0)));
            preparedStatement.setInt(2, Integer.parseInt(fieldsValues.get(1)));
            preparedStatement.setInt(3, Integer.parseInt(fieldsValues.get(2)));
        } catch (IllegalArgumentException e) {
            throw new SQLException("Введен невалидный аргумент: " + e.getLocalizedMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void deleteRow(AbstractPrimaryKey primaryKeyValue) throws SQLException {
        String sql = "DELETE FROM drug_to_component WHERE drug_id = ? AND component_id = ?";

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        preparedStatement.setInt(1,
            ((DrugToComponentRowUniqueKey) primaryKeyValue).getDrugId());
        preparedStatement.setInt(2,
            ((DrugToComponentRowUniqueKey) primaryKeyValue).getComponentId());

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public void updateRow(AbstractPrimaryKey primaryKeyValue, AbstractTableRow updatedRow)
            throws SQLException {
        DrugToComponentRowUniqueKey pk = (DrugToComponentRowUniqueKey) primaryKeyValue;
        DrugToComponentRow row = (DrugToComponentRow) updatedRow;

        String sql = """
            UPDATE drug_to_component SET drug_id = ?, component_id = ?, required_volume = ?
            WHERE drug_id = ? AND component_id = ?
            """;

        PreparedStatement preparedStatement
            = dbService.getDbConnection().prepareStatement(sql);

        try {
            preparedStatement.setInt(1, row.getDrugId());
            preparedStatement.setInt(2, row.getComponentId());
            preparedStatement.setInt(3, row.getRequiredVolume());

            preparedStatement.setInt(4, pk.getDrugId());
            preparedStatement.setInt(5, pk.getComponentId());
        } catch (IllegalArgumentException e) {
            throw new SQLException(e.getMessage());
        }

        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public List<AbstractTableRow> getAllRows() throws SQLException {
        String sql = "SELECT * FROM drug_to_component";

        Statement statement = dbService.getDbConnection().createStatement();

        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<AbstractTableRow> allTableRows = new ArrayList<>();

        while (resultSet.next()) {
            allTableRows.add(
                new DrugToComponentRow(
                    resultSet.getInt("drug_id"),
                    resultSet.getInt("component_id"),
                    resultSet.getInt("required_volume")
                )
            );
        }

        statement.close();
        return allTableRows;
    }

    @Override
    public String getTranslatedName() {
        return "Лекарство и его компоненты";
    }
}

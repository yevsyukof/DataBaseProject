package dbApp.model.db.tables.drug_to_component;

import dbApp.model.db.DataBase.DBService;
import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTable;
import dbApp.model.db.entities.AbstractTableRow;
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
    public void updateRow(AbstractPrimaryKey primaryKeyValue,
        AbstractTableRow updatedRow) throws SQLException {
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

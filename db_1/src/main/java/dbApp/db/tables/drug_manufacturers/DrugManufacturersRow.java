package dbApp.db.tables.drug_manufacturers;

import dbApp.db.entities.AbstractPrimaryKey;
import dbApp.db.entities.AbstractTableRow;
import java.sql.SQLException;
import java.util.List;
import lombok.Getter;

public class DrugManufacturersRow extends AbstractTableRow {

    @Getter
    private final Integer id;
    @Getter
    private final String name;

    public DrugManufacturersRow(Integer id, String name) {
        this.id = id;
        this.name = name;

        loadFieldsValues();
    }

    private DrugManufacturersRow(List<String> newFieldsValues) throws SQLException {
        try {
            this.id = Integer.valueOf(newFieldsValues.get(0));
            this.name = newFieldsValues.get(1);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new SQLException(e.getMessage());
        }

        loadFieldsValues();
    }

    @Override
    protected void loadFieldsValues() {
        fieldsValues.clear();
        fieldsValues.add(id);
        fieldsValues.add(name);
    }

    @Override
    public AbstractTableRow buildUpdatedCopy(List<String> newFieldsValues) throws SQLException {
        return new DrugManufacturersRow(newFieldsValues);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new DrugManufacturersRowPrimaryKey(new Object[]{id});
    }
}

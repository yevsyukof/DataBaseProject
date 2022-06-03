package dbApp.db.tables.release_forms;

import dbApp.db.entities.AbstractPrimaryKey;
import dbApp.db.entities.AbstractTableRow;
import java.sql.SQLException;
import java.util.List;
import lombok.Getter;

public class ReleaseFormsRow extends AbstractTableRow {

    @Getter
    private final Integer id;
    @Getter
    private final String drugType;

    public ReleaseFormsRow(Integer id, String drugType) {
        this.id = id;
        this.drugType = drugType;

        loadFieldsValues();
    }

    private ReleaseFormsRow(List<String> newFieldsValues) throws SQLException {
        try {
            this.id = Integer.valueOf(newFieldsValues.get(0));
            this.drugType = newFieldsValues.get(1);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new SQLException(e.getMessage());
        }
        loadFieldsValues();
    }

    @Override
    protected void loadFieldsValues() {
        fieldsValues.clear();
        fieldsValues.add(id);
        fieldsValues.add(drugType);
    }

    @Override
    public AbstractTableRow buildUpdatedCopy(List<String> newFieldsValues) throws SQLException {
        return new ReleaseFormsRow(newFieldsValues);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new ReleaseFormsRowPrimaryKey(new Object[]{id});
    }
}

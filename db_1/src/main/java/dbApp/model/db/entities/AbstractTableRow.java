package dbApp.model.db.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public abstract class AbstractTableRow {

    @Getter
    protected ArrayList<Object> fieldsValues;

    public AbstractTableRow() {
        fieldsValues = new ArrayList<>();
    }

    public Object getField(int columnIndex) {
        return fieldsValues.get(columnIndex);
    }

    protected abstract void loadFieldsValues();

    public abstract AbstractTableRow buildUpdatedCopy(List<String> newFieldsValues)
        throws SQLException;

    public abstract AbstractPrimaryKey getPrimaryKeyValue();
}

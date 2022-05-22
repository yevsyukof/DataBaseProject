package dbApp.model.db.entities;

import java.util.ArrayList;

public abstract class AbstractTableRow {

    protected final ArrayList<Object> fields;

    public AbstractTableRow() {
        fields = new ArrayList<>();
    }

    public Object getField(int columnIndex) {
        return fields.get(columnIndex);
    }

    public abstract AbstractPrimaryKey getPrimaryKeyValue();
}

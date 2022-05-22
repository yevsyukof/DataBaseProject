package dbApp.model.db.entities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public abstract class TableRow {

    protected final ArrayList<Object> fields = new ArrayList<>();

    public Object getField(int columnIndex) {
        return fields.get(columnIndex);
    }
}

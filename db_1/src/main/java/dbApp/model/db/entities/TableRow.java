package dbApp.model.db.entities;

import java.util.List;

public interface TableRow {

    String getColumnValue(String columnName);

    PrimaryKey getPrimaryKey();
}

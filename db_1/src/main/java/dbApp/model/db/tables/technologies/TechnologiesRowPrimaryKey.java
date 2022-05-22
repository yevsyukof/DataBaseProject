package dbApp.model.db.tables.technologies;

import dbApp.model.db.entities.PrimaryKey;

public class TechnologiesRowPrimaryKey extends PrimaryKey {

    private int id;

    public TechnologiesRowPrimaryKey(int keyValue) {
        id = keyValue;
    }

    @Override
    public Object getValue() {
        return id;
    }
}

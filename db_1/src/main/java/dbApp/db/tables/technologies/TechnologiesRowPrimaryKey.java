package dbApp.db.tables.technologies;

import dbApp.db.entities.AbstractPrimaryKey;

public class TechnologiesRowPrimaryKey extends AbstractPrimaryKey {

    public TechnologiesRowPrimaryKey(Object[] keyComponents) {
        super(keyComponents);
    }

    public int getId() {
        return (int) keyComponents[0];
    }
}

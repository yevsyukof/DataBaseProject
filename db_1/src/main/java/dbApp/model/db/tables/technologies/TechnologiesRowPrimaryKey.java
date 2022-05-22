package dbApp.model.db.tables.technologies;

import dbApp.model.db.entities.AbstractPrimaryKey;

public class TechnologiesRowPrimaryKey extends AbstractPrimaryKey {

    public TechnologiesRowPrimaryKey(Object[] keyComponents) {
        super(keyComponents);
    }

    public int getId() {
        return (int) keyComponents[0];
    }
}

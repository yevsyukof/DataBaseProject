package dbApp.model.db.tables.drugs;

import dbApp.model.db.entities.AbstractPrimaryKey;

public class DrugsRowPrimaryKey extends AbstractPrimaryKey {

    public DrugsRowPrimaryKey(Object[] keyComponents) {
        super(keyComponents);
    }

    public int getId() {
        return (int) keyComponents[0];
    }
}

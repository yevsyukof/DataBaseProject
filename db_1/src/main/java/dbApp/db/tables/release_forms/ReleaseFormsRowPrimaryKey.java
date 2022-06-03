package dbApp.db.tables.release_forms;

import dbApp.db.entities.AbstractPrimaryKey;

public class ReleaseFormsRowPrimaryKey extends AbstractPrimaryKey {

    public ReleaseFormsRowPrimaryKey(Object[] keyComponents) {
        super(keyComponents);
    }

    public int getId() {
        return (int) keyComponents[0];
    }
}

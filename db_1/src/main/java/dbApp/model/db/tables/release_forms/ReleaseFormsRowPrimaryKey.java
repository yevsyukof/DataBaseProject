package dbApp.model.db.tables.release_forms;

import dbApp.model.db.entities.AbstractPrimaryKey;

public class ReleaseFormsRowPrimaryKey extends AbstractPrimaryKey {

    public ReleaseFormsRowPrimaryKey(Object[] keyComponents) {
        super(keyComponents);
    }

    public int getId() {
        return (int) keyComponents[0];
    }
}

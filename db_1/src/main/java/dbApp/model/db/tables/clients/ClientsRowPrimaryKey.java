package dbApp.model.db.tables.clients;

import dbApp.model.db.entities.AbstractPrimaryKey;

public class ClientsRowPrimaryKey extends AbstractPrimaryKey {

    public ClientsRowPrimaryKey(Object[] keyComponents) {
        super(keyComponents);
    }

    public int getId() {
        return (int) keyComponents[0];
    }
}

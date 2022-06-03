package dbApp.db.tables.clients;

import dbApp.db.entities.AbstractPrimaryKey;

public class ClientsRowPrimaryKey extends AbstractPrimaryKey {

    public ClientsRowPrimaryKey(Object[] keyComponents) {
        super(keyComponents);
    }

    public int getId() {
        return (int) keyComponents[0];
    }
}

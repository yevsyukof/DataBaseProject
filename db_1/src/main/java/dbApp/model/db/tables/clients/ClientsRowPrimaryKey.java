package dbApp.model.db.tables.clients;

import dbApp.model.db.entities.PrimaryKey;

public class ClientsRowPrimaryKey extends PrimaryKey {

    private int id;

    public ClientsRowPrimaryKey(int keyValue) {
        this.id = keyValue;
    }

    @Override
    public Object getValue() {
        return id;
    }
}

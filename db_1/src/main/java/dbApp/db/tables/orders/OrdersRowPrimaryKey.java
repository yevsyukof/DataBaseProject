package dbApp.db.tables.orders;

import dbApp.db.entities.AbstractPrimaryKey;

public class OrdersRowPrimaryKey extends AbstractPrimaryKey {

    public OrdersRowPrimaryKey(Object[] keyComponents) {
        super(keyComponents);
    }

    public long getId() {
        return (long) keyComponents[0];
    }
}

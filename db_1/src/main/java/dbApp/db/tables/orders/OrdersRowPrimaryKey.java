package dbApp.db.tables.orders;

import dbApp.db.entities.AbstractPrimaryKey;

public class OrdersRowPrimaryKey extends AbstractPrimaryKey {

    public OrdersRowPrimaryKey(Object[] keyComponents) {
        super(keyComponents);
    }

    public int getId() {
        return (int) keyComponents[0];
    }
}

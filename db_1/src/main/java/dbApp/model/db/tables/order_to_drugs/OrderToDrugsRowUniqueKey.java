package dbApp.model.db.tables.order_to_drugs;

import dbApp.model.db.entities.AbstractPrimaryKey;

public class OrderToDrugsRowUniqueKey extends AbstractPrimaryKey {

    public OrderToDrugsRowUniqueKey(Object[] keyComponents) {
        super(keyComponents);
    }

    public Long getOrderId() {
        return (Long) keyComponents[0];
    }
}

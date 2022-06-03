package dbApp.db.tables.order_to_missing_drugs;

import dbApp.db.entities.AbstractPrimaryKey;

public class OrderToMissingDrugsRowUniqueKey extends AbstractPrimaryKey {

    public OrderToMissingDrugsRowUniqueKey(Object[] keyComponents) {
        super(keyComponents);
    }

    public Long getOrderId() {
        return (Long) keyComponents[0];
    }
}

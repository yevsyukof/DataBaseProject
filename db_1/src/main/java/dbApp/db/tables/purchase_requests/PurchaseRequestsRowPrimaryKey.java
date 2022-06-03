package dbApp.db.tables.purchase_requests;

import dbApp.db.entities.AbstractPrimaryKey;

public class PurchaseRequestsRowPrimaryKey extends AbstractPrimaryKey {

    public PurchaseRequestsRowPrimaryKey(Object[] keyComponents) {
        super(keyComponents);
    }

    public int getId() {
        return (int) keyComponents[0];
    }
}

package dbApp.model.db.tables.drug_manufacturers;

import dbApp.model.db.entities.AbstractPrimaryKey;

public class DrugManufacturersRowPrimaryKey extends AbstractPrimaryKey {

    public DrugManufacturersRowPrimaryKey(Object[] keyComponents) {
        super(keyComponents);
    }

    public int getId() {
        return (int) keyComponents[0];
    }
}

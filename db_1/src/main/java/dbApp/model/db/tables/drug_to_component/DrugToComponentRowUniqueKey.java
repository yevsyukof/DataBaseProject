package dbApp.model.db.tables.drug_to_component;

import dbApp.model.db.entities.AbstractPrimaryKey;

public class DrugToComponentRowUniqueKey extends AbstractPrimaryKey {

    public DrugToComponentRowUniqueKey(Object[] keyComponents) {
        super(keyComponents);
    }

    public int getDrugId() {
        return (int) keyComponents[0];
    }

    public int getComponentId() {
        return (int) keyComponents[1];
    }
}

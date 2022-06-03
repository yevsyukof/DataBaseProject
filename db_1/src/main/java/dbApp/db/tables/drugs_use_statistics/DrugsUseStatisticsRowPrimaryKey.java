package dbApp.db.tables.drugs_use_statistics;

import dbApp.db.entities.AbstractPrimaryKey;

public class DrugsUseStatisticsRowPrimaryKey extends AbstractPrimaryKey {

    public DrugsUseStatisticsRowPrimaryKey(Object[] keyComponents) {
        super(keyComponents);
    }

    public int getId() {
        return (int) keyComponents[0];
    }
}

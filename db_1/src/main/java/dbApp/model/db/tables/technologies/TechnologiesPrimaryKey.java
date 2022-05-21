package dbApp.model.db.tables.technologies;

import dbApp.model.db.entities.PrimaryKey;

public class TechnologiesPrimaryKey implements PrimaryKey {

    private int id;

    public TechnologiesPrimaryKey(int value) {
        id = value;
    }
}

package dbApp.model.db.entities;

public class PrimaryKey {

    private final Object primaryKeyValue;

    public PrimaryKey(Object keyValue) {
        this.primaryKeyValue = keyValue;
    }

    public Object getValue() {
        return primaryKeyValue;
    }
}

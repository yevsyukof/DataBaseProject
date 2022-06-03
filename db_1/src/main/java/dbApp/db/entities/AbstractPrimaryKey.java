package dbApp.db.entities;

import dbApp.utils.Pair;
import java.util.Set;

public abstract class AbstractPrimaryKey {

    protected final Object[] keyComponents;

    public AbstractPrimaryKey(Object[] keyComponents) {
        this.keyComponents = keyComponents;
    }

    public Object[] getValue() {
        return keyComponents;
    }
}

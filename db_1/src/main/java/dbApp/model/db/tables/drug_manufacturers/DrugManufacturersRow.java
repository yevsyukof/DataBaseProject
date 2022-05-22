package dbApp.model.db.tables.drug_manufacturers;

import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;
import lombok.Getter;

public class DrugManufacturersRow extends AbstractTableRow {

    @Getter
    private final Integer id;
    @Getter
    private final String name;

    public DrugManufacturersRow(Integer id, String name) {
        this.id = id;
        this.name = name;

        fields.add(id);
        fields.add(name);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new DrugManufacturersRowPrimaryKey(new Object[]{id});
    }
}

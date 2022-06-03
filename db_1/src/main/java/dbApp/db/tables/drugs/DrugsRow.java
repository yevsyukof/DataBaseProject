package dbApp.db.tables.drugs;

import dbApp.db.entities.AbstractPrimaryKey;
import dbApp.db.entities.AbstractTableRow;
import java.sql.SQLException;
import java.util.List;
import lombok.Getter;

public class DrugsRow extends AbstractTableRow {

    @Getter
    private final Integer id;
    @Getter
    private final String name;
    @Getter
    private final Integer releaseFormId;
    @Getter
    private final Integer drugManufacturerId;
    @Getter
    private final Integer inventoryVolume;
    @Getter
    private final Integer criticalRate;
    @Getter
    private final Integer price;

    public DrugsRow(Integer id, String name, Integer releaseFormId, Integer drugManufacturerId,
        Integer inventoryVolume, Integer criticalRate, Integer price) {
        this.id = id;
        this.name = name;
        this.releaseFormId = releaseFormId;
        this.drugManufacturerId = drugManufacturerId;
        this.inventoryVolume = inventoryVolume;
        this.criticalRate = criticalRate;
        this.price = price;

        loadFieldsValues();
    }

    private DrugsRow(List<String> newFieldsValues) throws SQLException {
        try {
            this.id = Integer.valueOf(newFieldsValues.get(0));
            this.name = newFieldsValues.get(1);
            this.releaseFormId = Integer.valueOf(newFieldsValues.get(2));
            this.drugManufacturerId = Integer.valueOf(newFieldsValues.get(3));
            this.inventoryVolume = Integer.valueOf(newFieldsValues.get(4));
            this.criticalRate = Integer.valueOf(newFieldsValues.get(5));
            this.price = Integer.valueOf(newFieldsValues.get(6));
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new SQLException(e.getMessage());
        }
        loadFieldsValues();
    }

    @Override
    protected void loadFieldsValues() {
        fieldsValues.clear();
        fieldsValues.add(id);
        fieldsValues.add(name);
        fieldsValues.add(releaseFormId);
        fieldsValues.add(drugManufacturerId);
        fieldsValues.add(inventoryVolume);
        fieldsValues.add(criticalRate);
        fieldsValues.add(price);
    }

    @Override
    public AbstractTableRow buildUpdatedCopy(List<String> newFieldsValues) throws SQLException {
        return new DrugsRow(newFieldsValues);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new DrugsRowPrimaryKey(new Object[]{id});
    }
}

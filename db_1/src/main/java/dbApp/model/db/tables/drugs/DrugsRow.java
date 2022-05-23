package dbApp.model.db.tables.drugs;

import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;
import dbApp.model.db.tables.drug_manufacturers.DrugManufacturersRowPrimaryKey;
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

        fields.add(id);
        fields.add(name);
        fields.add(releaseFormId);
        fields.add(drugManufacturerId);
        fields.add(inventoryVolume);
        fields.add(criticalRate);
        fields.add(price);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new DrugsRowPrimaryKey(new Object[]{id});
    }
}

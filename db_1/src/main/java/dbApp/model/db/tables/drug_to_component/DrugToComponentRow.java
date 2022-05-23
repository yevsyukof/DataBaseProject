package dbApp.model.db.tables.drug_to_component;

import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;
import lombok.Getter;

public class DrugToComponentRow extends AbstractTableRow {

    @Getter
    private final Integer drugId;
    @Getter
    private final Integer componentId;
    @Getter
    private final Integer requiredVolume;

    public DrugToComponentRow(Integer drugId, Integer componentId, Integer requiredVolume) {
        this.drugId = drugId;
        this.componentId = componentId;
        this.requiredVolume = requiredVolume;

        fields.add(drugId);
        fields.add(componentId);
        fields.add(requiredVolume);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new DrugToComponentRowUniqueKey(new Object[]{drugId, componentId});
    }
}
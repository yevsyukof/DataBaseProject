package dbApp.model.db.tables.order_to_drugs;

import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;
import dbApp.model.db.tables.drug_to_component.DrugToComponentRowUniqueKey;
import lombok.Getter;

public class OrderToDrugsRow extends AbstractTableRow {

    @Getter
    private final Long orderId;
    @Getter
    private final Integer drugId;
    @Getter
    private final Integer volume;
    @Getter
    private final Integer drugPrice;

    public OrderToDrugsRow(Long orderId, Integer drugId, Integer volume, Integer drugPrice) {
        this.orderId = orderId;
        this.drugId = drugId;
        this.volume = volume;
        this.drugPrice = drugPrice;

        fields.add(orderId);
        fields.add(drugId);
        fields.add(volume);
        fields.add(drugPrice);
    }


    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new DrugToComponentRowUniqueKey(new Object[]{orderId});
    }
}

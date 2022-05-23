package dbApp.model.db.tables.order_to_missing_drugs;

import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;
import dbApp.model.db.tables.drug_to_component.DrugToComponentRowUniqueKey;
import lombok.Getter;

public class OrderToMissingDrugsRow extends AbstractTableRow {

    @Getter
    private final Long orderId;
    @Getter
    private final Integer drugId;
    @Getter
    private final Integer deficit;

    public OrderToMissingDrugsRow(Long orderId, Integer drugId, Integer deficit) {
        this.orderId = orderId;
        this.drugId = drugId;
        this.deficit = deficit;

        fields.add(orderId);
        fields.add(drugId);
        fields.add(deficit);
    }


    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new DrugToComponentRowUniqueKey(new Object[]{orderId});
    }
}

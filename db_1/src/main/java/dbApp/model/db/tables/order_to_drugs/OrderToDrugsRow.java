package dbApp.model.db.tables.order_to_drugs;

import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;
import dbApp.model.db.tables.drug_to_component.DrugToComponentRowUniqueKey;
import java.sql.SQLException;
import java.util.List;
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

        loadFieldsValues();
    }

    private OrderToDrugsRow(List<String> newFieldsValues) throws SQLException {
        try {
            this.orderId = Long.valueOf(newFieldsValues.get(0));
            this.drugId = Integer.valueOf(newFieldsValues.get(1));
            this.volume = Integer.valueOf(newFieldsValues.get(2));
            this.drugPrice = Integer.valueOf(newFieldsValues.get(3));
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new SQLException(e.getMessage());
        }
        loadFieldsValues();
    }

    @Override
    protected void loadFieldsValues() {
        fieldsValues.clear();
        fieldsValues.add(orderId);
        fieldsValues.add(drugId);
        fieldsValues.add(volume);
        fieldsValues.add(drugPrice);
    }

    @Override
    public AbstractTableRow buildUpdatedCopy(List<String> newFieldsValues) throws SQLException {
        return new OrderToDrugsRow(newFieldsValues);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new DrugToComponentRowUniqueKey(new Object[]{orderId});
    }
}

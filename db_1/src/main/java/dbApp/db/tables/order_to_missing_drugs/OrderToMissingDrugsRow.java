package dbApp.db.tables.order_to_missing_drugs;

import dbApp.db.entities.AbstractPrimaryKey;
import dbApp.db.entities.AbstractTableRow;
import dbApp.db.tables.drug_to_component.DrugToComponentRowUniqueKey;
import java.sql.SQLException;
import java.util.List;
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

        loadFieldsValues();
    }

    private OrderToMissingDrugsRow(List<String> newFieldsValues) throws SQLException {
        try {
            this.orderId = Long.valueOf(newFieldsValues.get(0));
            this.drugId = Integer.valueOf(newFieldsValues.get(1));
            this.deficit = Integer.valueOf(newFieldsValues.get(2));
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
        fieldsValues.add(deficit);
    }

    @Override
    public AbstractTableRow buildUpdatedCopy(List<String> newFieldsValues) throws SQLException {
        return new OrderToMissingDrugsRow(newFieldsValues);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new DrugToComponentRowUniqueKey(new Object[]{orderId});
    }
}

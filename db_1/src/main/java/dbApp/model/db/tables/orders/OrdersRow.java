package dbApp.model.db.tables.orders;

import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;
import dbApp.model.db.tables.drug_manufacturers.DrugManufacturersRowPrimaryKey;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import lombok.Getter;

public class OrdersRow extends AbstractTableRow {

    @Getter
    private final Long id;
    @Getter
    private final Integer clientId;
    @Getter
    private final Date orderDate;
    @Getter
    private final Date readyDate;
    @Getter
    private final Date issueDate;

    public OrdersRow(Long id, Integer clientId, Date orderDate, Date readyDate, Date issueDate) {
        this.id = id;
        this.clientId = clientId;
        this.orderDate = orderDate;
        this.readyDate = readyDate;
        this.issueDate = issueDate;

        loadFieldsValues();
    }

    private OrdersRow(List<String> newFieldsValues) throws SQLException {
        try {
            this.id = Long.valueOf(newFieldsValues.get(0));
            this.clientId = Integer.valueOf(newFieldsValues.get(1));
            this.orderDate = Date.valueOf(newFieldsValues.get(2));
            this.readyDate = Date.valueOf(newFieldsValues.get(3));
            this.issueDate = Date.valueOf(newFieldsValues.get(4));
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new SQLException(e.getMessage());
        }
        loadFieldsValues();
    }

    @Override
    protected void loadFieldsValues() {
        fieldsValues.clear();
        fieldsValues.add(id);
        fieldsValues.add(clientId);
        fieldsValues.add(orderDate);
        fieldsValues.add(readyDate);
        fieldsValues.add(issueDate);
    }

    @Override
    public AbstractTableRow buildUpdatedCopy(List<String> newFieldsValues) throws SQLException {
        return new OrdersRow(newFieldsValues);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new DrugManufacturersRowPrimaryKey(new Object[]{id});
    }
}

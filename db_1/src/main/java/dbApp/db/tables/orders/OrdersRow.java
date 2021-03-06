package dbApp.db.tables.orders;

import dbApp.db.tables.drug_manufacturers.DrugManufacturersRowPrimaryKey;
import dbApp.db.entities.AbstractPrimaryKey;
import dbApp.db.entities.AbstractTableRow;
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
            this.orderDate = !newFieldsValues.get(2).isEmpty()
                ? Date.valueOf(newFieldsValues.get(2))
                : null;
            this.readyDate = !newFieldsValues.get(3).isEmpty()
                ? Date.valueOf(newFieldsValues.get(3))
                : null;
            this.issueDate = !newFieldsValues.get(4).isEmpty()
                ? Date.valueOf(newFieldsValues.get(4))
                : null;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            System.err.println("Error: OrdersRow");
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
        return new OrdersRowPrimaryKey(new Object[]{id});
    }
}

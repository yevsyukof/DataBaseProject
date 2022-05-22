package dbApp.model.db.tables.orders;

import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;
import dbApp.model.db.tables.drug_manufacturers.DrugManufacturersRowPrimaryKey;
import java.sql.Date;
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

        fields.add(id);
        fields.add(clientId);
        fields.add(orderDate);
        fields.add(readyDate);
        fields.add(issueDate);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new DrugManufacturersRowPrimaryKey(new Object[]{id});
    }
}

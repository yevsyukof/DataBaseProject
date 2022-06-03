package dbApp.db.tables.purchase_requests;

import dbApp.db.entities.AbstractPrimaryKey;
import dbApp.db.entities.AbstractTableRow;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import lombok.Getter;

public class PurchaseRequestsRow extends AbstractTableRow {

    @Getter
    private final Integer id;
    @Getter
    private final Integer requiredDrugId;
    @Getter
    private final Integer volume;
    @Getter
    private final Date creationDate;

    public PurchaseRequestsRow(Integer id, Integer requiredDrugId, Integer volume,
            Date creationDate) {
        this.id = id;
        this.requiredDrugId = requiredDrugId;
        this.volume = volume;
        this.creationDate = creationDate;

        loadFieldsValues();
    }

    private PurchaseRequestsRow(List<String> newFieldsValues) throws SQLException {
        try {
            this.id = Integer.valueOf(newFieldsValues.get(0));
            this.requiredDrugId = Integer.valueOf(newFieldsValues.get(1));
            this.volume = Integer.valueOf(newFieldsValues.get(2));
            this.creationDate = Date.valueOf(newFieldsValues.get(3));
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new SQLException(e.getMessage());
        }
        loadFieldsValues();
    }

    @Override
    protected void loadFieldsValues() {
        fieldsValues.clear();
        fieldsValues.add(id);
        fieldsValues.add(requiredDrugId);
        fieldsValues.add(volume);
        fieldsValues.add(creationDate);
    }

    @Override
    public AbstractTableRow buildUpdatedCopy(List<String> newFieldsValues) throws SQLException {
        return new PurchaseRequestsRow(newFieldsValues);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new PurchaseRequestsRowPrimaryKey(new Object[]{id});
    }
}

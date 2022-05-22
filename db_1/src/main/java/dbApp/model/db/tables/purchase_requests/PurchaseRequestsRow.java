package dbApp.model.db.tables.purchase_requests;

import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;
import dbApp.model.db.tables.drug_manufacturers.DrugManufacturersRowPrimaryKey;
import java.sql.Date;
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

        fields.add(id);
        fields.add(requiredDrugId);
        fields.add(volume);
        fields.add(creationDate);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new PurchaseRequestsRowPrimaryKey(new Object[]{id});
    }
}

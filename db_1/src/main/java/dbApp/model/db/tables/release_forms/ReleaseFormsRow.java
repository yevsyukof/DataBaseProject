package dbApp.model.db.tables.release_forms;

import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;
import dbApp.model.db.tables.purchase_requests.PurchaseRequestsRowPrimaryKey;
import lombok.Getter;

public class ReleaseFormsRow extends AbstractTableRow {

    @Getter
    private final Integer id;
    @Getter
    private final String drugType;

    public ReleaseFormsRow(Integer id, String drugType) {
        this.id = id;
        this.drugType = drugType;

        fields.add(id);
        fields.add(drugType);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new ReleaseFormsRowPrimaryKey(new Object[]{id});
    }
}

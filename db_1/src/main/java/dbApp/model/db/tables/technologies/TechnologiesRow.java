package dbApp.model.db.tables.technologies;

import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;

public class TechnologiesRow extends AbstractTableRow {

    private final int id;
    private final int drugId;
    private final int makeDuration;
    private final String technologyDesc;

    public TechnologiesRow(int id, int drugId,
        int makeDuration, String technologyDesc) {
        this.id = id;
        this.drugId = drugId;
        this.makeDuration = makeDuration;
        this.technologyDesc = technologyDesc;

        fields.add(id);
        fields.add(drugId);
        fields.add(makeDuration);
        fields.add(technologyDesc);
    }

    public int getId() {
        return id;
    }

    public int getDrugId() {
        return drugId;
    }

    public int getMakeDuration() {
        return makeDuration;
    }

    public String getTechnologyDesc() {
        return technologyDesc;
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new TechnologiesRowPrimaryKey(new Object[]{id});
    }
}

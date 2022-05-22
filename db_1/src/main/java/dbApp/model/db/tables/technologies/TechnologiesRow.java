package dbApp.model.db.tables.technologies;

import dbApp.model.db.entities.TableRow;

public class TechnologiesRow extends TableRow {

    private final TechnologiesRowPrimaryKey id;
    private final int drugId;
    private final int makeDuration;
    private final String technologyDesc;
    
    public TechnologiesRow(TechnologiesRowPrimaryKey id, int drugId,
                int makeDuration, String technologyDesc) {
        this.id = id;
        this.drugId = drugId;
        this.makeDuration = makeDuration;
        this.technologyDesc = technologyDesc;

        fields.add(id.getValue());
        fields.add(drugId);
        fields.add(makeDuration);
        fields.add(technologyDesc);
    }

//    @Override
//    public String getField(int columnIdx) {
//        return
//    }

//    @Override
//    public PrimaryKey getPrimaryKey() {
//        return id;
//    }

    public int getDrugId() {
        return drugId;
    }

    public int getMakeDuration() {
        return makeDuration;
    }

    public String getTechnologyDesc() {
        return technologyDesc;
    }
}

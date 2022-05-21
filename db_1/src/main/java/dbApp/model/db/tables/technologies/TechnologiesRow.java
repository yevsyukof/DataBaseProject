package dbApp.model.db.tables.technologies;

import dbApp.model.db.entities.PrimaryKey;
import dbApp.model.db.entities.TableRow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TechnologiesRow implements TableRow {

    private final TechnologiesPrimaryKey id;
    private final int drugId;
    private final int makeDuration;
    private final String technologyDesc;

    private final HashMap<String, String> rowValues;
    
    public TechnologiesRow(TechnologiesPrimaryKey id, int drugId,
                int makeDuration, String technologyDesc) {
        this.id = id;
        this.drugId = drugId;
        this.makeDuration = makeDuration;
        this.technologyDesc = technologyDesc;
        
        rowValues = new HashMap<>(4);
        rowValues.put("id", String.valueOf(id));
        rowValues.put("drug_id", String.valueOf(drugId));
        rowValues.put("make_duration", String.valueOf(makeDuration));
        rowValues.put("technology_desc", technologyDesc);
    }

    @Override
    public String getColumnValue(String columnName) {
        return rowValues.get(columnName);
    }

    @Override
    public PrimaryKey getPrimaryKey() {
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
}

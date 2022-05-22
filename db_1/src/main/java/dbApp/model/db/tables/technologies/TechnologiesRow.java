package dbApp.model.db.tables.technologies;

import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;
import dbApp.model.db.errors.InvalidRowFormatException;
import java.util.List;
import lombok.Getter;

public class TechnologiesRow extends AbstractTableRow {

    @Getter
    private final Integer id;
    @Getter
    private final Integer drugId;
    @Getter
    private final Integer makeDuration;
    @Getter
    private final String technologyDesc;

    public TechnologiesRow(Integer id, Integer drugId,
            Integer makeDuration, String technologyDesc) {
        this.id = id;
        this.drugId = drugId;
        this.makeDuration = makeDuration;
        this.technologyDesc = technologyDesc;

        fields.add(id);
        fields.add(drugId);
        fields.add(makeDuration);
        fields.add(technologyDesc);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new TechnologiesRowPrimaryKey(new Object[]{id});
    }
}

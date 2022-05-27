package dbApp.model.db.tables.technologies;

import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;
import java.sql.SQLException;
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

        loadFieldsValues();
    }

    private TechnologiesRow(List<String> newFieldsValues) throws SQLException {
        try {
            this.id = Integer.valueOf(newFieldsValues.get(0));
            this.drugId = Integer.valueOf(newFieldsValues.get(1));
            this.makeDuration = Integer.valueOf(newFieldsValues.get(2));
            this.technologyDesc = newFieldsValues.get(3);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new SQLException(e.getMessage());
        }
        loadFieldsValues();
    }

    @Override
    protected void loadFieldsValues() {
        fieldsValues.clear();
        fieldsValues.add(id);
        fieldsValues.add(drugId);
        fieldsValues.add(makeDuration);
        fieldsValues.add(technologyDesc);
    }

    @Override
    public AbstractTableRow buildUpdatedCopy(List<String> newFieldsValues) throws SQLException {
        return new TechnologiesRow(newFieldsValues);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new TechnologiesRowPrimaryKey(new Object[]{ id });
    }
}

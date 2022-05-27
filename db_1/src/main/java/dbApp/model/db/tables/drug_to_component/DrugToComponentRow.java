package dbApp.model.db.tables.drug_to_component;

import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;
import java.sql.SQLException;
import java.util.List;
import lombok.Getter;

public class DrugToComponentRow extends AbstractTableRow {

    @Getter
    private final Integer drugId;
    @Getter
    private final Integer componentId;
    @Getter
    private final Integer requiredVolume;

    public DrugToComponentRow(Integer drugId, Integer componentId, Integer requiredVolume) {
        this.drugId = drugId;
        this.componentId = componentId;
        this.requiredVolume = requiredVolume;

        loadFieldsValues();
    }

    private DrugToComponentRow(List<String> newFieldsValues) throws SQLException {
        try {
            this.drugId = Integer.valueOf(newFieldsValues.get(0));
            this.componentId = Integer.valueOf(newFieldsValues.get(1));
            this.requiredVolume = Integer.valueOf(newFieldsValues.get(2));
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new SQLException(e.getMessage());
        }

        loadFieldsValues();
    }

    @Override
    protected void loadFieldsValues() {
        fieldsValues.clear();
        fieldsValues.add(drugId);
        fieldsValues.add(componentId);
        fieldsValues.add(requiredVolume);
    }

    @Override
    public AbstractTableRow buildUpdatedCopy(List<String> newFieldsValues) throws SQLException {
        return new DrugToComponentRow(newFieldsValues);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new DrugToComponentRowUniqueKey(new Object[]{drugId, componentId});
    }
}
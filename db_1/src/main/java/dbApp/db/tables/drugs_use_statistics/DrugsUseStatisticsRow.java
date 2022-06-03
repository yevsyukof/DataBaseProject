package dbApp.db.tables.drugs_use_statistics;

import dbApp.db.entities.AbstractTableRow;
import dbApp.db.tables.drug_manufacturers.DrugManufacturersRowPrimaryKey;
import dbApp.db.entities.AbstractPrimaryKey;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import lombok.Getter;

public class DrugsUseStatisticsRow extends AbstractTableRow {

    @Getter
    private final Integer id;
    @Getter
    private final Integer drugId;
    @Getter
    private final Long orderId;
    @Getter
    private final Date recordDate;
    @Getter
    private final Integer volume;

    public DrugsUseStatisticsRow(Integer id, Integer drugId, Long orderId,
           Date recordDate, Integer volume) {
        this.id = id;
        this.drugId = drugId;
        this.orderId = orderId;
        this.recordDate = recordDate;
        this.volume = volume;

        loadFieldsValues();
    }

    private DrugsUseStatisticsRow(List<String> newFieldsValues) throws SQLException {
        try {
            this.id = Integer.valueOf(newFieldsValues.get(0));
            this.drugId = Integer.valueOf(newFieldsValues.get(1));
            this.orderId = Long.valueOf(newFieldsValues.get(2));
            this.recordDate = Date.valueOf(newFieldsValues.get(3));
            this.volume = Integer.valueOf(newFieldsValues.get(4));
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
        fieldsValues.add(orderId);
        fieldsValues.add(recordDate);
        fieldsValues.add(volume);
    }

    @Override
    public AbstractTableRow buildUpdatedCopy(List<String> newFieldsValues) throws SQLException {
        return new DrugsUseStatisticsRow(newFieldsValues);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new DrugManufacturersRowPrimaryKey(new Object[]{id});
    }
}

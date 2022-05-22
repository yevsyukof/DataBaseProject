package dbApp.model.db.tables.drugs_use_statistics;

import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;
import dbApp.model.db.tables.drug_manufacturers.DrugManufacturersRowPrimaryKey;
import java.sql.Date;
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

        fields.add(id);
        fields.add(drugId);
        fields.add(orderId);
        fields.add(recordDate);
        fields.add(volume);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new DrugManufacturersRowPrimaryKey(new Object[]{id});
    }
}

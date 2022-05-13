package dbApp.model.db.entities;

import java.math.BigDecimal;

public class TechnologiesRow extends TableRow{

    private final BigDecimal id;
    private final String technologyName;
    private final String technologyDesc;

    public TechnologiesRow(BigDecimal id, String technologyName, String technologyDesc) {
        this.id = id;
        this.technologyName = technologyName;
        this.technologyDesc = technologyDesc;
    }


}

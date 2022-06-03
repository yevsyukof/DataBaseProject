package dbApp.db.actions;

import dbApp.db.DataBase;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import lombok.Setter;

public class AddDrugAction {

    private final DataBase dataBase;

    private final Set<Integer> drugComponentsIds;

    @Setter
    private Integer releaseFormId = null;
    @Setter
    private Integer drugManufacturerId = null;

    private final Integer inventoryVolume = 0;

    @Setter
    private String drugName = null;
    @Setter
    private Integer criticalRate = null;
    @Setter
    private Integer price = null;


    public AddDrugAction(DataBase dataBase) {
        this.dataBase = dataBase;
        drugComponentsIds = new LinkedHashSet<>();
    }

    public Map<String, Integer> getPossibleComponentsMap() throws SQLException {
        return dataBase.getDrugs().getDrugsMap();
    }

    public Map<String, Integer> getPossibleDrugManufacturersMap() throws SQLException {
        return dataBase.getDrugManufacturers().getDrugManufacturersMap();
    }

    public Map<String, Integer> getPossibleReleaseFormsMap() throws SQLException {
        return dataBase.getReleaseForms().getReleaseFormsMap();
    }

    public void addComponent(Integer componentId) {
        drugComponentsIds.add(componentId);
    }

    public void addDrugIntoBase() throws SQLException {
        if (drugComponentsIds.isEmpty()) {
            throw new SQLException("Не указаны компоненты лекарства");
        }
    }
}

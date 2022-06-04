package dbApp.db.actions;

import dbApp.db.DataBase;
import dbApp.utils.Pair;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import lombok.Setter;

public class AddDrugAction {

    private final DataBase dataBase;

    // <component_id, volume>
    @Setter
    private List<Pair<Integer, Integer>> drugComponents;

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

    public void addDrugIntoBase() throws SQLException {
        if (drugComponents.isEmpty()) {
            throw new SQLException("Не указаны компоненты лекарства");
        }

        try {
            /// Начало транзакции
            dataBase.getDbService().getDbConnection().setAutoCommit(false);
            {
                ArrayList<Object> args = new ArrayList<>();
                args.add(drugName);
                args.add(releaseFormId);
                args.add(drugManufacturerId);
                args.add(criticalRate);
                args.add(price);

                Integer newDrugId = dataBase.getDrugs().addNewDrugIntoBase(args);

                for (Pair<Integer, Integer> drugComponent : drugComponents) {
                    dataBase.getDrugToComponent()
                        .addDrugComponentIntoBase (new Integer[] {
                            newDrugId,
                            drugComponent.first(),
                            drugComponent.second()
                        });
                }
            }
            // Конец транзакции
            dataBase.getDbService().getDbConnection().commit();
        } catch (SQLException e) {
            dataBase.getDbService().getDbConnection().setAutoCommit(true);
            throw new SQLException(e.getMessage());
        }
        dataBase.getDbService().getDbConnection().setAutoCommit(true);
    }
}

package dbApp.db.tables.clients;

import dbApp.db.entities.AbstractPrimaryKey;
import dbApp.db.entities.AbstractTableRow;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

public class ClientsRow extends AbstractTableRow {

    @Getter
    private final Integer id;
    @Getter
    private final String firstName;
    @Getter
    private final String lastName;
    @Getter
    private final String address;
    @Getter
    private final String phoneNumber;

    public ClientsRow(Integer id, String firstName, String lastName,
        String address, String phoneNumber) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;

        loadFieldsValues();
    }

    private ClientsRow(List<String> newFieldsValues) throws SQLException {
        try {
            this.id = Integer.valueOf(newFieldsValues.get(0));
            this.firstName = newFieldsValues.get(1);
            this.lastName = newFieldsValues.get(2);
            this.address = newFieldsValues.get(3);
            this.phoneNumber = newFieldsValues.get(4);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            throw new SQLException(e.getMessage());
        }

        loadFieldsValues();
    }

    @Override
    protected void loadFieldsValues() {
        fieldsValues.clear();
        fieldsValues.add(id);
        fieldsValues.add(firstName);
        fieldsValues.add(lastName);
        fieldsValues.add(address);
        fieldsValues.add(phoneNumber);
    }

//    @Override
//    public Map<String, Object> getMap() {
//        Map<String, Object> fieldsMap = new LinkedHashMap<>();
//
//        fieldsMap.put();
//
//        return fieldsMap;
//    }

    @Override
    public AbstractTableRow buildUpdatedCopy(List<String> newFieldsValues) throws SQLException {
        return new ClientsRow(newFieldsValues);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new ClientsRowPrimaryKey(new Object[]{id});
    }
}

package dbApp.model.db.tables.clients;

import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;
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

        fields.add(id);
        fields.add(firstName);
        fields.add(lastName);
        fields.add(address);
        fields.add(phoneNumber);
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new ClientsRowPrimaryKey(new Object[]{id});
    }
}

package dbApp.model.db.tables.clients;

import dbApp.model.db.entities.AbstractPrimaryKey;
import dbApp.model.db.entities.AbstractTableRow;

public class ClientsRow extends AbstractTableRow {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String phoneNumber;

    public ClientsRow(int id, String firstName, String lastName,
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

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public AbstractPrimaryKey getPrimaryKeyValue() {
        return new ClientsRowPrimaryKey(new Object[]{id});
    }
}

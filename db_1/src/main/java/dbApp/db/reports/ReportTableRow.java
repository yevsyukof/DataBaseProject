package dbApp.db.reports;

import java.util.ArrayList;
import java.util.List;

public class ReportTableRow {

    private final List<Object> fieldsValues;

    public ReportTableRow() {
        fieldsValues = new ArrayList<>();
    }

    public ReportTableRow(List<Object> fieldsValues) {
        this.fieldsValues = fieldsValues;
    }

    public ReportTableRow(Object[] fieldsValues) {
        this.fieldsValues = List.of(fieldsValues);
    }

    public Object getField(int columnIndex) {
        return fieldsValues.get(columnIndex);
    }

    public void addField(Object fieldValue) {
        fieldsValues.add(fieldValue);
    }
}

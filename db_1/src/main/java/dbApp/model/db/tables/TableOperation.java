package dbApp.model.db.tables;

import dbApp.model.db.entities.TableRow;
import java.sql.ResultSet;
import java.util.List;

public interface TableOperation {

    boolean createTable();

    boolean dropTable();

    boolean addRow();

    boolean deleteRow();

    boolean updateRow();

    List<TableRow> readAll();
}

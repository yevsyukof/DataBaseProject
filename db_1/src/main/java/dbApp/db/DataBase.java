package dbApp.db;

import dbApp.db.tables.purchase_requests.PurchaseRequests;
import dbApp.db.tables.release_forms.ReleaseForms;
import dbApp.db.entities.AbstractTable;
import dbApp.db.tables.clients.Clients;
import dbApp.db.tables.drug_manufacturers.DrugManufacturers;
import dbApp.db.tables.drug_to_component.DrugToComponent;
import dbApp.db.tables.drugs.Drugs;
import dbApp.db.tables.drugs_use_statistics.DrugsUseStatistics;
import dbApp.db.tables.order_to_drugs.OrderToDrugs;
import dbApp.db.tables.order_to_missing_drugs.OrderToMissingDrugs;
import dbApp.db.tables.orders.Orders;
import dbApp.db.tables.technologies.Technologies;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class DataBase {

    public static class DBService {

        private Connection dbConnection = null;

        public DBService(String dbURL, String username, char[] password) throws SQLException {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                System.err
                    .println("APP INIT ERROR: JDBC Driver is not found");
                System.exit(1);
            }

            dbConnection = DriverManager
                .getConnection(dbURL, username, String.valueOf(password));
        }

        public Connection getDbConnection() {
            try {
                if (dbConnection == null || dbConnection.isClosed()) {
                    return null; // TODO: тут нужна бизнес-логика
                } else {
                    return dbConnection;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public void close() {
            try {
                dbConnection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public enum DbUserRole {
        ADMIN,
        CLIENT,
        SUPPLIER
    }

    private final DbUserRole curSessionUserRole;

    @Getter
    private final Technologies technologiesTable;
    @Getter
    private final Clients clientsTable;
    @Getter
    private final DrugManufacturers drugManufacturers;
    @Getter
    private final Drugs drugs;
    @Getter
    private final DrugsUseStatistics drugsUseStatistics;
    @Getter
    private final Orders orders;
    @Getter
    private final PurchaseRequests purchaseRequests;
    @Getter
    private final ReleaseForms releaseForms;
    @Getter
    private final DrugToComponent drugToComponent;
    @Getter
    private final OrderToDrugs orderToDrugs;
    @Getter
    private final OrderToMissingDrugs orderToMissingDrugs;

    private final DBService dbService;

    public DataBase(String dbURL, String username, char[] password) throws SQLException {
        dbService = new DBService(dbURL, username, password);

        technologiesTable = new Technologies(dbService);
        clientsTable = new Clients(dbService);
        drugManufacturers = new DrugManufacturers(dbService);
        drugs = new Drugs(dbService);
        drugsUseStatistics = new DrugsUseStatistics(dbService);
        orders = new Orders(dbService);
        purchaseRequests = new PurchaseRequests(dbService);
        releaseForms = new ReleaseForms(dbService);
        drugToComponent = new DrugToComponent(dbService);
        orderToDrugs = new OrderToDrugs(dbService);
        orderToMissingDrugs = new OrderToMissingDrugs(dbService);

        curSessionUserRole = identifyUserRole(username);
        setRoleRights();
    }

    private DbUserRole identifyUserRole(String username) throws SQLException {
        String sql = """
            SELECT rolname FROM pg_roles
            WHERE oid = (SELECT roleid FROM pg_auth_members
                         WHERE member = (SELECT usesysid FROM pg_user
                                         WHERE usename = ?))""";

        PreparedStatement preparedStatement = dbService.getDbConnection().prepareStatement(sql);
        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        switch (resultSet.getString("rolname")) {
            case "administrators" -> {
                return DbUserRole.ADMIN;
            }
            case "suppliers" -> {
                return DbUserRole.SUPPLIER;
            }
            case "clients" -> {
                return DbUserRole.CLIENT;
            }
            default -> throw new RuntimeException("Неизвестная роль базы данных");
        }
    }

    private void setRoleRights() throws SQLException {
        String sql = switch (curSessionUserRole) {
            case ADMIN -> "SET ROLE administrators";
            case CLIENT -> "SET ROLE clients";
            case SUPPLIER -> "SET ROLE suppliers";
        };

        Statement statement = dbService.getDbConnection().createStatement();
        statement.execute(sql);
        statement.close();
    }

    public DbUserRole getCurSessionUserRole() {
        return curSessionUserRole;
    }

    public DBService getDbService() {
        return dbService;
    }

    public List<AbstractTable> getTables() {
        ArrayList<AbstractTable> tables = new ArrayList<>();
        tables.add(technologiesTable);
        tables.add(clientsTable);
        tables.add(drugManufacturers);
        tables.add(drugs);
        tables.add(drugsUseStatistics);
        tables.add(orders);
        tables.add(purchaseRequests);
        tables.add(releaseForms);
        tables.add(drugToComponent);
        tables.add(orderToDrugs);
        tables.add(orderToMissingDrugs);

        return tables;
    }

    public void close() {
        dbService.close();
    }
}

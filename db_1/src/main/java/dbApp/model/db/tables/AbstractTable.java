package dbApp.model.db.tables;

import dbApp.model.db.DataBase;
import dbApp.model.db.DataBase.DBService;
import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

// Сервисный родительский класс, куда вынесена реализация общих действий для всех таблиц
public abstract class AbstractTable {

    protected Set<String> columns;

    protected final DBService dbService;
    protected final String tableName;  // Имя таблицы

    // Для реальной таблицы передадим в конструктор её имя
    public AbstractTable(String tableName, DBService dbService) throws SQLException {
        this.tableName = tableName;
        this.dbService = dbService;

        columns = new HashSet<>();
        loadColumns();
    }

    protected void loadColumns() throws SQLException {
        String sql = """
            SELECT column_name FROM information_schema.columns
            	WHERE table_name = ?;
                """;

        PreparedStatement preparedStatement = dbService.getDbConnection().prepareStatement(sql);
        preparedStatement.setString(1, tableName);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            columns.add(String.valueOf(resultSet.getObject(1)));
        }
    }

//    public void executeSqlStatement(String sql) throws SQLException {
////        reopenConnection(); // переоткрываем (если оно неактивно) соединение с СУБД
//
//        // Создаем statement для выполнения sql-команд
//        Statement statement = dbService.getDbConnection().createStatement();
//        statement.execute(sql); // Выполняем statement - sql команду
//        statement.close();      // Закрываем statement для фиксации изменений в СУБД
//    }
}

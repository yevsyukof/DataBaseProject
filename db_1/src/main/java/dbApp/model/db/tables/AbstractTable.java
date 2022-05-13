package dbApp.model.db.tables;

import dbApp.model.db.DataBase;
import dbApp.model.db.DataBase.DBService;
import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Сервисный родительский класс, куда вынесена реализация общих действий для всех таблиц
public abstract class AbstractTable {

    protected final DBService dbService;
    protected final String tableName;  // Имя таблицы

    // Для реальной таблицы передадим в конструктор её имя
    public AbstractTable(String tableName, DBService dbService) {
        this.tableName = tableName;
        this.dbService = dbService;
    }


    public void executeSqlStatement(String sql) throws SQLException {
//        reopenConnection(); // переоткрываем (если оно неактивно) соединение с СУБД

        // Создаем statement для выполнения sql-команд
        Statement statement = dbService.getDbConnection().createStatement();
        statement.execute(sql); // Выполняем statement - sql команду
        statement.close();      // Закрываем statement для фиксации изменений в СУБД
    }
}

package ua.com.juja.sqlcmd.dao.manager;

import ua.com.juja.sqlcmd.dao.DataSet;
import ua.com.juja.sqlcmd.model.DatabaseConnection;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface DatabaseManager {

    void connect(String server, String port, String database, String userName, String password);

    void disconnect();

    DatabaseConnection getDatabaseConnection();

    boolean isConnected();

    //TODO database is field of databaseConnection
    String currentDatabase();

    void createDatabase(String databaseName);

    void dropDatabase(String databaseName);

    Set<String> getDatabaseNames();

    void createTable(String tableName, String query);

    void dropTable(String tableName);

    Set<String> getTableColumns(String tableName);

    List<DataSet> getTableData(String tableName);

    int getTableSize(String tableName);

    //TODO sort by name
    //TODO remove from this list sqlcmd_log
    Set<String> getTableNames();

    void clearTable(String tableName);

    boolean existRecord(String tableName, String field, String parameter);

    DataSet getRecordData(String tableName, int id);

    void createRecord(String tableName, DataSet input);

    void updateRecord(String tableName, int id, DataSet input);

    void deleteRecord(String tableName, int id);

    List<DataSet> executeQuery(String query);

    void createAction(Date date, String userName, String dbName, String action);
}

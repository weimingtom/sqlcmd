package ua.com.juja.sqlcmd.model;

import ua.com.juja.sqlcmd.controller.PropertiesLoader;

import java.sql.*;
import java.util.*;

public class JDBCDatabaseManager implements DatabaseManager {

    private Connection connection;

    @Override
    public void connect(String database, String userName, String password) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL driver not found", e);
        }
        try {
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection(
                    PropertiesLoader.DATABASE_URL + database, userName, password);
        } catch (SQLException e) {
            connection = null;
            throw new SQLException(
                    String.format("Failed to connect to database: %s, user: %s", database, userName), e);
        }
    }

    @Override
    public List<DataSet> getTableData(String tableName) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(String.format("SELECT * FROM %s", tableName))) {
            return getDataSets(rs);
        }
    }

    @Override
    public int getSize(String tableName) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(String.format("SELECT COUNT(*) FROM %s", tableName))) {
            rs.next();
            int size = rs.getInt(1);
            return size;
        }
    }

    @Override
    public Set<String> getTableNames() throws SQLException {
        Set<String> tables = new LinkedHashSet<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT table_name FROM information_schema.tables" +
                     " WHERE table_schema = 'public' AND table_type = 'BASE TABLE'")) {
            while ((rs.next())) {
                tables.add(rs.getString("table_name"));
            }
            return tables;
        }
    }

    @Override
    public void clear(String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("DELETE FROM %s", tableName));
        }
    }

    @Override
    public void create(String tableName, DataSet input) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String columns = "";
            for (String name : input.getNames()) {
                columns = columns + name + ",";
            }
            columns = columns.substring(0, columns.length() - 1);
            String values = "";
            for (Object value : input.getValues()) {
                values += "'" + value.toString() + "',";
            }
            values = values.substring(0, values.length() - 1);
            statement.executeUpdate(String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, values));
        }
    }

    @Override
    public void update(String tableName, int id, DataSet input) throws SQLException {
        String fields = "";
        for (String name : input.getNames()) {
            fields += String.format("%s =? ", name) + ",";
        }
        fields = fields.substring(0, fields.length() - 1);
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                String.format("UPDATE %s SET %s WHERE id=?", tableName, fields))) {
            int index = 1;
            for (Object value : input.getValues()) {
                preparedStatement.setObject(index++, value);
            }
            preparedStatement.setInt(index, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(String tableName, int id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                String.format("DELETE FROM %s WHERE id=?", tableName))) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Set<String> getTableColumns(String tableName) throws SQLException {
        Set<String> columns = new LinkedHashSet<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(
                     String.format("SELECT column_name FROM information_schema.columns" +
                             " WHERE  table_schema = 'public' and table_name = '%s'", tableName))) {
            while ((rs.next())) {
                columns.add(rs.getString("column_name"));
            }
            return columns;
        }
    }

    @Override
    public List<DataSet> executeQuery(String query) throws SQLException {
        if (query.toLowerCase().startsWith("select")) {
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(query)) {
                return getDataSets(rs);
            }
        } else {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(query);
                return new ArrayList<>();
            }
        }
    }

    private List<DataSet> getDataSets(ResultSet rs) throws SQLException {
        List<DataSet> result = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        while ((rs.next())) {
            DataSet dataSet = new DataSet();
            for (int index = 1; index <= rsmd.getColumnCount(); index++) {
                dataSet.put(rsmd.getColumnName(index), rs.getObject(index));
            }
            result.add(dataSet);
        }
        return result;
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }
}

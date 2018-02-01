package datalayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.log.StandardLogger;

public class DatabaseExecutor {
    private final StandardLogger log = new StandardLogger("database");
    private DatabaseConnector connectionToDB;
    private Statement statement;

    public DatabaseExecutor() {
        connectionToDB = new DatabaseConnector();
    }

    /*execute SELECT*/
    public ResultSet executeGetQuery(String sql) {
        statement = connectionToDB.makeConnection();
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            log.logError(e.getMessage());
        }
        return rs;
    }
    /*execute INSERT, ALTER, ...*/
    public boolean executeUpdateQuery(String sql) {
        statement = connectionToDB.makeConnection();
        try {
            int result = statement.executeUpdate(sql);
            if (result > 0) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException e) {
            log.logError(e.getMessage());
        }
        return false;
    }

    public int executeAutoIncrementUpdateQuery(String sql) {
        statement = connectionToDB.makeConnection();
        ResultSet rs;
        int primaryKey = -1;
        try {
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                primaryKey = rs.getInt(1);
            }
        }
        catch (SQLException e) {
            log.logError(e.getMessage());
        }
        return primaryKey;
    }

    public void closeConnection() {
        try {
            statement.close();
            connectionToDB.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

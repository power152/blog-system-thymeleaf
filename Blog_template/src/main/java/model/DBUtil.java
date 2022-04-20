package model;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 通过这个类封装建立连接操作！！！
public class DBUtil {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/BlogSystem?characterEncoding=utf8&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "0123456789";

    //找到数据库用到数据源
    private static DataSource dataSource = new MysqlDataSource();

    // 初始化 datasource里面的内容
    static {
        ((MysqlDataSource)dataSource).setURL(URL);
        ((MysqlDataSource)dataSource).setUser(USERNAME);
        ((MysqlDataSource)dataSource).setPassword(PASSWORD);

    }

    // 创建一个方法建立连接
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // 释放资源
    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}



//    private static final String URL = "jdbc:mysql//127.0.0.1:3306/BlogSystem?characterEncoding=utf8&useSSL=false";
//    private static final String USERNAME = "root";
//    private static final String PASSWORD = "0123456789";
//
//    // 建立数据库的连接，直接引用DataSource获取数据库的连接对象即可;用于获取操作数据Connection对象
//    //DataSource可以看作数据源，它封装了数据库参数，连接数据库，程序中操作DataSource对象即可对数据库进行增删改查操作。
//    private static DataSource dataSource = new MysqlDataSource();
//
//    static {
//        // 对上面 DataSource 进行初始化操作
//        ((MysqlDataSource)dataSource).setURL(URL);
//        ((MysqlDataSource)dataSource).setUser(USERNAME);
//        ((MysqlDataSource)dataSource).setPassword(PASSWORD);
//
//    }
//
//    // 需要提供一个方法, 来建立连接.
//    // Connection 选择是java.sql
//    public static Connection getConnection() throws SQLException {
//        return dataSource.getConnection();
//    }
//
//    // 释放资源
//    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet) {
//        if (connection != null) {
//            try {
//                resultSet.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (statement != null) {
//            try {
//                statement.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (resultSet != null) {
//            try {
//                resultSet.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
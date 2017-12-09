package com.mobile.vnews.dao;

import com.mobile.vnews.util.PropertiesUtils;
import com.mobile.vnews.module.Notice;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Create by xuantang
 * @date on 12/8/17
 */
public class Dao {
    /**
     *
     * @param newsID
     * @return
     */
    public static List<String> getRelationUsersOnNews(int newsID) {
        if (newsID < 0) {
            throw new IllegalArgumentException();
        }
        List<String> users = new ArrayList<>();
        // get connection
        Connection conn = getConnection();

        String sql = "(SELECT fromID FROM notice " +
                "WHERE newsID = ?) " +
                "UNION " +
                "(SELECT toID FROM notice " +
                "WHERE newsID = ?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, newsID);
            preparedStatement.setInt(2, newsID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                users.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


    /**
     *
     * @param notice
     */
    public static void addNotice(Notice notice) {
        // get connection
        Connection conn = getConnection();
        /**
         * newsID
         * fromID    VARCHAR(20)                         NULL,
         * toID      VARCHAR(20)                         NULL,
         * content   TEXT                                NOT NULL,
         * timestamp
         */
        // sql
        String sql = "insert into notice(newsID, fromID, toID, content, timestamp) " +
                "values(?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement;
        try {
            // insert to mysql
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, notice.getNewsID());
            preparedStatement.setString(2, notice.getFromID());
            preparedStatement.setString(3, notice.getToID());
            preparedStatement.setString(4, notice.getContent());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.executeUpdate();
            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get connection
     * @return
     */
    private static Connection getConnection() {
        Connection conn = null;
        String driver = PropertiesUtils.getValue("driver");
        String url = PropertiesUtils.getValue("url");
        String username = PropertiesUtils.getValue("username");
        String password = PropertiesUtils.getValue("password");
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}

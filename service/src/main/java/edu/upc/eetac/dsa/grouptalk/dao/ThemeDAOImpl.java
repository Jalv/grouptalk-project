package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Theme;
import edu.upc.eetac.dsa.grouptalk.entity.ThemeCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by juan on 26/10/15.
 */
public class ThemeDAOImpl implements ThemeDAO {
    @Override
    public Theme createTheme(String userid, String groupid, String subject, String content) throws SQLException{
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ThemeDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(ThemeDAOQuery.CREATE_THEME);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(2, groupid);
            stmt.setString(4, subject);
            stmt.setString(5, content);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getThemeById(id);
    }

    @Override
    public Theme getThemeById(String id) throws SQLException{
        Theme theme = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ThemeDAOQuery.GET_THEME_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                theme = new Theme();
                theme.setId(rs.getString("id"));
                theme.setUserid(rs.getString("userid"));
                theme.setGroupid(rs.getString("groupid"));
                theme.setSubject(rs.getString("subject"));
                theme.setContent(rs.getString("content"));
                theme.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                theme.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return theme;
    }

    @Override
    public ThemeCollection getThemesByGroupId(String groupid) throws SQLException{
        ThemeCollection themeCollection = new ThemeCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(ThemeDAOQuery.GET_THEMES_BY_GROUPID);
            stmt.setString(1, groupid);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Theme theme = new Theme();
                theme.setId(rs.getString("id"));
                theme.setUserid(rs.getString("userid"));
                theme.setGroupid(rs.getString("groupid"));
                theme.setSubject(rs.getString("subject"));
                theme.setContent(rs.getString("content"));
                theme.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                theme.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    themeCollection.setNewestTimestamp(theme.getLastModified());
                    first = false;
                }
                themeCollection.setOldestTimestamp(theme.getLastModified());
                themeCollection.getThemes().add(theme);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return themeCollection;
    }

    @Override
    public Theme updateTheme(String id, String subject, String content) throws SQLException{
        Theme theme = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ThemeDAOQuery.UPDATE_THEME);
            stmt.setString(1, subject);
            stmt.setString(2, content);
            stmt.setString(3, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                theme = getThemeById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return theme;
    }

    @Override
    public boolean deleteTheme(String id) throws SQLException{
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ThemeDAOQuery.DELETE_THEME);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }
}

package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Answer;
import edu.upc.eetac.dsa.grouptalk.entity.AnswerCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by juan on 26/10/15.
 */
public class AnswerDAOImpl implements AnswerDAO{
    @Override
    public Answer createAnswer(String userid, String themeid, String content) throws SQLException{
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(AnswerDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(AnswerDAOQuery.CREATE_ANSWER);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(2, themeid);
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
        return getAnswerById(id);
    }

    @Override
    public Answer getAnswerById(String id) throws SQLException{
        Answer answer = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(AnswerDAOQuery.GET_ANSWER_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                answer = new Answer();
                answer.setId(rs.getString("id"));
                answer.setUserid(rs.getString("userid"));
                answer.setThemeid(rs.getString("themeid"));
                answer.setContent(rs.getString("content"));
                answer.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                answer.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return answer;
    }

    @Override
    public AnswerCollection getAnswersByThemeId(String themeid) throws SQLException{
        AnswerCollection answerCollection = new AnswerCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(AnswerDAOQuery.GET_ANSWERS_BY_THEMEID);
            stmt.setString(1, themeid);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Answer answer = new Answer();
                answer.setId(rs.getString("id"));
                answer.setUserid(rs.getString("userid"));
                answer.setThemeid(rs.getString("themeid"));
                answer.setContent(rs.getString("content"));
                answer.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                answer.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    answerCollection.setNewestTimestamp(answer.getLastModified());
                    first = false;
                }
                answerCollection.setOldestTimestamp(answer.getLastModified());
                answerCollection.getAnswers().add(answer);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return answerCollection;
    }

    @Override
    public Answer updateAnswer(String id, String content) throws SQLException{
        Answer answer = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(AnswerDAOQuery.UPDATE_ANSWER);
            stmt.setString(1, content);
            stmt.setString(2, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                answer = getAnswerById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return answer;
    }

    @Override
    public boolean deleteAnswer(String id) throws SQLException{
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(AnswerDAOQuery.DELETE_ANSWER);
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

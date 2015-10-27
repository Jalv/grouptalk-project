package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.User;

import java.sql.SQLException;

/**
 * Created by juan on 26/10/15.
 */
public interface UserDAO {
    public User createUser(String loginid, String password) throws SQLException, UserAlreadyExistsException;

    public User getUserById(String id) throws SQLException;

    public User getUserByLoginid(String loginid) throws SQLException;

    public boolean subscribeUserToGroup(String userid,String groupid) throws SQLException, UserAlreadySubscribedException;

    public boolean deleteUser(String id) throws SQLException;

    public boolean checkPassword(String id, String password) throws SQLException;

    public boolean checkUser(String userid, String id) throws SQLException;
}

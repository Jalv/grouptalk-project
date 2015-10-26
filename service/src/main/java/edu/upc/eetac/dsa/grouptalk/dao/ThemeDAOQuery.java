package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by juan on 26/10/15.
 */
public interface ThemeDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_THEME = "insert into themes (id, userid, groupid, subject, content) values (UNHEX(?), unhex(?), unhex(?), ?, ?)";
    public final static String GET_THEME_BY_ID = "select hex(t.id) as id, hex(t.userid) as userid, hex(t.groupid) as groupid, t.content, t.subject, t.creation_timestamp, t.last_modified from themes t , users u where t.id=unhex(?) and u.id=t.userid";
    public final static String GET_THEMES_BY_GROUPID = "select hex(id) as id, hex(userid) as userid, hex(groupid) as groupid, subject, content, creation_timestamp, last_modified from themes, groups g where g.id=unhex(?) and t.groupid=g.id";
    public final static String UPDATE_THEME = "update themes set subject=?, content=? where id=unhex(?) ";
    public final static String DELETE_THEME = "delete from themes where id=unhex(?)";

}

package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Theme;
import edu.upc.eetac.dsa.grouptalk.entity.ThemeCollection;

import java.sql.SQLException;

/**
 * Created by juan on 26/10/15.
 */
public interface ThemeDAO {
    public Theme createTheme(String userid, String groupid, String subject, String content) throws SQLException;
    public Theme getThemeById(String id) throws SQLException;
    public ThemeCollection getThemesByGroupId(String groupid) throws SQLException;
    public Theme updateTheme(String id, String subject, String content) throws SQLException;
    public boolean deleteTheme(String id) throws SQLException;

}
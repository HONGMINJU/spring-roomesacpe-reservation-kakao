package nextstep.dao.directConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.dao.DatabaseUtil;
import nextstep.dao.ThemeDao;
import nextstep.dto.ThemeRequestDto;
import nextstep.entity.Theme;

@RequiredArgsConstructor
public class DirectConnectionThemeDao implements ThemeDao {

    private static final String FIND_BY_ID_SQL = "SELECT ID, NAME, DESC, PRICE FROM THEME WHERE ID = ?";
    private static final String FIND_SQL = "SELECT ID, NAME, DESC, PRICE FROM THEME";
    private static final String DELETE_BY_THEME_ID_SQL = "DELETE FROM THEME WHERE ID = ?";
    private static final String INSERT_SQL = "INSERT INTO `THEME`(`name`, `desc`, `price`) VALUES (?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE THEME SET NAME=?, DESC=?, PRICE=? where ID=?";

    @Override
    public Theme findById(Long id) throws SQLException {
        Connection con = DatabaseUtil.getConnection();
        PreparedStatement psmt = null;
        ResultSet resultSet = null;
        try {
            psmt = con.prepareStatement(FIND_BY_ID_SQL);
            psmt.setLong(1, id);
            resultSet = psmt.executeQuery();
            List<Theme> themes = getTheme(id, resultSet);
            return (themes.size() > 0) ? themes.get(0) : null;
        } catch (SQLException sqlException) {
            return null;
        } finally {
            DatabaseUtil.close(con, psmt, resultSet);
        }
    }

    @Override
    public int insert(Theme theme) throws SQLException {
        Connection con = DatabaseUtil.getConnection();
        PreparedStatement psmt = null;
        ResultSet resultSet = null;
        Long themeId = 0L;
        int insertCount = 0;
        try {
            int parameterIndex = 1;
            psmt = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            psmt.setString(parameterIndex++, theme.getName());
            psmt.setString(parameterIndex++, theme.getDesc());
            psmt.setInt(parameterIndex++, theme.getPrice());

            insertCount = psmt.executeUpdate();
            resultSet = psmt.getGeneratedKeys();

            while (resultSet.next()) {
                themeId = resultSet.getLong(1);
            }
            theme.setId(themeId);
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            DatabaseUtil.close(con, psmt, resultSet);
        }
        return insertCount;
    }

    @Override
    public int deleteTheme(Long id) throws SQLException {
        Connection con = null;
        PreparedStatement psmt = null;
        try {
            con = DatabaseUtil.getConnection();
            psmt = con.prepareStatement(DELETE_BY_THEME_ID_SQL);
            psmt.setLong(1, id);
            return psmt.executeUpdate();
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            DatabaseUtil.close(con, psmt);
        }
    }

    @Override
    public List<Theme> findAll() throws SQLException {
        Connection con = DatabaseUtil.getConnection();
        PreparedStatement psmt = null;
        ResultSet resultSet = null;
        try {
            psmt = con.prepareStatement(FIND_SQL);
            resultSet = psmt.executeQuery();
            List<Theme> themes = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                String name = resultSet.getString("NAME");
                String desc = resultSet.getString("DESC");
                Integer price = resultSet.getInt("PRICE");
                themes.add((new Theme(id, name, desc, price)));
            }
            return themes;
        } catch (SQLException sqlException) {
            return null;
        } finally {
            DatabaseUtil.close(con, psmt, resultSet);
        }
    }

    @Override
    public int update(ThemeRequestDto themeRequestDto, Long themeId) throws SQLException {
        Connection con = null;
        PreparedStatement psmt = null;
        try {
            con = DatabaseUtil.getConnection();
            psmt = con.prepareStatement(UPDATE_SQL);
            psmt.setString(1, themeRequestDto.getName());
            psmt.setString(2, themeRequestDto.getDesc());
            psmt.setLong(3, themeRequestDto.getPrice());
            psmt.setLong(4, themeId);
            return psmt.executeUpdate();
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            DatabaseUtil.close(con, psmt);
        }
    }

    private List<Theme> getTheme(Long id, ResultSet resultSet) throws SQLException {
        List<Theme> themes = new ArrayList<>();
        while (resultSet.next()) {
            String name = resultSet.getString("NAME");
            String desc = resultSet.getString("DESC");
            Integer price = resultSet.getInt("PRICE");
            themes.add((new Theme(id, name, desc, price)));
        }
        return themes;
    }
}

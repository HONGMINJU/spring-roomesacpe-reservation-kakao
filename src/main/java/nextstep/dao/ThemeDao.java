package nextstep.dao;

import java.sql.SQLException;
import java.util.List;
import nextstep.dto.ThemeRequestDto;
import nextstep.entity.Theme;

public interface ThemeDao {

    Theme findById(Long id) throws SQLException;

    int insert(Theme theme) throws SQLException;

    int deleteTheme(Long id) throws SQLException;

    List<Theme> findAll() throws SQLException;

    int update(ThemeRequestDto themeRequestDto, Long themeId) throws SQLException;
}

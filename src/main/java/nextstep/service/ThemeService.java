package nextstep.service;

import java.sql.SQLException;
import java.util.List;
import nextstep.dto.ThemeRequestDto;
import nextstep.dto.ThemeResponseDto;

public interface ThemeService {

    Long create(final ThemeRequestDto themeRequestDto) throws SQLException;

    List<ThemeResponseDto> getThemeList() throws SQLException;

    int delete(final Long themeId) throws SQLException;

    int update(final ThemeRequestDto themeRequestDto, Long themeId) throws SQLException;
}

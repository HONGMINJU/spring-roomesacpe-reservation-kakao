package nextstep.service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nextstep.dao.ThemeDao;
import nextstep.dto.ThemeRequestDto;
import nextstep.dto.ThemeResponseDto;
import nextstep.entity.Theme;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThemeServiceImpl implements ThemeService {

    @Qualifier("jDBCTemplateReservationDao")
    private final ThemeDao themeDao;

    @Override
    public Long create(ThemeRequestDto themeRequestDto) throws SQLException {
        int id = themeDao.insert(new Theme(themeRequestDto.getName(), themeRequestDto.getDesc(), themeRequestDto.getPrice()));
        return Long.valueOf(id);
    }

    @Override
    public List<ThemeResponseDto> getThemeList() throws SQLException {
        return themeDao.findAll().stream()
            .map(theme -> theme.toDto())
            .collect(Collectors.toList());
    }

    @Override
    public int delete(final Long themeId) throws SQLException {
        return themeDao.deleteTheme(themeId);
    }

    @Override
    public int update(ThemeRequestDto themeRequestDto, Long themeId) throws SQLException {
        return themeDao.update(themeRequestDto, themeId);
    }

}

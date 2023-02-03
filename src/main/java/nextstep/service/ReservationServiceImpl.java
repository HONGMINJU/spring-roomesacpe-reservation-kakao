package nextstep.service;

import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nextstep.dao.ReservationDao;
import nextstep.dao.ThemeDao;
import nextstep.dto.ReservationDetail;
import nextstep.dto.ReservationDto;
import nextstep.entity.Reservation;
import nextstep.entity.Theme;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDao reservationDao;
    private final ThemeDao themeDao;

    private static final Long DEFAULT_THEME_ID = 1L;

    @Override
    public Long reserve(ReservationDto reservationDto) throws SQLException {
        reservationDto.setThemeId(DEFAULT_THEME_ID);
        Reservation reservation = ReservationDto.from(reservationDto);
        reservationDao.insert(reservation);
        return reservation.getId();
    }

    @Override
    public void cancelById(Long id) throws SQLException {
        int deleteCount = reservationDao.deleteReservation(id);
        if (deleteCount == 0) {
            throw new SQLException();
        }
    }

    @Override
    public ReservationDetail findById(Long id) throws SQLException {
        Reservation reservation = reservationDao.findById(id);
        if (reservation == null) {
            return null;
        }
        Theme theme = themeDao.findById(reservation.getThemeId());
        if (theme == null) {
            return null;
        }
        return new ReservationDetail(reservation, theme);
    }

    @Override
    public boolean isExistByThemeId(Long themeId) throws SQLException {
        return reservationDao.existByThemeId(themeId);
    }
}

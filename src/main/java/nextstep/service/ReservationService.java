package nextstep.service;

import java.sql.SQLException;
import nextstep.dto.ReservationDetail;
import nextstep.dto.ReservationDto;

public interface ReservationService {

    Long reserve(ReservationDto reservationDto) throws SQLException;

    void cancelById(Long id) throws SQLException;

    ReservationDetail findById(Long id) throws SQLException;

    boolean isExistByThemeId(Long ThemeId) throws SQLException;
}

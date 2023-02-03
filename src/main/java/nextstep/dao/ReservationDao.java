package nextstep.dao;

import nextstep.entity.Reservation;

import java.sql.SQLException;


public interface ReservationDao {

    int insert(Reservation reservation) throws SQLException;

    int deleteReservation(Long id) throws SQLException;

    Reservation findById(Long id) throws SQLException;

    boolean existByThemeId(Long themeId) throws SQLException;
}

package nextstep.dao.directConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.dao.DatabaseUtil;
import nextstep.dao.ReservationDao;
import nextstep.entity.Reservation;

@RequiredArgsConstructor
public class DirectConnectionReservationDao implements ReservationDao {

    private static final String INSERT_SQL = "INSERT INTO RESERVATION(date, time, name, theme_id) VALUES (?, ?, ?, ?)";
    private static final String DELETE_BY_RESERVATION_ID_SQL = "DELETE FROM RESERVATION WHERE ID = ?";
    private static final String SELECT_BY_RESERVATION_ID_SQL = "SELECT * FROM RESERVATION WHERE ID = ?";

    private static final String SELECT_BY_THEME_ID_SQL = "SELECT * FROM RESERVATION WHERE THEME_ID = ?";


    @Override
    public int insert(Reservation reservation) throws SQLException {
        System.out.println("DirectConnectionThemeReservationDao.insert");
        Connection con = DatabaseUtil.getConnection();
        System.out.println(con);
        PreparedStatement psmt = null;
        ResultSet resultSet = null;
        Long reservationId = 0L;
        int insertCount = 0;
        try {
            int parameterIndex = 1;
            psmt = con.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
            psmt.setDate(parameterIndex++, java.sql.Date.valueOf(reservation.getDate()));
            psmt.setTime(parameterIndex++, java.sql.Time.valueOf(reservation.getTime()));
            psmt.setString(parameterIndex++, reservation.getName());
            psmt.setLong(parameterIndex++, reservation.getThemeId());
            insertCount = psmt.executeUpdate();
            resultSet = psmt.getGeneratedKeys();
            while (resultSet.next()) {
                reservationId = resultSet.getLong(1);
            }
            reservation.setId(reservationId);
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            DatabaseUtil.close(con, psmt, resultSet);
        }
        return insertCount;
    }

    @Override
    public int deleteReservation(Long id) throws SQLException {
        Connection con = DatabaseUtil.getConnection();
        PreparedStatement psmt = null;
        int deleteCount = 0;
        try {
            psmt = con.prepareStatement(DELETE_BY_RESERVATION_ID_SQL);
            psmt.setLong(1, id);
            deleteCount = psmt.executeUpdate();
        } catch (SQLException sqlException) {
            throw sqlException;
        } finally {
            DatabaseUtil.close(con, psmt);
        }
        return deleteCount;
    }

    @Override
    public Reservation findById(Long id) throws SQLException {
        Connection con = DatabaseUtil.getConnection();
        PreparedStatement psmt = null;
        ResultSet resultSet = null;
        try {
            psmt = con.prepareStatement(SELECT_BY_RESERVATION_ID_SQL);
            psmt.setLong(1, id);
            resultSet = psmt.executeQuery();
            List<Reservation> reservations = getReservation(id, resultSet);
            return (reservations.size() > 0) ? reservations.get(0) : null;
        } catch (SQLException sqlException) {
            return null;
        } finally {
            DatabaseUtil.close(con, psmt, resultSet);
        }
    }

    @Override
    public boolean existByThemeId(Long themeId) throws SQLException {
        Connection con = DatabaseUtil.getConnection();
        PreparedStatement psmt = null;
        ResultSet resultSet = null;
        try {
            psmt = con.prepareStatement(SELECT_BY_THEME_ID_SQL);
            psmt.setLong(1, themeId);
            resultSet = psmt.executeQuery();
            return resultSet.next();
        } catch (SQLException sqlException) {
            return false;
        } finally {
            DatabaseUtil.close(con, psmt, resultSet);
        }
    }

    private List<Reservation> getReservation(Long id, ResultSet resultSet) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        while (resultSet.next()) {
            LocalDate date = resultSet.getDate("DATE").toLocalDate();
            LocalTime time = resultSet.getTime("TIME").toLocalTime();
            String name = resultSet.getString("NAME");
            Long themeId = resultSet.getLong("THEME_ID");
            reservations.add((new Reservation(id, date, time, name, themeId)));
        }
        return reservations;
    }
}
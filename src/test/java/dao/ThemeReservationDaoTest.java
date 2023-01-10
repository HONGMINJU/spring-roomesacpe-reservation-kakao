package dao;

import nextstep.RoomEscapeWebApplication;
import nextstep.entity.Reservation;
import nextstep.dao.ThemeReservationDao;
import nextstep.dto.ReservationDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = RoomEscapeWebApplication.class)
public class ThemeReservationDaoTest {

    @Autowired
    private ThemeReservationDao themeReservationDao;

    @Test
    @DisplayName("방탈출 예약하기")
    void test1(){
        Reservation reservation = makeRandomReservation("2022-08-23", "13:01", 1L);

        Long reservationId = themeReservationDao.createReservation(reservation);
        assertThat(reservationId).isEqualTo(1);
    }

    @Test
    @DisplayName("이미 예약된 방탈출 예약을 취소한다.")
    void test2(){
        Reservation reservation = makeRandomReservation("2022-08-23", "13:02", 1L);
        Long reservationId = themeReservationDao.createReservation(reservation);

        themeReservationDao.deleteReservation(reservationId);
        assertThat(themeReservationDao.findById(reservationId)).isNull();
    }

    @Test
    @DisplayName("존재하지 않는 예약을 취소할 수 없다.")
    void test3(){
        assertThatThrownBy(() -> themeReservationDao.deleteReservation(1000L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("예약된 방을 조회한다.")
    void test4() {
        Reservation randomReservation = makeRandomReservation("2022-08-23", "13:02", 1L);
        long reservationId = themeReservationDao.createReservation(randomReservation);
        Reservation findReservation = themeReservationDao.findById(reservationId);

        assertThat(findReservation.getName()).isEqualTo(randomReservation.getName());
    }

    @Test
    @DisplayName("예약되지 않은 방을 조회한다.")
    void test5() {
        Reservation findReservation = themeReservationDao.findById(100L);

        assertThat(findReservation).isNull();
    }

    @Test
    @DisplayName("존재하지 않는 테마는 생성할 수 없다.")
    void test6(){
        Reservation reservation = makeRandomReservation("2022-08-23", "13:06", 100L);

        assertThatThrownBy(() -> themeReservationDao.createReservation(reservation))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("날짜와 시간이 같은 예약은 할 수 없다.")
    void test7() {
        Reservation reservation1 = makeRandomReservation("2022-08-23", "13:07", 1L);
        Reservation reservation2 = makeRandomReservation("2022-08-23", "13:07", 1L);

        themeReservationDao.createReservation(reservation1);
        Assertions.assertThatThrownBy(() -> themeReservationDao.createReservation(reservation2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    Reservation makeRandomReservation(String date, String time, Long themeId){
        List<String> names = List.of("omin", "ethan", "java");

        int index = ThreadLocalRandom.current().nextInt(3);

        return ReservationDto.from(new ReservationDto(date, time, names.get(index), themeId));
    }
}
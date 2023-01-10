package nextstep.domain;

import nextstep.entity.Reservation;
import nextstep.dto.ReservationDto;

import java.util.LinkedList;
import java.util.List;

public class Reservations {

    private final List<Reservation> reservations;
    private Long lastId;

    private static final Reservations instance = new Reservations();

    private Reservations(){
        this.lastId = 1L;
        this.reservations = new LinkedList<>();
    }

    public static Reservations getInstance(){
        return instance;
    }

    public Long add(ReservationDto reservationDto) {
        Reservation reservation = Reservation.from(reservationDto);
        reservation.setId(getAutoIncrementId());

        reservations.add(reservation);
        return reservation.getId();
    }

    public Reservation findById(Long id) {
        return reservations.stream()
                .filter(reservation -> id.equals(reservation.getId()))
                .findFirst()
                .orElse(null);
    }

    public void deleteById(Long id){
        Reservation reservation = findById(id);

        if(reservation == null){
            throw new IllegalArgumentException();
        }
        reservations.remove(reservation);
    }

    private Long getAutoIncrementId(){
        return lastId++;
    }

    public List<Reservation> findAll() {
        return reservations;
    }
}
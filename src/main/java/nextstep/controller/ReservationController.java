package nextstep.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nextstep.dto.ReservationDetail;
import nextstep.dto.ReservationDto;
import nextstep.service.ReservationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.net.URI;
import java.sql.SQLException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/{id}")
    ResponseEntity<ReservationDetail> getReservations(@NonNull @PathVariable("id") Long id) throws SQLException {
        ReservationDetail reservationDetail = reservationService.findById(id);
        if (reservationDetail == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(reservationDetail);
    }

    @PostMapping
    ResponseEntity<ReservationDetail> createReservation(@Valid @RequestBody ReservationDto reservationDto) throws SQLException {
        Long reservationId = reservationService.reserve(reservationDto);
        return ResponseEntity.created(URI.create("/reservations/" + reservationId)).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> cancelReservation(@NonNull @PathVariable("id") Long id) throws SQLException {
        try {
            reservationService.cancelById(id);
            return ResponseEntity.noContent().build();
        } catch (SQLException sqlException) {
            return ResponseEntity.notFound().build();
        }
    }
}

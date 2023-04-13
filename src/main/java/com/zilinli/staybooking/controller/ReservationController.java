//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 04/23
// * Definition: Implementation of ReservationController class.
//**********************************************************************************************************************

package com.zilinli.staybooking.controller;
//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.zilinli.staybooking.exception.InvalidReservationDateException;
import com.zilinli.staybooking.model.Reservation;
import com.zilinli.staybooking.model.User;

// Framework includes
import com.zilinli.staybooking.service.ReservationService;
import org.springframework.web.bind.annotation.*;

// System includes
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@RestController
public class ReservationController {

//**********************************************************************************************************************
// * Class constructors
//**********************************************************************************************************************

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************
    @GetMapping(value = "/reservations")
    public List<Reservation> listReservations(Principal principal) {
        return reservationService.listByGuest(principal.getName());
    }

    @PostMapping("/reservations")
    public void addReservation(@RequestBody Reservation reservation, Principal principal) {
        LocalDate checkinDate = reservation.getCheckinDate();
        LocalDate checkoutDate = reservation.getCheckoutDate();
        if (checkinDate.equals(checkoutDate) || checkinDate.isAfter(checkoutDate) || checkinDate.isBefore(LocalDate.now())) {
            throw new InvalidReservationDateException("Invalid date for reservation");
        }
        reservation.setGuest(new User.Builder().setUsername(principal.getName()).build());
        reservationService.add(reservation);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public void deleteReservation(@PathVariable Long reservationId, Principal principal) {
        reservationService.delete(reservationId, principal.getName());
    }

//**********************************************************************************************************************
// * Protected methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Private methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************

    private ReservationService reservationService;

}

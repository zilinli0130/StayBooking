//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 03/23
// * Definition: Implementation of SearchService class.
//**********************************************************************************************************************

package com.zilinli.staybooking.service;
//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.zilinli.staybooking.model.Stay;

// Framework includes
import com.zilinli.staybooking.repository.LocationRepository;
import com.zilinli.staybooking.repository.StayRepository;
import com.zilinli.staybooking.repository.StayReservationDateRepository;
import org.springframework.stereotype.Service;

// System includes
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************

@Service
public class SearchService {

//**********************************************************************************************************************
// * Class constructors
//**********************************************************************************************************************
    public SearchService(StayRepository stayRepository, StayReservationDateRepository stayReservationDateRepository, LocationRepository locationRepository) {
        this.stayRepository = stayRepository;
        this.stayReservationDateRepository = stayReservationDateRepository;
        this.locationRepository = locationRepository;
    }
//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************
    public List<Stay> search(int guestNumber, LocalDate checkinDate, LocalDate checkoutDate, double lat, double lon, String distance) {

        // Search stay by distance (ES)
        List<Long> stayIds = locationRepository.searchByDistance(lat, lon, distance);
        if (stayIds == null || stayIds.isEmpty()) {
            return new ArrayList<>();
        }

        // Search reservation in between the expected check-in and check-out date (MySQL)
        Set<Long> reservedStayIds = stayReservationDateRepository.findByIdInAndDateBetween(stayIds, checkinDate, checkoutDate.minusDays(1));

        // Filter out the occupied stays in between expected check-in and check-out date
        List<Long> filteredStayIds = new ArrayList<>();
        for (Long stayId : stayIds) {
            if (!reservedStayIds.contains(stayId)) {
                filteredStayIds.add(stayId);
            }
        }

        // Filter out the stay which has at least number of guest availability
        return stayRepository.findByIdInAndGuestNumberGreaterThanEqual(filteredStayIds, guestNumber);
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

    private final StayRepository stayRepository;
    private final StayReservationDateRepository stayReservationDateRepository;
    private final LocationRepository locationRepository;
}

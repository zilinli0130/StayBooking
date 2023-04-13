//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 03/23
// * Definition: Implementation of StayService class.
//**********************************************************************************************************************

package com.zilinli.staybooking.service;
//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.zilinli.staybooking.exception.GeoCodingException;
import com.zilinli.staybooking.exception.StayDeleteException;
import com.zilinli.staybooking.exception.StayNotExistException;
import com.zilinli.staybooking.model.*;
import com.zilinli.staybooking.repository.LocationRepository;
import com.zilinli.staybooking.repository.ReservationRepository;
import com.zilinli.staybooking.repository.StayRepository;

// Framework includes
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

// System includes
import java.awt.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Service
public class StayService {

//**********************************************************************************************************************
// * Class constructors
//**********************************************************************************************************************
    public StayService(StayRepository stayRepository, ImageStorageService imageStorageService, LocationRepository locationRepository, GeoCodingService geoCodingService, ReservationRepository reservationRepository) {
        this.stayRepository = stayRepository;
        this.imageStorageService = imageStorageService;
        this.locationRepository = locationRepository;
        this.geoCodingService = geoCodingService;
        this.reservationRepository = reservationRepository;
    }
//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************
    public List<Stay> listByUser(String username) {
        return stayRepository.findByHost(new User.Builder().setUsername(username).build());
    }

    public Stay findByIdAndHost(Long stayId, String username) throws StayNotExistException {
        Stay stay = stayRepository.findByIdAndHost(stayId, new User.Builder().setUsername(username).build());
        if (stay == null) {
            throw new StayNotExistException("Stay does not exist");
        }
        return stay;
    }

    @Transactional
    public void addStay(Stay stay, MultipartFile[] images) {

        // Arrays.stream(T[] array) -> Stream<T> array
        List<String> mediaLinks = Arrays.stream(images).parallel().
                                                        map(image -> imageStorageService.save(image))
                                                        .collect(Collectors.toList());
        List<StayImage> stayImages = new ArrayList<>();
        for (String mediaLink : mediaLinks) {
            stayImages.add(new StayImage(mediaLink, stay));
        }
        stay.setImages(stayImages);
        stayRepository.save(stay);

        // Save location of stay to ES
        Location location = geoCodingService.getLatLng(stay.getId(), stay.getAddress());
        locationRepository.save(location);
    }

    @Transactional
    public void delete(Long stayId, String username) {
        Stay stay = stayRepository.findByIdAndHost(stayId, new User.Builder().setUsername(username).build());
        if (stay == null) {
            throw new StayNotExistException("Stay does not exist");
        }

        List<Reservation> reservations = reservationRepository.findByStayAndCheckoutDateAfter(stay, LocalDate.now());
        if (reservations != null && reservations.size() > 0) {
            throw new StayDeleteException("Cannot delete stay with active reservation");
        }

        stayRepository.deleteById(stayId);

        // Delete location of stay to ES
        Location location = geoCodingService.getLatLng(stay.getId(), stay.getAddress());
        locationRepository.delete(location);
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
    private final ImageStorageService imageStorageService;

    private final LocationRepository locationRepository;

    private final ReservationRepository reservationRepository;

    private final GeoCodingService geoCodingService;

}

//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 03/23
// * Definition: Implementation of GeoCodingService class.
//**********************************************************************************************************************

package com.zilinli.staybooking.service;
//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.zilinli.staybooking.exception.GeoCodingException;
import com.zilinli.staybooking.exception.InvalidStayAddressException;
import com.zilinli.staybooking.model.Location;

// Framework includes
import com.google.maps.errors.ApiException;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

// System includes
import java.io.IOException;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Service
public class GeoCodingService {

//**********************************************************************************************************************
// * Class constructors
//**********************************************************************************************************************
    public GeoCodingService(GeoApiContext context) {
        this.context = context;
    }
//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************

    public Location getLatLng(Long id, String address) {
        try {
            GeocodingResult result = GeocodingApi.geocode(context, address).await()[0];
            if (result.partialMatch) {
                throw new InvalidStayAddressException("Failed to find stay address");
            }
            return new Location(id, new GeoPoint(result.geometry.location.lat, result.geometry.location.lng));
        } catch (IOException | ApiException | InterruptedException e) {
            e.printStackTrace();
            throw new GeoCodingException("Failed to encode stay address");
        }
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

    private final GeoApiContext context;
}

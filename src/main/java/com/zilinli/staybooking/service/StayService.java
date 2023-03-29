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
import com.zilinli.staybooking.exception.StayNotExistException;
import com.zilinli.staybooking.model.Stay;
import com.zilinli.staybooking.model.User;
import com.zilinli.staybooking.repository.StayRepository;

// Framework includes
import org.springframework.stereotype.Service;

import java.util.List;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Service
public class StayService {

//**********************************************************************************************************************
// * Class constructors
//**********************************************************************************************************************
    public StayService(StayRepository stayRepository) {
        this.stayRepository = stayRepository;
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

    public void addStay(Stay stay) {
        stayRepository.save(stay);
    }

    public void delete(Long stayId, String username) {
        Stay stay = stayRepository.findByIdAndHost(stayId, new User.Builder().setUsername(username).build());
        if (stay == null) {
            throw new StayNotExistException("Stay does not exist");
        }
        stayRepository.deleteById(stayId);
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
}

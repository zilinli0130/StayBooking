//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 03/23
// * Definition: Implementation of RegisterController class.
//**********************************************************************************************************************

package com.zilinli.staybooking.controller;
//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.zilinli.staybooking.model.User;
import com.zilinli.staybooking.model.UserRole;
import com.zilinli.staybooking.service.RegisterService;

// Framework includes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@RestController
public class RegisterController {

//**********************************************************************************************************************
// * Class constructors
//**********************************************************************************************************************
    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }
//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************

    @PostMapping("/register/guest")
    public void addGuest(@RequestBody User user) {
        registerService.add(user, UserRole.ROLE_GUEST);
    }

    @PostMapping("/register/host")
    public void addHost(@RequestBody User user) {
        registerService.add(user, UserRole.ROLE_HOST);
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

    private RegisterService registerService;
}

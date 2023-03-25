//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 03/23
// * Definition: Implementation of AuthenticationService class.
//**********************************************************************************************************************

package com.zilinli.staybooking.service;
//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.zilinli.staybooking.exception.UserNotExistException;
import com.zilinli.staybooking.model.Token;
import com.zilinli.staybooking.model.User;
import com.zilinli.staybooking.model.UserRole;
import com.zilinli.staybooking.utill.JwtUtil;

// Framework includes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Service
public class AuthenticationService {

//**********************************************************************************************************************
// * Class constructors
//**********************************************************************************************************************

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************

    public Token authenticate(User user, UserRole role) throws UserNotExistException {

        Authentication auth = null;
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (AuthenticationException ex) {
            throw new UserNotExistException("User does not exist");
        }

        if (auth == null || !auth.isAuthenticated() || !auth.getAuthorities().contains(new SimpleGrantedAuthority(role.name()))) {
            throw new UserNotExistException("User does not exist");
        }
        return new Token(jwtUtil.generateToken(user.getUsername()));
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

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
}

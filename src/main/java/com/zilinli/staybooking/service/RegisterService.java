//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 03/23
// * Definition: Implementation of RegisterService class.
//**********************************************************************************************************************

package com.zilinli.staybooking.service;
//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.zilinli.staybooking.exception.UserAlreadyExistException;
import com.zilinli.staybooking.model.Authority;
import com.zilinli.staybooking.model.User;
import com.zilinli.staybooking.model.UserRole;
import com.zilinli.staybooking.repository.AuthorityRepository;
import com.zilinli.staybooking.repository.UserRepository;

// Framework includes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@Service
public class RegisterService {

//**********************************************************************************************************************
// * Class constructors
//**********************************************************************************************************************

    @Autowired
    public RegisterService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }
//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(User user, UserRole role) throws UserAlreadyExistException {
        if (userRepository.existsById(user.getUsername())) {
            throw new UserAlreadyExistException("User already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        authorityRepository.save(new Authority(user.getUsername(), role.name()));
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

    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;

    private PasswordEncoder passwordEncoder;

}

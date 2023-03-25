//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 03/23
// * Definition: Implementation of SecurityConfig class.
//**********************************************************************************************************************

package com.zilinli.staybooking.config;
//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Framework includes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// System includes
import javax.sql.DataSource;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//**********************************************************************************************************************
// * Class constructors
//**********************************************************************************************************************

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.authorizeHttpRequests()
               .antMatchers(HttpMethod.POST, "/register/*").permitAll()
               .antMatchers(HttpMethod.POST, "/authenticate/*").permitAll()
               .anyRequest().authenticated()
               .and()
               .csrf()
               .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
            .jdbcAuthentication()
            .dataSource(dataSource)
            .passwordEncoder(passwordEncoder())
            .usersByUsernameQuery("SELECT username, password, enabled FROM user WHERE username = ?")
            .authoritiesByUsernameQuery("SELECT username, authority FROM authority WHERE username = ?");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Protected methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Private methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************

    @Autowired
    DataSource dataSource;

}

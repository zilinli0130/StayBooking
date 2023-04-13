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
import com.zilinli.staybooking.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    public SecurityConfig(DataSource dataSource, JwtFilter jwtFilter) {
        this.dataSource = dataSource;
        this.jwtFilter = jwtFilter;
    }

//**********************************************************************************************************************
// * Public methods
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
               .antMatchers("/stays").hasAuthority("ROLE_HOST")
               .antMatchers("/stays/*").hasAuthority("ROLE_HOST")
               .antMatchers("/search").hasAuthority("ROLE_GUEST")
               .antMatchers("/reservations").hasAuthority("ROLE_GUEST")
               .antMatchers("/reservations/*").hasAuthority("ROLE_GUEST")
               .anyRequest().authenticated()
               .and()
               .csrf()
               .disable();

        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

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
// * Protected methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Private methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************

    DataSource dataSource;
    private final JwtFilter jwtFilter;

}

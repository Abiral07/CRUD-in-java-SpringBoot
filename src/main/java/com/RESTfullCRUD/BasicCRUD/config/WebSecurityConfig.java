package com.RESTfullCRUD.BasicCRUD.config;

import com.RESTfullCRUD.BasicCRUD.constant.PathConstant;
import com.RESTfullCRUD.BasicCRUD.utils.JwtAuthenticationEntryPoint;
import com.RESTfullCRUD.BasicCRUD.utils.JwtRequestFilter;
import com.RESTfullCRUD.BasicCRUD.utils.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//allows customization to both WebSecurity and HttpSecurity
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    UserDetailsService jwtUserDetailsService;
    private static final String[] WHITE_LIST_URLS = {
            "/",
            PathConstant.REGISTRATION,
            PathConstant.LOGIN,
            PathConstant.GET_PRODUCT,
            PathConstant.GET_PRODUCT_BY_NAME,
            PathConstant.GET_USER_ID,
    };
    private static final String[] ADMIN_URLS = {
            "/all",
            PathConstant.REMOVE_PRODUCT,
            PathConstant.GET_USER_BY_COUNTRY,
            PathConstant.GET_USER,
            PathConstant.GET_USERDTO,
            PathConstant.UPDATE_USER+"/**",
//            PathConstant.REMOVE_USER,
            PathConstant.GET_ROLE,
            PathConstant.ADD_ROLE,
            PathConstant.UPDATE_ROLE,
            PathConstant.GET_SESSION_DETAILS
    };
    private static final String[] VENDOR_URLS = {
            PathConstant.GET_PRODUCT_BY_ID,
            PathConstant.ADD_PRODUCT,
            PathConstant.REMOVE_PRODUCT,
            PathConstant.UPDATE_PRODUCT
    };
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {return http.build(); }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(WHITE_LIST_URLS).permitAll()
                .antMatchers(VENDOR_URLS).hasRole("VENDOR")
                .antMatchers(ADMIN_URLS).hasRole("ADMIN")
                .antMatchers("/**").authenticated()
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic();
        // Add a filter to validate the tokens with every request
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load user for matching credentials
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
//        auth.authenticationProvider(authenticationProvider());
    }
    //    @Bean
//    public DaoAuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(customUserDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        return authenticationProvider;
//    }
}


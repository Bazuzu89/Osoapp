package controller.security;

//import controller.security.filter.AuthorizationFilter;
import controller.security.filter.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import service.UserService;

import static controller.security.ApplicationUserRole.ADMIN;


@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userDAOService;
    private final BCryptPasswordEncoder bCryptoPasswordEncoder;

    public ApplicationSecurityConfig(UserService userDAOService, BCryptPasswordEncoder bCryptoPasswordEncoder) {
        this.userDAOService = userDAOService;
        this.bCryptoPasswordEncoder = bCryptoPasswordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/users/**").hasRole(ADMIN.name())
                .anyRequest()
                .authenticated();

    }

//    public void configure(WebSecurity web) {
//        web.ignoring()
//                .antMatchers("/auth/**"); //auth endpoints
//    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDAOService).passwordEncoder(bCryptoPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }
}

package by.arseniyty.lab04spring;

import by.arseniyty.lab04spring.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UsersService usersService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // not safe, replace with token, otherwise post is unavailable
            .cors().disable()
            .authorizeRequests()
//                .antMatchers("/questions/secret").hasRole("ADMIN")
                .antMatchers("/registration").not().fullyAuthenticated()  // only for users, not auth yet
                .antMatchers("/",
                        "/findQuestion",
                        "/login",  // very important!!!
                        "/logout",
                        "/questions/*",
                        "/styles/**",
                        "/js/**"
                ).permitAll()  // without auth
                .anyRequest().authenticated()  // all other pages only with auth
                .and()
            .formLogin()
                .loginPage("/login")
//                .successForwardUrl("/afterLogin")
//                .failureUrl("/login")
                .permitAll()
                .and()
            .logout()
                .logoutSuccessUrl("/")
                .permitAll();
    }
}

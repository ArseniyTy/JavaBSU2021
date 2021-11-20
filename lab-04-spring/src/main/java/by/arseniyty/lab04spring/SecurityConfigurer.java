package by.arseniyty.lab04spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();  // not safe, replace with token, otherwise post is unavailable

        http.authorizeRequests()
//                .antMatchers("/questions/secret").hasRole("ADMIN")
                .and()
            .formLogin()
                .permitAll()
                .and()
            .logout()
                .permitAll()
            .disable().cors();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails user = User
            .withDefaultPasswordEncoder()
            .username("u")
            .roles("ADMIN")
            .password("p")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
}

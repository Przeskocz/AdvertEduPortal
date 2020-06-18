package com.przeskocz.AdvertEduPortal.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AppAccessDeniedHandler accessDeniedHandler;
    private final DataSource dataSource;
    private final LogoutSuccessHandler logoutSuccessHandler;

    @Value("SELECT email, password, active FROM user WHERE email=?")
    private String usersQuery;
    @Value("SELECT user.email, role.role " +
            "FROM user " +
            "INNER JOIN role_user ON (user.id=role_user.user_id) " +
            "INNER JOIN role ON (role_user.role_id=role.id) " +
            "WHERE user.email=? and user.active=1")
    private String rolesQuery;

    @Autowired
    public SpringSecurityConfig(AppAccessDeniedHandler accessDeniedHandler, @Qualifier("dataSource") DataSource dataSource, LogoutSuccessHandler logoutSuccessHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.dataSource = dataSource;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/",
                        "/home",
                        "/search",
                        "/search/**",
                        "/login",
                        "/registration/**",
                        "/activation/**",
                        "/advertisement/**",
                        "/h2/**",
                        "/403",
                        "/404").permitAll()
                .antMatchers("/user/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/logout").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().permitAll()

                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/", true)
                .usernameParameter("email").passwordParameter("password")

                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler(logoutSuccessHandler)

                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .accessDeniedPage("/403");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2/**","/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/fonts/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(14);
    }
}

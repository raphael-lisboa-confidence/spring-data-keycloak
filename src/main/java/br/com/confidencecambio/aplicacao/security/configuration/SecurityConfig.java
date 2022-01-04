package br.com.confidencecambio.aplicacao.security.configuration;

import br.com.confidencecambio.aplicacao.security.filter.AuthenticationFilter;
import br.com.confidencecambio.aplicacao.security.filter.CorsFilterCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile({"dev", "tst", "pp", "homolog", "prod"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationFilter authenticationFilter;

    @Autowired
    public SecurityConfig(final AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().antMatchers("/v3/api-docs/**",  "/swagger-ui.html", "/swagger-ui/**",
                        "/actuator/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf()
            .disable()
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .addFilterBefore(new CorsFilterCustom(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

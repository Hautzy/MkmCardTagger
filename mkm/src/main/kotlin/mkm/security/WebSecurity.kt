package mkm.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
open class WebSecurity(val userDetailsService: UserDetailsServiceImpl) : WebSecurityConfigurerAdapter() {

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity) {
        http
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/sign-up").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/card/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .addFilter(JWTAuthenticationFilter(authenticationManager()))
                    .addFilter(JWTAuthorizationFilter(authenticationManager()))
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder())
    }
}
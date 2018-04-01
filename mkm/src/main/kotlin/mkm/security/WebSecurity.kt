package mkm.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
open class WebSecurity(val userDetailsService: UserDetailsServiceImpl) : WebSecurityConfigurerAdapter() {

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity) {
        http
                .cors().and()
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/sign-up").permitAll()
                    .antMatchers(HttpMethod.GET, "/card/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/expansion/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .addFilter(JWTAuthenticationFilter(authenticationManager()))
                    .addFilter(JWTAuthorizationFilter(authenticationManager()))
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()

        var cors = CorsConfiguration().applyPermitDefaultValues()

        source.registerCorsConfiguration("/**", cors)
        return source
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder())
    }
}
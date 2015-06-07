package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.security.oauth2.sso.OAuth2SsoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class OAuth2SecureRouterConfiguration extends OAuth2SsoConfigurerAdapter {

    @Autowired
    private SecureRouteMatcherProperties matcherProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        for (HttpMethod method : matcherProperties.getHttpMethods()) {
            if (!matcherProperties.isSecure(method)) {
                http.authorizeRequests().antMatchers(method, matcherProperties.getPath(method)).permitAll();
            }
        }
    }
}

package demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix="spring.oauth2.sso")
public class SecureRouteMatcherProperties {
    private Map<String, Object> home = new HashMap<>();

    public void setHome(Map<String, Object> home) {
        this.home = home;
    }

    public Map<String, Object> getHome() {
        return home;
    }

    public List<HttpMethod> getHttpMethods() {

        List<HttpMethod> methodList = new ArrayList<>();

        for (String key : home.keySet()) {
            try {
                methodList.add(HttpMethod.valueOf(key));
            }
            catch (IllegalArgumentException ignore) {}
        }

        return methodList;
    }

    private Map<String, String> options(HttpMethod method) {
        return (Map<String, String>) home.get(method.name());
    }

    public boolean isSecure(HttpMethod method) {
        return Boolean.valueOf(options(method).get("secure"));
    }

    public String getPath(HttpMethod method) {
        return options(method).get("path");
    }
}

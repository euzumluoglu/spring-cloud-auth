package emin.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableResourceServer 
@RestController
public class SecurityConfig {
	
	@RequestMapping("/hello") //[1]
	 public String home() {
	      return "Hello World";
	 }
	
	 @Configuration
	 @EnableResourceServer // [2]
	 protected static class ResourceServer extends ResourceServerConfigurerAdapter {

	      @Override // [3]
	      public void configure(HttpSecurity http) throws Exception {
	           // @formatter:off
	           http
	           // Just for laughs, apply OAuth protection to only 2 resources
	           .requestMatchers().antMatchers("/hello").and()
	           .authorizeRequests()
	           .anyRequest().access("#oauth2.hasScope('read')"); //[4]
	           // @formatter:on
	      }

	      @Override
	      public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
	           resources.resourceId("RESOURCE_ID");
	      }

	 }

}

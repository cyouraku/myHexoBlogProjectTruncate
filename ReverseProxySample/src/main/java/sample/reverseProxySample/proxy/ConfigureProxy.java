package sample.reverseProxySample.proxy;

import java.util.Map;

import javax.servlet.Servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

@Component
public class ConfigureProxy {

	@Bean
	public Servlet medyPassProxyServlet(){
	    return new MedyPassServlet();
	}

	@Bean
	public Servlet d2puserProxyServlet(){
	    return new D2puserServlet();
	}

	@Bean
	public ServletRegistrationBean medyPassProxyServletRegistration(){
	    ServletRegistrationBean registrationBean = new ServletRegistrationBean(medyPassProxyServlet(), "/MedyPass");
	    Map<String, String> params = ImmutableMap.of(
	            "targetUri", "http://localhost:8088/_api/sharedRp/authenticate?token=cde9c85aaf9e848541ffe79560952edb","log", "true");
	    registrationBean.setInitParameters(params);
	    return registrationBean;
	}

	@Bean
	public ServletRegistrationBean d2puserProxyServletRegistration(){
	    ServletRegistrationBean registrationBean = new ServletRegistrationBean(d2puserProxyServlet(), "/d2puser");
	    Map<String, String> params = ImmutableMap.of(
	            "targetUri", "http://localhost:8081/api/1/medy?rl=http%3A%2F%2Flocalhost%3A8081%2F", "log", "true");
	    registrationBean.setInitParameters(params);
	    return registrationBean;
	}
}

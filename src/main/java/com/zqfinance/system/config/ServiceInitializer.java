/**
 * 系统启动入口
 */

package com.zqfinance.system.config;

import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.zqfinance.system.listener.RoleListener;

public class ServiceInitializer implements WebApplicationInitializer {
	private static Logger logger = LoggerFactory.getLogger(ServiceInitializer.class);
	
	public void onStartup(ServletContext servletContext) throws ServletException {
		logger.info("系统启动");
		AnnotationConfigWebApplicationContext serviceContext = new AnnotationConfigWebApplicationContext();
		serviceContext.register(ServerConfig.class);		
		servletContext.addListener(new ContextLoaderListener(serviceContext));
		
		servletContext.addListener(new RoleListener());
		
		Dynamic encodingFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		encodingFilter.addMappingForUrlPatterns(null, false, "/*");
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        //spring security处理
        Dynamic springSecurityFilterChain = servletContext.addFilter("springSecurityFilterChain",
                 org.springframework.web.filter.DelegatingFilterProxy.class);
         springSecurityFilterChain.addMappingForUrlPatterns(null, false, "/*");
        
        ServletRegistration.Dynamic notifyDispatcher = servletContext
                .addServlet("serviceDispatcher", new DispatcherServlet(serviceContext));
        notifyDispatcher.setLoadOnStartup(1);
        notifyDispatcher.setMultipartConfig(new MultipartConfigElement(null, 20000000, 20000000, 20000000));
        notifyDispatcher.addMapping("*.htm");
        

        

	}

}


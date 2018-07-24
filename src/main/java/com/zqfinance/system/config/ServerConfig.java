package com.zqfinance.system.config;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@ComponentScan(basePackages = "com.zqfinance")
@ImportResource("classpath:manage-web.xml")
@MapperScan("com.zqfinance.module.*.dao")
public class ServerConfig extends WebMvcConfigurationSupport{
	@Resource
	private HandlerExceptionResolver exceptionResolver;

	@Override
	protected void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
/*		ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver = new ExceptionHandlerExceptionResolver();
		exceptionHandlerExceptionResolver.setMessageConverters(getMessageConverters());
		exceptionHandlerExceptionResolver.afterPropertiesSet();

		exceptionResolvers.add(exceptionHandlerExceptionResolver);
		exceptionResolvers.add(new ResponseStatusExceptionResolver());
		exceptionResolvers.add(exceptionResolver);
		exceptionResolvers.add(new DefaultHandlerExceptionResolver());*/
	}
}


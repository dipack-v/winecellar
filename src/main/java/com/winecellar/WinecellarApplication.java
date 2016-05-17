package com.winecellar;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winecellar.repository.WineRepository;
import com.winecellar.repository.entity.WineEntity;

@SpringBootApplication
public class WinecellarApplication {

	public static void main(String[] args) {
		SpringApplication.run(WinecellarApplication.class, args);
	}
	
	@Bean
	public CustomizableTraceInterceptor interceptor() {

		CustomizableTraceInterceptor interceptor = new CustomizableTraceInterceptor();
		interceptor.setEnterMessage("Entering $[methodName]($[arguments]).");
		interceptor.setExitMessage("Leaving $[methodName](..) with return value $[returnValue], took $[invocationTime]ms.");

		return interceptor;
	}
	
	@Bean
	CommandLineRunner init(WineRepository wineRepository) {
		return args -> {
			byte[] jsonData = IOUtils.toByteArray(new ClassPathResource("store.json").getInputStream());
			ObjectMapper objectMapper = new ObjectMapper();
			List<WineEntity> wineList =  objectMapper.readValue(jsonData,objectMapper.getTypeFactory().constructCollectionType(List.class, WineEntity.class));
			wineRepository.save(wineList);
			
		};
	}
}

@Component
class SimpleCORSFilter implements Filter {  
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		  HttpServletResponse response = (HttpServletResponse) arg1;
		  response.setHeader("Access-Control-Allow-Origin", "*");
	      response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
	      response.setHeader("Access-Control-Max-Age", "3600");
	      response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
	      arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}


@Aspect
@Component
class RepositoryMonitor {
	@Before("execution( * org.springframework.data.repository.Repository+.*(..))")
	public void logServiceAccess(JoinPoint joinPoint) {
		System.out.println("Repository Method: " + joinPoint.getSignature().getName());
		System.out.println("Arguments: " + Arrays.toString(joinPoint.getArgs()));
	}

}

@ControllerAdvice
class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {  IllegalArgumentException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "This should be application specific !" ;
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}



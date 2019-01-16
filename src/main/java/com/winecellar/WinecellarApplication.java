package com.winecellar;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
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
@EnableEurekaClient
public class WinecellarApplication {

	public static void main(String[] args) {
		SpringApplication.run(WinecellarApplication.class, args);
	}

	@Bean
	public CustomizableTraceInterceptor interceptor() {

		CustomizableTraceInterceptor interceptor = new CustomizableTraceInterceptor();
		interceptor.setEnterMessage("Entering $[methodName]($[arguments]).");
		interceptor.setExitMessage(
				"Leaving $[methodName](..) with return value $[returnValue], took $[invocationTime]ms.");

		return interceptor;
	}

	@Bean
	CommandLineRunner init(WineRepository wineRepository) {
		return args -> {
			byte[] jsonData = IOUtils.toByteArray(new ClassPathResource("store.json").getInputStream());
			ObjectMapper objectMapper = new ObjectMapper();
			List<WineEntity> wineList = objectMapper.readValue(jsonData,
					objectMapper.getTypeFactory().constructCollectionType(List.class, WineEntity.class));
			wineRepository.saveAll(wineList);

		};
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
	@ExceptionHandler(value = { IllegalArgumentException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = "This should be application specific !";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
}

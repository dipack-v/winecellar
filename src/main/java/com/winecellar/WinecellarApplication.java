package com.winecellar;

import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winecellar.repository.WineRepository;
import com.winecellar.repository.entity.WineEntity;

@SpringBootApplication
public class WinecellarApplication {

	public static void main(String[] args) {
		SpringApplication.run(WinecellarApplication.class, args);
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

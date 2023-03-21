package com.qmt.besedo;

import com.qmt.besedo.service.data.DataGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Perform actions when application is ready
 * @see ApplicationReadyEvent
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class ApplicationStartupPostProcessing implements ApplicationListener<ApplicationReadyEvent> {

	private final DataGeneratorService dataGeneratorService;

	@Override
	public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
		log.info("Webservices are ready, launch startup post processes");
		dataGeneratorService.populateDatabase();
		log.info("Post processing is over, Webservices are ready to use");
	}
}

package com.lc.library.scheduler;

import com.lc.library.services.ArgentinaDatosLibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;

@Component
public class ArgentinaDatosSyncScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArgentinaDatosSyncScheduler.class);

    private final ArgentinaDatosLibraryService argentinaDatosLibraryService;

    public ArgentinaDatosSyncScheduler(ArgentinaDatosLibraryService argentinaDatosLibraryService) {
        this.argentinaDatosLibraryService = argentinaDatosLibraryService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        LOGGER.info("Iniciando sincronizacion historica al levantar la app");
        argentinaDatosLibraryService.syncHistoricos();
    }

    @Scheduled(cron = "${lc.library.sync.cron:0 0 1 * * *}")
    public void scheduledSync() {
        LOGGER.info("Iniciando sincronizacion historica programada");
        argentinaDatosLibraryService.syncHistoricos();
    }
}

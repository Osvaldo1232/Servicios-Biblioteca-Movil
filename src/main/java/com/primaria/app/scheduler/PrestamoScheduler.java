package com.primaria.app.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.primaria.app.Service.PrestamoService;

@Component
public class PrestamoScheduler {

	
	private final PrestamoService prestamoService;

    public PrestamoScheduler(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @Scheduled(cron = "0 10 0 * * *")
    public void marcarVencidosDiario() {
        prestamoService.actualizarEstatusVencidos();
    }
}

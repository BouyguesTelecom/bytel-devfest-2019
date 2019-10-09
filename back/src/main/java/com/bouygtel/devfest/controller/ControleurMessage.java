package com.bouygtel.devfest.controller;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.bouygtel.devfest.ressources.Ressources;

/**
 * Controleur principal de l'application, déclenche les actions voulues pour
 * chaque message
 * 
 * @author Arthur
 *
 */
@Controller
public class ControleurMessage {

	private static final Logger LOG = LoggerFactory.getLogger(ControleurMessage.class);

	private final Ressources ressources;
	private final ScheduledExecutorService executorService;

	public ControleurMessage(Ressources ressources) {
		this.ressources = ressources;
		executorService = Executors.newScheduledThreadPool(500);
	}

	public void nouveauMessage(long timestampMessage) {
		LOG.info("Nouveau message à traiter, reçu à {}", timestampMessage);
		ressources.ajouterFrappe();

		UUID id = UUID.randomUUID();
		ressources.startRequete(id);
		executorService.schedule(() -> endRequete(id), 10, TimeUnit.SECONDS);
	}

	private void endRequete(UUID id) {
		LOG.info("Fin de la requete {}", id);
		ressources.endRequete(id);
	}

}
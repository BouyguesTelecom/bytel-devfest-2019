package com.bouygtel.devfest.controller;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	private final ExecutorService executorService;

	public ControleurMessage(Ressources ressources) {
		this.ressources = ressources;
		executorService = Executors.newFixedThreadPool(1000);
	}

	public void nouveauMessage(long timestampMessage) {
//		LOG.info("Nouveau message à traiter, reçu à {}", timestampMessage);

		// On met ça dans un Executor, parce qu'on ne veut pas que le Thread qui gère le
		// MIDI
		// Soit impacté si y a un souci de communication WebSocket.
		executorService.execute(() -> {
			try {
				// Tout ça c'est déléguable ailleurs
				UUID id = UUID.randomUUID();
//				LOG.info("[TRAITEMENT] {}", id);
				ressources.ajouterFrappe();
//				LOG.info("[ACQUITTEMENT] {}", id);
				ressources.startRequete(id);
				Thread.sleep(4000); // Pour les feux d'artifice !
//				LOG.info("Fin de la requete {}", id);
				ressources.endRequete(id);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});

	}
}
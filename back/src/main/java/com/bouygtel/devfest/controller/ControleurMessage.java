package com.bouygtel.devfest.controller;

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

	public ControleurMessage(Ressources ressources) {
		this.ressources = ressources;
	}

	public void nouveauMessage(long timestampMessage) {
		LOG.info("Nouveau message à traiter, reçu à {}", timestampMessage);
		ressources.ajouterFrappe();
	}

}
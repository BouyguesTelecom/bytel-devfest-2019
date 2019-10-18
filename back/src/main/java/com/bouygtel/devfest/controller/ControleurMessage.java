package com.bouygtel.devfest.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Controller;

import com.bouygtel.devfest.aws.AWSConfig.LambdaService;
import com.bouygtel.devfest.aws.LambdaIn;
import com.bouygtel.devfest.midi.CustomMidiMessage;
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

	private final Ressources ressources;
	private final ExecutorService executorService;
	private final LambdaService lambdaService;

	public ControleurMessage(Ressources ressources, LambdaService lambdaService) {
		this.ressources = ressources;
		this.lambdaService = lambdaService;
		executorService = Executors.newFixedThreadPool(1000);
	}

	public void nouveauMessage(CustomMidiMessage midiMessage) {
		// On met ça dans un Executor, parce qu'on ne veut pas que le Thread qui gère le
		// MIDI
		// Soit impacté si y a un souci de communication WebSocket.
		executorService.execute(() -> {
			try {
				// Tout ça c'est déléguable ailleurs
				ressources.addHit();
				ressources.startRequest(midiMessage.getVelocity());
				lambdaService.call(new LambdaIn(ressources.getStats().getSession().getUiidSession()));
				Thread.sleep(2000); // Pour les feux d'artifice !
				ressources.endRequest(midiMessage.getVelocity());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});

	}
}
package com.bouygtel.devfest.midi;

import java.util.function.Consumer;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe qui réceptionne tout ce qui arrive en MIDI dans l'application.
 */
public class MidiExecutorReceiver implements Receiver {

	private static final Logger LOG = LoggerFactory.getLogger(MidiExecutorReceiver.class);

	private Consumer<Long> consommateur;

	public MidiExecutorReceiver(Consumer<Long> consommateur) {
		this.consommateur = consommateur;
	}

	@Override
	public void send(MidiMessage message, long timeStamp) {
		LOG.info("Réception d'un msg midi !");
		consommateur.accept(timeStamp);
	}

	@Override
	public void close() {
		LOG.debug("Fermeture du receiver");
	}

}

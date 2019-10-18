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

	private Consumer<CustomMidiMessage> consommateur;

	public MidiExecutorReceiver(Consumer<CustomMidiMessage> consommateur) {
		this.consommateur = consommateur;
	}

	@Override
	public void send(MidiMessage message, long timestamp) {
		final int status = message.getStatus();
		if (status >= 144) { // correspond au NoteOn
//			LOG.info("Réception d'un msg de début de note !");
			CustomMidiMessage customMidiMessage = new CustomMidiMessage(timestamp, message.getMessage()[2]);
			consommateur.accept(customMidiMessage);
		} else {
//			LOG.info("Réception d'un msg de fin de note");
		}
	}

	@Override
	public void close() {
		LOG.debug("Fermeture du receiver");
	}

}

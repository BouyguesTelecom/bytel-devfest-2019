package com.bouygtel.devfest.midi;

import java.util.Objects;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bouygtel.devfest.controller.ControleurMessage;
import com.bouygtel.devfest.utils.BeanPostProcessorBuilder;

@Configuration
public class MidiConfig {

	@Bean
	public MidiExecutorReceiver initMidiReceiver(ControleurMessage controleurMessage) {
		return new MidiExecutorReceiver(controleurMessage::nouveauMessage);
	}

	@Bean
	public BeanPostProcessor initMidiTransmitter() {
		// Enregistrement de notre receiver
		return new BeanPostProcessorBuilder<Receiver>()//
				.forClass(Receiver.class)//
				.withConsumer(MidiConfig::addReceiverToCurrentTransmitter)//
				.afterInitialization(true)//
				.build();
	}

	private static void addReceiverToCurrentTransmitter(Receiver receiver) {
		try {
			// Vive les singletons
			Transmitter transmitter = MidiSystem.getTransmitter();
			if (Objects.isNull(transmitter.getReceiver())) {
				transmitter.setReceiver(receiver);
			} else {
				throw new RuntimeException("Plusieurs Midi-Receiver implémentés");
			}
		} catch (MidiUnavailableException e) {
			throw new RuntimeException("Erreur d'initialisation du Transmetteur", e);
		}
	}
}

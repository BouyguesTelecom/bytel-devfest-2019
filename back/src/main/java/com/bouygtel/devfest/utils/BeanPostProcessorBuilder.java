package com.bouygtel.devfest.utils;

import java.util.function.Consumer;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.Assert;

/**
 * Utilitaire pour créer des BeanPostProcessor
 * 
 * @author Arthur
 */
public class BeanPostProcessorBuilder<T> {

	private Consumer<T> consumer = null;
	private Class<? extends T> beanClass = null;
	private String beanName = null;
	private Boolean isAfterInitialization = false;

	public BeanPostProcessorBuilder() {
	}

	public BeanPostProcessorBuilder<T> forClass(Class<? extends T> beanClass) {
		this.beanClass = beanClass;
		return this;
	}

	public BeanPostProcessorBuilder<T> forName(String beanName) {
		this.beanName = beanName;
		return this;
	}

	/**
	 * Défini la fonction qui traitera le bean
	 */
	public BeanPostProcessorBuilder<T> withConsumer(Consumer<T> consumer) {
		this.consumer = consumer;
		return this;
	}

	/**
	 * Défini si la fonction doit s'éxécuter avant ou après l'initialization du bean
	 */
	public BeanPostProcessorBuilder<T> afterInitialization(Boolean isAfterInitialization) {
		this.isAfterInitialization = isAfterInitialization;
		return this;
	}

	@SuppressWarnings("unchecked")
	public BeanPostProcessor build() {
		Assert.notNull(consumer, "Le consommateur doit être fourni");
		Assert.isTrue(beanClass != null || beanName != null, "Le nom ou la classe doivent être fournis");

		return new BeanPostProcessor() {

			@Override
			public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
				if (!isAfterInitialization && beanMatchesClassAndName(bean, name)) {
					consumer.accept((T) bean);
				}
				return bean;
			}

			@Override
			public Object postProcessAfterInitialization(Object bean, String name) throws BeansException {
				if (isAfterInitialization && beanMatchesClassAndName(bean, name)) {
					consumer.accept((T) bean);
				}
				return bean;
			}
		};
	}

	private boolean beanMatchesClassAndName(Object bean, String name) {
		if (beanClass != null && beanName != null) {
			return beanClass.isAssignableFrom(bean.getClass()) && name.equals(beanName);
		}
		return (beanClass != null && beanClass.isAssignableFrom(bean.getClass())) //
				|| name.equals(beanName);
	}

}

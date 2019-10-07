package com.bouygtel.devfest.websocket;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.bouygtel.devfest.ressources.Action;
import com.bouygtel.devfest.ressources.Message;
import com.bouygtel.devfest.ressources.Ressources;

/**
 * @author Arthur
 */
@Component
public class MessageHandler {

	private Map<Action, List<Method>> actionToFunctionsMap;

	private ApplicationContext context;
	private Ressources ressources = null;

	public MessageHandler(ApplicationContext context) {
		this.context = context;
		initMap();
	}

	public void initMap() {
		this.actionToFunctionsMap = new HashMap<>();
		for (Method method : Ressources.class.getDeclaredMethods()) {
			if (method.isAnnotationPresent(ActionExecuter.class)) {
				Action action = Action.valueOf(method.getAnnotation(ActionExecuter.class).action());
				actionToFunctionsMap.putIfAbsent(action, new ArrayList<>());
				actionToFunctionsMap.get(action).add(method);
				method.setAccessible(true);
			}
		}
	}

	public void handleMessage(Message message)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		if (ressources == null) {
			ressources = context.getBean(Ressources.class);
		}

		List<Method> methods = actionToFunctionsMap.getOrDefault(message.getAction(), Collections.emptyList());
		for (Method method : methods) {
			if (message.getValue() != null) {
				method.invoke(ressources, message.getValue());
			} else {
				method.invoke(ressources);
			}
		}
	}

}

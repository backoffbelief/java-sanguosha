package org.dizem.common.annotation;

import java.awt.event.ActionListener;
import java.lang.reflect.*;

/**
 * User: dizem@126.com
 * Time: 11-5-2 上午10:58
 */
public class ActionListenerInstaller {

	/**
	 * Processes all ActionListenerFor annotations in the given object
	 *
	 * @param obj an object whose methods may have ActionListenerFor annotations
	 * @see ActionListenerFor
	 */
	public static void processAnnotation(Object obj) {
		try {
			Class c = obj.getClass();
			for (Method m : c.getDeclaredMethods()) {
				ActionListenerFor a = m.getAnnotation(ActionListenerFor.class);

				if (a != null) {
					Field f = c.getDeclaredField(a.source());
					f.setAccessible(true);
					addListener(f.get(obj), obj, m);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds an action listener that calls a given method
	 * @param source the event source to which an action listener is added
	 * @param param the implicit parameter of the method that the listener calls
	 * @param m the method that the listener calls
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private static void addListener(Object source, final Object param, final Method m)
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		
		InvocationHandler handler = new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				return m.invoke(param);
			}
		};

		Object listener = Proxy.newProxyInstance(null, new Class[]{ActionListener.class}, handler);
		Method adder = source.getClass().getMethod("addActionListener", ActionListener.class);
		adder.invoke(source, listener);
	}
}

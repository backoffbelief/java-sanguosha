package org.dizem.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * User: DIZEM
 * Time: 11-4-9 下午7:24
 */
public class TwoWayMap<K, V> implements Cloneable, Serializable {

	private Map<K, V> keyToValue;
	private Map<V, K> valueToKey;

	public TwoWayMap() {
		keyToValue = new HashMap<K, V>();
		valueToKey = new HashMap<V, K>();
	}

	public void put(K key, V value) {
		if (keyToValue.containsKey(key)) {
			throw new RuntimeException("Already contains key: " + key);

		} else {
			keyToValue.put(key, value);
		}

		if (valueToKey.containsKey(value)) {
			throw new RuntimeException("Already contains value: " + value);
		} else {
			valueToKey.put(value, key);
		}
	}

	public K getKey(V value) {

		if (!valueToKey.containsKey(value)) {
			throw new RuntimeException("Value: " + value + " does not exist");

		} else {
			return valueToKey.get(value);
		}
	}

	private void display() {
		for(Map.Entry<V, K> entry : valueToKey.entrySet()) {
			System.out.print(entry.getKey() + " ");
		}
	}


	public V getValue(K key) {

		if (!keyToValue.containsKey(key)) {
			throw new RuntimeException("Key: " + key + " does not exist");

		} else {
			return keyToValue.get(key);
		}
	}

	public void removeByKey(K key) {
		if (!keyToValue.containsKey(key)) {
			throw new RuntimeException("Key: " + key + " does not exist");

		} else {
			V value = keyToValue.get(key);
			valueToKey.remove(value);
			keyToValue.remove(key);
			System.out.println("removeByKey: remain " + keyToValue.size() + " " + valueToKey.size());
		}
	}

	public void removeByValue(V value) {
		if (!valueToKey.containsKey(value)) {
			throw new RuntimeException("Value: " + value + " does not exist");

		} else {
			K key = valueToKey.get(value);
			keyToValue.remove(key);
			valueToKey.remove(value);
			//System.out.println("removeByValue: remain " + keyToValue.size() + " " + valueToKey.size());
			//display();
		}
	}

	public void clear() {
		keyToValue.clear();
		valueToKey.clear();
	}
}

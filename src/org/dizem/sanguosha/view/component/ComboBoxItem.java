package org.dizem.sanguosha.view.component;

/**
 * 下拉列表项
 *
 * User: dizem
 * Time: 11-4-24 下午6:27
 */
public class ComboBoxItem {
	private String name;
	private int id;

	public static final ComboBoxItem ANY_ONE_ITEM = new ComboBoxItem("所有人", -1);

	public ComboBoxItem(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return name;
	}
}

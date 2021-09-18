package com.example.knowledge_android.plu_show.bean;

public class TypeX {
	private int id;
	private String typename;
	private int icon;

	public TypeX(int id, String typename, int icon) {
		super();
		this.id = id;
		this.typename = typename;
		this.icon = icon;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}
}

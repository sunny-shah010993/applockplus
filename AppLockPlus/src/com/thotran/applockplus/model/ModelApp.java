package com.thotran.applockplus.model;

import android.graphics.drawable.Drawable;

public class ModelApp {

	public String name;
	public String type;
	public Drawable image;
	public boolean isChecked;
	public String packgeName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public Drawable getImage() {
		return image;
	}

	public void setImage(Drawable image) {
		this.image = image;
	}

	public String getPackgeName() {
		return packgeName;
	}

	public void setPackgeName(String packgeName) {
		this.packgeName = packgeName;
	}

}

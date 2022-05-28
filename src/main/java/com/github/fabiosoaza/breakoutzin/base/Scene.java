package com.github.fabiosoaza.breakoutzin.base;

import java.awt.Graphics2D;

public abstract class Scene {

	protected int height, width;

	public Scene(int width, int height) {
		this.height = height;
		this.width = width;
	}

	public abstract void load();

	public abstract void unload();

	public abstract void update();

	public abstract void render(Graphics2D g);

}

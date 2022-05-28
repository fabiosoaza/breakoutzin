package com.github.fabiosoaza.breakoutzin.base;

import java.awt.*;

public class Menu extends Text {

	private short idx;
	private String label;
	private String[] options;
	private boolean selected;

	public Menu(String label) {
		super();

		this.label = label;
		setWidth(120);
		setHeight(20);
		setColor(Color.WHITE);
	}

	public void addOptions(String... opcao) {
		options = opcao;
	}

	@Override
	public void render(Graphics2D g) {
		if (options == null)
			return;

		g.setColor(getColor());
		super.render(g, String.format("%s: <%s>", getLabel(), options[idx]), getPx(), getPy() + getHeight());

		if (selected)
			g.drawLine(getPx(), getPy() + getHeight() + 5, getPx() + getWidth(), getPy() + getHeight() + 5);

	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;

	}

	public int getOptionId() {
		return idx;
	}

	public String getOptionText() {
		return options[idx];
	}

	public void changeOption(boolean leftDirection) {
		if (!isSelected() || !isEnabled())
			return;

		idx += leftDirection ? -1 : 1;

		if (idx < 0)
			idx = (short) (options.length - 1);
		else if (idx == options.length)
			idx = 0;

	}

}

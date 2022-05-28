package com.github.fabiosoaza.breakoutzin.base;

public class MathUtils {

	public static float moveAngleX(float angle) {
		return (float) Math.cos(Math.toRadians(angle));
	}

	public static float moveAngleY(float angle) {
		return (float) Math.sin(Math.toRadians(angle));
	}

	public static void move(Entity el, float angle, int vel) {
		float cos = (float) Math.cos(Math.toRadians(angle));
		float sen = (float) Math.sin(Math.toRadians(angle));

		el.setPx(el.getMovPx() + cos * vel);
		el.setPy(el.getMovPy() + sen * vel);
	}

	public static float correctDegrees(float degrees) {
		if (degrees < 0)
			degrees += 360;
		else if (degrees > 360)
			degrees -= 360;

		return degrees;
	}

	public static float calcDirection(int xDestin, int yDestin, int xOrigin, int yOrigin) {
		return (float) Math.toDegrees(Math.atan2(yDestin - yOrigin, xDestin - xOrigin));
	}

	public static float calcDirection(Entity origin, Entity destin) {
		return (float) Math.toDegrees(Math.atan2(destin.getPy() - origin.getPy(), destin.getPx() - origin.getPx()));
	}

}

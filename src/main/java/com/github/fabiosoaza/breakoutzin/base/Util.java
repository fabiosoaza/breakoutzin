package com.github.fabiosoaza.breakoutzin.base;

public class Util {

    public static boolean collided(Entity a, Entity b) {
        if (!a.isEnabled() || !b.isEnabled()) {
            return false;
        }
            /*
            * rect1.x + rect1.width > rect2.x &&
            rect1.x < rect2.x + rect2.width &&
            rect1.y + rect1.height > rect2.y
            rect1.y < rect2.y + rect2.height &&
            * */
        int elementALeft = a.getLeft();
        final int elementARight = a.getRight();
        int elementBLeft = b.getLeft();
        final int elementBRight = b.getRight();

        int elementATop = a.getTop();
        final int elementABottom = a.getBottom();
        int elementBTop = b.getTop();
        final int elementBBottom = b.getBottom();

        if (elementARight > elementBLeft
                && elementALeft < elementBRight
                && elementABottom > elementBTop
                && elementATop < elementBBottom) {
            return true;
        }

        return false;

    }

    public static Direction getCollisionDirection(Entity collided, Entity collidee) {
        if (!collided(collided, collidee)) {
            return null;
        }
        if (collidedFromTop(collided, collidee)) {
            return Direction.TOP;
        } else if (collidedFromBottom(collided, collidee)) {
            return Direction.BOTTOM;
        } else if (collidedFromLeft(collided, collidee)) {
            return Direction.LEFT;
        } else if (collidedFromRight(collided, collidee)) {
            return Direction.RIGHT;
        }
        return null;
    }

    public static boolean collidedFromTop(Entity principal, Entity b) {
        return collided(principal, b) && principal.getOldBottom() <= b.getTop()  // was not colliding
                && principal.getBottom() > b.getTop();
    }

    public static boolean collidedFromBottom(Entity principal, Entity b) {
        return collided(principal, b)
                && principal.getOldTop() >= b.getBottom()  // was not colliding
                && principal.getTop() < b.getBottom();
    }

    public static boolean collidedFromLeft(Entity principal, Entity b) {
        return collided(principal, b) &&
                principal.getOldRight() <= b.getLeft()  // was not colliding
                && principal.getRight() > b.getLeft();
    }

    public static boolean collidedFromRight(Entity principal, Entity b) {
        return collided(principal, b) &&
                principal.getOldLeft() >= b.getRight() && // was not colliding
                principal.getLeft() < b.getRight();

    }


    public static boolean collidedX(Entity a, Entity b) {
        if (!a.isEnabled() || !b.isEnabled())
            return false;

        if (a.getRight() >= b.getPx() && a.getPx() <= b.getRight()) {
            return true;
        }

        return false;
    }


    public static void center(Entity el, int width, int height) {
        if (height > 0)
            el.setPy(height / 2 - el.getHeight() / 2);

        if (width > 0)
            el.setPx(width / 2 - el.getWidth() / 2);

    }

    public static boolean outBounded(Entity e, int width, int height, int margin) {
        if (e.getPx() < -margin || e.getPx() + e.getWidth() > width + margin)
            return true;

        if (e.getPy() < -margin || e.getPy() + e.getHeight() > height + margin)
            return true;

        return false;
    }

    /**
     * Teletransporte
     */
    public static void correctPosition(Entity el, int limitX, int limitY) {
        float nx = el.getMovPx();
        float ny = el.getMovPy();

        if (nx + el.getWidth() < 0)
            nx = limitX;
        else if (nx > limitX)
            nx = -el.getWidth();

        if (ny + el.getHeight() < 0)
            ny = limitY;
        else if (ny > limitY)
            ny = -el.getHeight();

        el.setPx(nx);
        el.setPy(ny);
    }

}

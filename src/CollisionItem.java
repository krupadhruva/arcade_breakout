public class CollisionItem extends Actor {
    void onCollision(CollisionItem other) {}

    @Override
    public void act(long now) {}

    /**
     * Moves the item by specified displacement along X & Y axis within in world boundary
     *
     * <p>Let us not allow item escape the boundaries. Once it escapes, we cannot depend on
     * collisions to detect location of an item in game world
     *
     * @param dx Displacement along X axis
     * @param dy Displacement along Y axis
     */
    @Override
    public void move(double dx, double dy) {
        double transX = getX() + dx, transY = getY() + dy;
        if (transX < 0.0) {
            dx = -getX();
        } else if (transX + getWidth() > getWorld().getWidth()) {
            dx = getWorld().getWidth() - getWidth() - getX();
        }

        if (transY < 0.0) {
            dy = -getY();
        } else if (transY + getHeight() > getWorld().getHeight()) {
            dy = getWorld().getHeight() - getHeight() - getY();
        }

        super.move(dx, dy);
    }
}

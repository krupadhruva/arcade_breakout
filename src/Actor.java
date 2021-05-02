import java.util.ArrayList;

import javafx.scene.image.ImageView;

public abstract class Actor extends ImageView {

	
	
	
	
	
	// Methods
	public void move(double dx, double dy) {
		this.setX(getX() + dx);
		this.setY(getY() + dy);
	}
	
	public World getWorld() {
		return (World)this.getParent();
	}
	
	public double getWidth() {
		return this.getBoundsInParent().getWidth();
	}
	
	public double getHeight() {
		return this.getBoundsInParent().getHeight();
	}
	
	public <A extends Actor> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls) {
		
		ArrayList<A> list = new ArrayList<A>();
		
		for (A object : list) {
			if (!object.equals(this) && 
					object.getClass().getTypeName().equals(cls.getTypeName()) &&
					object.intersects(this.getBoundsInParent())) {
				list.add(object);
			}
		}
		
		return list;
		
	}
	
	public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls) {
		
		ArrayList<A> list = new ArrayList<A>();
		
		for (A object : list) {
			if (!object.equals(this) && 
					object.getClass().getTypeName().equals(cls.getTypeName()) &&
					object.intersects(this.getBoundsInParent())) {
				return object;
			}
		}
		
		return null;
		
	}
	
	public abstract void act(long now);
}

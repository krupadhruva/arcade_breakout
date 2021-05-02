
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class World extends Pane {
	
	// Attributes
	private AnimationTimer timer;
	
	
	// Constructor
	public World() {
		
		timer = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				act(now);
				
				for (Node actor : getChildren()) {
					((Actor)actor).act(now);
				}
			}
			
		};
		
		
	}
	
	
	// Methods
	public void start() {
		timer.start();
	}
	
	public void stop() {
		timer.stop();
	}
	
	public void add(Actor actor) {
		this.getChildren().add((Node) actor);
	}
	
	public void remove(Actor actor) {
		this.getChildren().remove((Node) actor);
	}
	
	public <A extends Actor> java.util.List<A> getObjects(java.lang.Class<A> cls) {
		
		ArrayList<A> list = new ArrayList<A>();
		
		for (A object : list) {
			if (object.getClass().getTypeName().equals(cls.getTypeName())) {
				list.add(object);
			}
		}
		
		return list;
		
	}
	
	public abstract void act(long now);
}

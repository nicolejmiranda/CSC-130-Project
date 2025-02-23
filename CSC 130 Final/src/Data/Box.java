package Data;

public class Box {
		private Vector2D position; // Top-left corner of the box
	    private int width;
	    private int height;

	    // Constructor
	    public Box(Vector2D position, int width, int height) {
	        setPosition(position);
	        setWidth(width);
	        setHeight(height);
	    }
	    
		// Methods
	    public Vector2D getPosition() {
	        return position;
	    }

	    public int getWidth() {
	        return width;
	    }

	    public int getHeight() {
	        return height;
	    }

	    public void setPosition(Vector2D newPosition) {
	        position = newPosition;
	    }

	    public void setWidth(int newWidth) {
	      width = newWidth;
	    }

	    public void setHeight(int newHeight) {
	        height = newHeight;
	    }

	 // Check if the current box intersects with another box
	    public boolean intersects(Box other) {
	        return position.getX() < other.position.getX() + other.width &&
	               position.getX() + width > other.position.getX() &&
	               position.getY() < other.position.getY() + other.height &&
	               position.getY() + height > other.position.getY();
	    }

}

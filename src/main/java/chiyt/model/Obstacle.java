package chiyt.model;

public class Obstacle implements MapObject{
	private int x, y;

	public Obstacle(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String getSymbol() {
		return "□";
	}

	@Override
	public String getSimpleName() {
		return "Obstacle";
	}

	@Override
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public boolean touch(MapObject mover) {
		System.out.println(
			String.format("The position(%d,%d) is already occupied by %s, not moving.", 
			getY(), getX(), getSimpleName())
		);
		return false;//Don't move
	}
}

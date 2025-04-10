package chiyt.model;

public interface MapObject {
	int getX();
	int getY();
	String getSymbol();
	String getSimpleName();
	void setPosition(int x, int y);
	boolean touch(MapObject mover);
}
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
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

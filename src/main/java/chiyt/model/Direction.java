package chiyt.model;

public enum Direction {
	UP("↑"), DOWN("↓"), LEFT("←"), RIGHT("→");

	String symbol;

	Direction(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public int getDx() {
        switch (this) {
            case UP: return 0;
            case RIGHT: return 1;
            case DOWN: return 0;
            case LEFT: return -1;
            default: return 0;
        }
    }

    public int getDy() {
        switch (this) {
            case UP: return -1;
            case RIGHT: return 0;
            case DOWN: return 1;
            case LEFT: return 0;
            default: return 0;
        }
    }
}

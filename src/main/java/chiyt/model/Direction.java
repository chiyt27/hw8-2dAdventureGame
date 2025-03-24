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
}

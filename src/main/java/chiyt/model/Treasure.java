package chiyt.model;

import java.util.Random;

public class Treasure implements MapObject{
	enum TreasureType {
		SUPER_STAR("Super Star", State.INVINCIBLE),
		POISON("Poison", State.POISONED),
		ACCELERATING_POTION("Accelerating Potion", State.ACCELERATED),
		HEALING_POTION("Healing Potion", State.HEALING),
		DEVIL_FRUIT("Devil Fruit", State.ORDERLESS),
		KINGS_ROCK("King's Rock", State.STOCKPILE),
		DOKODEMO_DOOR("Dokodemo Door", State.TELEPORT);
	
		private final String name;
		private final State effect;
	
		TreasureType(String name, State effect) {
			this.name = name;
			this.effect = effect;
		}
	
		public String getName() {
			return name;
		}
	
		public State getEffect() {
			return effect;
		}
	}

	int x, y;
	TreasureType type;

	public Treasure(int x, int y) {
		this.x = x;
		this.y = y;
		
		Random rand = new Random();
		double r = rand.nextDouble();
		if (r < 0.1) type = TreasureType.SUPER_STAR;
		else if (r < 0.35) type = TreasureType.POISON;
		else if (r < 0.55) type = TreasureType.ACCELERATING_POTION;
		else if (r < 0.7) type = TreasureType.HEALING_POTION;
		else if (r < 0.8) type = TreasureType.DEVIL_FRUIT;
		else if (r < 0.9) type = TreasureType.KINGS_ROCK;
		else type = TreasureType.DOKODEMO_DOOR;
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
	public String getSymbol() {
		return "x";
	}

	@Override
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public TreasureType getType() {
		return this.type;
	}
}

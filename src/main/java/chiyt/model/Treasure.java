package chiyt.model;

import java.util.Random;

import chiyt.model.State.State;

public class Treasure implements MapObject{

	private static StateStrategy stateStrategy = new StateStrategy();

	enum TreasureType {
		SUPER_STAR("Super Star"),
		POISON("Poison"),
		ACCELERATING_POTION("Accelerating Potion"),
		HEALING_POTION("Healing Potion"),
		DEVIL_FRUIT("Devil Fruit"),
		KINGS_ROCK("King's Rock"),
		DOKODEMO_DOOR("Dokodemo Door");
	
		private final String name;
	
		TreasureType(String name) {
			this.name = name;
		}
	
		public String getName() {
			return name;
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
		type = TreasureType.ACCELERATING_POTION;
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

	@Override
	public boolean touch(MapObject obj) {
		return false;//Don't move
	}

	public void applyEffect(Role role) {
		State newState = stateStrategy.applyEffect(role, type);
		role.setState(newState);
	}
}

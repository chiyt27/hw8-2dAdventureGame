package chiyt.model;

import chiyt.model.strategy.MoveStrategy;
import chiyt.model.strategy.AttackStrategy;
import chiyt.model.strategy.NormalMoveStrategy;
import chiyt.model.strategy.NormalAttackStrategy;

public abstract class Role implements MapObject {
	private int x;
	private int y;
	private int hp;
	private State currentState;
	private MoveStrategy moveStrategy;
	private AttackStrategy attackStrategy;

	public Role(int x, int y) {
		this.x = x;
		this.y = y;
		this.hp = 300;
		this.currentState = new NormalState();
		this.moveStrategy = new NormalMoveStrategy();
		this.attackStrategy = new NormalAttackStrategy();
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State state) {
		this.currentState = state;
		updateStrategies(state);
	}

	public void setMoveStrategy(MoveStrategy moveStrategy) {
		this.moveStrategy = moveStrategy;
	}

	public void setAttackStrategy(AttackStrategy attackStrategy) {
		this.attackStrategy = attackStrategy;
	}

	protected boolean move(Direction direction, Map map) {
		return moveStrategy.move(this, direction, map);
	}

	protected void attack(Role target, Map map) {
		attackStrategy.attack(this, target, map);
	}

	private void updateStrategies(State state) {
		switch (state.getType()) {
			case ORDERLESS:
				// 隨機選擇上下或左右移動
				boolean isVertical = Math.random() < 0.5;
				setMoveStrategy(new OrderlessMoveStrategy(isVertical));
				break;
			case ERUPTING:
				setAttackStrategy(new EruptingAttackStrategy());
				break;
			default:
				setMoveStrategy(new NormalMoveStrategy());
				setAttackStrategy(new NormalAttackStrategy());
		}
	}

	public void takeDamage(int damage) {
		if (currentState instanceof InvincibleState) {
			return;
		}
		hp -= damage;
		if (hp <= 0) {
			hp = 0;
		}
	}
}
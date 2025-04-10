package chiyt.model;

import chiyt.model.State.NormalState;
import chiyt.model.State.State;

public abstract class Role implements MapObject{
	private int x, y, maxHp, hp;
	private State state;
	private int stateDuration;
	protected Map map;

	public Role(int x, int y, int hp, Map map) {
		this.x = x;
		this.y = y;
		this.maxHp = hp;
		this.hp = hp;
		this.map = map;
		this.setState(new NormalState(this));
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
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Map getMap(){
		return map;
	}

	public int getDuration(){
		return stateDuration;
	}

	public int getMaxHp(){
		return maxHp;
	}

	public int getHp(){
		return hp;
	}

	public void setHp(int hp){
		this.hp = hp;
	}

	public void setState(State state) {
		if(this.state!=null && !this.state.equals(state))
			System.out.println(
				String.format("%s(%d,%d) changes state from %s(%d) to %s(%d)", 
					this.getSimpleName(), getY(), getX(), 
					this.state.getName(), this.stateDuration,
					state.getName(), state.getDuration())
			);
		this.state = state;
		this.stateDuration = state.getDuration();
	}

	public void takeDamage(int damage) {
		state.handleDamage(damage);
	}

	public void heal(int amount) {
		hp = Math.min(maxHp, hp + amount);
	}

	public void move(Direction dir){
		int newX = this.x + dir.getDx();
		int newY = this.y + dir.getDy();
		map.moveObject(this, newX, newY);
	}

	@Override
	public boolean touch(MapObject mover) {
		System.out.println(
			String.format("The position(%d,%d) is already occupied by %s, not moving.", 
			getY(), getX(), getSimpleName())
		);
		return false;//Don't move
	}

	public void attack(Role target) {
		state.handleAttack(target);
	}

	public void playTurn(){
		State oldState = state;
		//1.
		state.startTurn();

		//2.
		map.printMap(this);
		String msg = "\u001B[42;37m" + this.getSimpleName() + "\u001B[0m ";
		msg += ("HP: " + getHp());
		msg += (", State: " + this.state.getName());
		msg += (", Duration: " + getDuration());

		System.out.println(msg);
		state.executeTurnAction();

		//3.
		//這次沒有改變狀態，需要扣效果持續時間
		if(oldState.equals(state)){
			this.stateDuration = Math.max(0, this.stateDuration-1);
		}

		state.endTurn();
	}

	public abstract void executeTurnAction();
	public abstract int getAttackPower();
}
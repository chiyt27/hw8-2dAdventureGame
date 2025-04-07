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

	public void setDuration(int stateDuration){
		this.stateDuration = Math.max(0, stateDuration);
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

	public State getState(){
		return state;
	}

	public void setState(State state) {
		if(this.state!=null && !this.state.equals(state))
			System.out.println(
				String.format("%s(%d,%d) changes state from %s(%d) to %s(%d)", 
					this.getClass().getSimpleName(), getY(), getX(), 
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
	public boolean touch(MapObject obj) {
		boolean canMove = true;
		// 如果位置是寶物，取得寶物效果。如果是主角、怪物或障礙物則不移動
		if(obj instanceof Treasure) {
			Treasure t = (Treasure) obj;
			System.out.println(String.format("%s(%d,%d) obtained %s(%d,%d)!", this.getClass().getSimpleName(), getY(), getX(), t.getType().getName(), t.getY(), t.getX()));
			t.applyEffect(this);
		}
		else {
			System.out.println(String.format("The position(%d,%d) is already occupied by %s, not moving.", obj.getY(), obj.getX(), obj.getClass().getSimpleName()));
			canMove = false;
		}
		return canMove;
	}

	public void attack(Role target) {
		state.handleAttack(target);
	}


	public void playTurn(){
		State oldState = state;
		//1.
		state.startTurn();

		//2.
		map.printMap(getX(), getY());
		String msg = "\u001B[42;37m" + getClass().getSimpleName() + "\u001B[0m ";
		msg += ("HP: " + getHp());
		msg += (", State: " + getState().getName());
		msg += (", Duration: " + getDuration());

		System.out.println(msg);
		state.executeTurnAction();

		//3.
		//這次沒有改變狀態，需要扣效果持續時間
		if(oldState.equals(state))
			setDuration(getDuration() - 1);

		state.endTurn();
	}

	public abstract void executeTurnAction();
	public abstract int getAttackPower();
}
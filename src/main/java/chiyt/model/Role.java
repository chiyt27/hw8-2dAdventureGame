package chiyt.model;

public abstract class Role implements MapObject{
	private int x, y, maxHp, hp;
	private State state = State.NORMAL;
	private int stateDuration;
	protected Map map;

	public Role(int x, int y, int hp, Map map) {
		this.x = x;
		this.y = y;
		this.maxHp = hp;
		this.hp = hp;
		this.setState(State.NORMAL);
		this.map = map;
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
		if(!this.state.equals(state))
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
		state.handleDamage(this, damage);
	}

	public void heal(int amount) {
		hp = Math.min(maxHp, hp + amount);
	}

	public void move(Direction dir){
		int newX = this.x, newY = this.y;
		if(dir.equals(Direction.UP))
			newY -= 1;
		else if (dir.equals(Direction.DOWN)) 
			newY += 1;
		else if (dir.equals(Direction.RIGHT)) 
			newX += 1;
		else if (dir.equals(Direction.LEFT)) 
			newX -= 1;

		map.moveObject(this, newX, newY);
	}

	@Override
	public boolean touch(MapObject obj) {
		boolean canMove = true;
		// 如果位置是寶物，取得寶物效果。如果是主角、怪物或障礙物則不移動
		if(obj instanceof Treasure) {
			Treasure t = (Treasure) obj;
			State state = t.getType().getEffect();
			System.out.println(String.format("%s(%d,%d) obtained %s(%d,%d)!", this.getClass().getSimpleName(), getY(), getX(), t.getType().getName(), t.getY(), t.getX()));
			this.setState(state);
		}
		else {
			System.out.println(String.format("The position(%d,%d) is already occupied by %s, not moving.", obj.getY(), obj.getX(), obj.getClass().getSimpleName()));
			canMove = false;
		}
		return canMove;
	}

	public void attack(Role target) {
		state.handleAttack(this, target);
	}


	public void playTurn(){
		State oldState = state;
		//1.
		state.startTurn(this);

		//2.
		map.printMap(getX(), getY());
		String msg = "\u001B[42;37m" + getClass().getSimpleName() + "\u001B[0m HP: " 
						+ getHp() + ", State: " + getState().getName();
		if(!getState().equals(State.NORMAL))
			msg += (", Duration: " + getDuration());

		System.out.println(msg);
		state.executeTurnAction(this);

		//3.
		//這次沒有改變狀態，需要扣效果持續時間
		if(oldState.equals(state))
			setDuration(getDuration() - 1);

		state.endTurn(this);
	}

	public abstract void executeTurnAction();
	public abstract int getAttackPower();
}
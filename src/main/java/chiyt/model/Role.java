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
	
	public int getMaxHp(){
		return maxHp;
	}

	public int getHp(){
		return hp;
	}

	public State getState(){
		return state;
	}

	public Map getMap(){
		return map;
	}

	public int getDuration(){
		return stateDuration;
	}

	public void setState(State state) {
		State oldState = this.state;
		if(!oldState.equals(state))
			System.out.println(
				String.format("%s(%d,%d) changes state from %s to %s", 
				this.getClass().getSimpleName(), getY(), getX(), oldState.getName(), state.getName())
			);
		this.state = state;
		this.stateDuration = state.getDuration();
		state.applyEffect(this);
	}

	public void takeDamage(int damage) {
		if(state.equals(State.INVINCIBLE))
			return;
		//加速:若在期間遭受攻擊則立刻恢復至正常狀態
		else if(state.equals(State.ACCELERATED)){
			this.setState(State.NORMAL);
		}
		//蓄力:若在期間遭受攻擊則立刻恢復至正常狀態
		else if(state.equals(State.STOCKPILE)){
			this.setState(State.NORMAL);
		}

		hp = Math.max(0, hp - damage);
		if (hp == 0) {
			System.out.println(String.format("%s(%d,%d) dies!", this.getClass().getSimpleName(), getY(), getX()));
			map.removeObj(this);
		}
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

		map.moveRole(this, newX, newY);
	}

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
		target.takeDamage(getAttackPower());
		System.out.println(
			String.format("%s(%d,%d) attacks %s (%d,%d)", 
			this.getClass().getSimpleName(), getY(), getY(), 
			target.getClass().getSimpleName(), target.getY(), target.getX()
		));
	}


	public void playTurn(){
		updateStateBeforeAction();

		int actionCnt = 1;
		if(this.state.equals(State.ACCELERATED))
			actionCnt = 2;

		for(int i=0; i<actionCnt; i++){
			map.printMap(this.getX(), this.getY());
			String msg = "\u001B[42;37m" + this.getClass().getSimpleName() + "\u001B[0m HP: " + this.getHp() + ", State: " + this.getState().getName();
			if(!state.equals(State.NORMAL))
				msg += (", Duration: " + stateDuration);

			System.out.println(msg);
			executeTurnAction();
		}

		updateStateAfterAction();
	}


	public void updateStateBeforeAction() {
		if (!state.equals(State.NORMAL)) {
			stateDuration--;
			state.applyEffect(this);
			if (stateDuration <= 0) {
				state = State.NORMAL; // 狀態結束
			}
		}
	}

	public void updateStateAfterAction() {
		
	}

	public abstract void executeTurnAction();
	public abstract int getAttackPower();
}
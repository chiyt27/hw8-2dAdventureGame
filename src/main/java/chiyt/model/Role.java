package chiyt.model;

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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        this.stateDuration = State.NORMAL.getDuration();
    }

    public void takeDamage(int damage) {
        if(state == State.INVINCIBLE)
            return;
        hp = Math.max(0, hp - damage);
        if (hp == 0) {
            System.out.println(this.getClass().getSimpleName() + " dies!");
            map.removeObj(this);
        }
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }

    public void updateState() {
        if (state != State.NORMAL) {
            state.applyEffect(this);
            stateDuration--;
            if (stateDuration <= 0) {
                state = State.NORMAL; // 狀態結束
            }
        }
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
            System.out.println("Obtained " + t.getType().getName() +"!");
            t.getType().getEffect().applyEffect(this);
        }
        else {
            System.out.println("The position is already occupied by " + obj.getClass().getSimpleName() + ", not moving.");
            canMove = false;
        }
        return canMove;
    }

    public void attack(Role target) {
        target.takeDamage(getAttackPower());
        System.out.println(
            String.format("%s(%d, %d) attacks %s (%d, %d)", 
            this.getClass().getSimpleName(), getY(), getY(), 
            target.getClass().getSimpleName(), target.getX(), target.getY()
        ));
    }

    public abstract void playTurn();
    public abstract int getAttackPower();
}
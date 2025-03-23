package chiyt.model;

public abstract class Role implements MapObject{
    private int x, y, maxHp, hp;
    private State state;
    private int stateDuration;

    public Role(int x, int y, int hp) {
        this.x = x;
        this.y = y;
        this.maxHp = hp;
        this.hp = hp;
        this.setState(State.NORMAL);
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
}
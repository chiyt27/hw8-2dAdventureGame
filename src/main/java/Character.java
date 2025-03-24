import states.*;
import commands.MoveCommand;

public class Character implements MapObject {
    private int x;
    private int y;
    private Direction direction;
    private int hp;
    private State currentState;
    private boolean canMoveTwice;

    public Character(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.hp = 300;
        this.currentState = new NormalState();
        this.canMoveTwice = false;
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
    public String getSymbol() {
        return direction.getSymbol();
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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
    }

    public boolean canMoveTwice() {
        return canMoveTwice;
    }

    public void setCanMoveTwice(boolean canMoveTwice) {
        this.canMoveTwice = canMoveTwice;
    }

    public void move(Direction newDirection, Map map) {
        setDirection(newDirection);
        MoveCommand moveCommand = new MoveCommand(map, this, newDirection);
        moveCommand.execute();
    }

    public void attack(Map map) {
        int attackX = x;
        int attackY = y;
        while (true) {
            attackX += direction.getDx();
            attackY += direction.getDy();
            
            if (!map.isValidPosition(attackX, attackY)) {
                break;
            }
            
            MapObject target = map.getObject(attackX, attackY);
            if (target instanceof Monster) {
                ((Monster) target).takeDamage(1);
                if (((Monster) target).isDead()) {
                    map.removeObject(attackX, attackY);
                }
            } else if (target instanceof Obstacle) {
                break;
            }
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
import java.util.Random;
import states.*;
import commands.MoveCommand;

public class Monster implements MapObject {
    private int x;
    private int y;
    private int hp;
    private State currentState;

    public Monster(int x, int y) {
        this.x = x;
        this.y = y;
        this.hp = 1;
        this.currentState = new NormalState();
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
        return "M";
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
    }

    public boolean isDead() {
        return hp <= 0;
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

    public void takeTurn(Map map, Character player) {
        // 檢查是否在攻擊範圍內
        if (isInAttackRange(player)) {
            attack(player);
            return;
        }

        // 決定移動方向
        Direction[] directions = Direction.values();
        Random random = new Random();
        Direction direction = directions[random.nextInt(directions.length)];

        // 移動
        MoveCommand moveCommand = new MoveCommand(map, this, direction);
        moveCommand.execute();
    }

    private boolean isInAttackRange(Character player) {
        int dx = Math.abs(x - player.getX());
        int dy = Math.abs(y - player.getY());
        return dx <= 1 && dy <= 1;
    }

    private void attack(Character player) {
        player.takeDamage(50);
        if (!(player.getCurrentState() instanceof StockpileState) && 
            !(player.getCurrentState() instanceof AcceleratedState)) {
            player.setCurrentState(new InvincibleState());
        }
    }
} 
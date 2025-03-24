package states;

import java.util.Random;

public class OrderlessState implements State {
    private int duration = 3;
    private boolean canMoveVertically;

    public OrderlessState() {
        canMoveVertically = new Random().nextBoolean();
    }

    @Override
    public void onTurnStart(Character character) {
        // 混亂狀態沒有特殊效果
    }

    @Override
    public void onTurnEnd(Character character) {
        duration--;
        if (duration <= 0) {
            character.setCurrentState(new NormalState());
        }
    }

    @Override
    public void onDamage(Character character) {
        // 混亂狀態沒有特殊效果
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean canMoveVertically() {
        return canMoveVertically;
    }
} 
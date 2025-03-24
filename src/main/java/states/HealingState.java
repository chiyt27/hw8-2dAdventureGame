package states;

public class HealingState implements State {
    private int duration = 5;

    @Override
    public void onTurnStart(Character character) {
        int currentHp = character.getHp();
        if (currentHp < 300) {
            character.setHp(Math.min(300, currentHp + 30));
        } else {
            character.setCurrentState(new NormalState());
        }
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
        // 恢復狀態沒有特殊效果
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }
} 
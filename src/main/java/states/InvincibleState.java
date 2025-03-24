package states;

public class InvincibleState implements State {
    private int duration = 2;

    @Override
    public void onTurnStart(Character character) {
        // 無敵狀態沒有特殊效果
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
        // 無敵狀態不會受到傷害
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
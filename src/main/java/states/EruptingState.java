package states;

public class EruptingState implements State {
    private int duration = 3;

    @Override
    public void onTurnStart(Character character) {
        // 爆發狀態沒有特殊效果
    }

    @Override
    public void onTurnEnd(Character character) {
        duration--;
        if (duration <= 0) {
            character.setCurrentState(new TeleportState());
        }
    }

    @Override
    public void onDamage(Character character) {
        // 爆發狀態沒有特殊效果
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
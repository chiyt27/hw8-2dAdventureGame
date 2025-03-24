package states;

public class StockpileState implements State {
    private int duration = 2;

    @Override
    public void onTurnStart(Character character) {
        // 蓄力狀態沒有特殊效果
    }

    @Override
    public void onTurnEnd(Character character) {
        duration--;
        if (duration <= 0) {
            character.setCurrentState(new EruptingState());
        }
    }

    @Override
    public void onDamage(Character character) {
        character.setCurrentState(new NormalState());
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
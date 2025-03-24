public class NormalState implements State {
    private int duration = 0;

    @Override
    public void onTurnStart(Character character) {
        // 正常狀態沒有特殊效果
    }

    @Override
    public void onTurnEnd(Character character) {
        // 正常狀態沒有特殊效果
    }

    @Override
    public void onDamage(Character character) {
        // 正常狀態沒有特殊效果
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
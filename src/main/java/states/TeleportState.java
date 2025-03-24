package states;

import java.util.Random;

public class TeleportState implements State {
    private int duration = 1;

    @Override
    public void onTurnStart(Character character) {
        // 瞬身狀態沒有特殊效果
    }

    @Override
    public void onTurnEnd(Character character) {
        duration--;
        if (duration <= 0) {
            // 隨機傳送到地圖上的空位置
            // 這個邏輯會在Game類中處理
            character.setCurrentState(new NormalState());
        }
    }

    @Override
    public void onDamage(Character character) {
        // 瞬身狀態沒有特殊效果
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
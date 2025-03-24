package states;

public class AcceleratedState implements State {
    private int duration = 3;

    @Override
    public void onTurnStart(Character character) {
        character.setCanMoveTwice(true);
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
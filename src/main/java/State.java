public interface State {
    void onTurnStart(Character character);
    void onTurnEnd(Character character);
    void onDamage(Character character);
    int getDuration();
    void setDuration(int duration);
} 
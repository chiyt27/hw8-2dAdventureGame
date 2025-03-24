import java.util.Random;
import states.*;

public class Treasure implements MapObject {
    private int x;
    private int y;
    private TreasureType type;

    public Treasure(int x, int y, TreasureType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public static Treasure generateRandomTreasure(int x, int y) {
        Random random = new Random();
        double chance = random.nextDouble();
        TreasureType type;

        if (chance < 0.1) {
            type = TreasureType.SUPER_STAR;
        } else if (chance < 0.35) {
            type = TreasureType.POISON;
        } else if (chance < 0.55) {
            type = TreasureType.ACCELERATING_POTION;
        } else if (chance < 0.7) {
            type = TreasureType.HEALING_POTION;
        } else if (chance < 0.8) {
            type = TreasureType.DEVIL_FRUIT;
        } else if (chance < 0.9) {
            type = TreasureType.KINGS_ROCK;
        } else {
            type = TreasureType.DOKODEMO_DOOR;
        }

        return new Treasure(x, y, type);
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
        return "x";
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    public void applyEffect(MapObject target) {
        if (target instanceof Character) {
            Character character = (Character) target;
            switch (type) {
                case SUPER_STAR:
                    character.setCurrentState(new InvincibleState());
                    break;
                case POISON:
                    character.setCurrentState(new PoisonedState());
                    break;
                case ACCELERATING_POTION:
                    character.setCurrentState(new AcceleratedState());
                    break;
                case HEALING_POTION:
                    character.setCurrentState(new HealingState());
                    break;
                case DEVIL_FRUIT:
                    character.setCurrentState(new OrderlessState());
                    break;
                case KINGS_ROCK:
                    character.setCurrentState(new StockpileState());
                    break;
                case DOKODEMO_DOOR:
                    character.setCurrentState(new TeleportState());
                    break;
            }
        } else if (target instanceof Monster) {
            Monster monster = (Monster) target;
            switch (type) {
                case SUPER_STAR:
                    monster.setCurrentState(new InvincibleState());
                    break;
                case POISON:
                    monster.setCurrentState(new PoisonedState());
                    break;
                case ACCELERATING_POTION:
                    monster.setCurrentState(new AcceleratedState());
                    break;
                case HEALING_POTION:
                    monster.setCurrentState(new HealingState());
                    break;
                case DEVIL_FRUIT:
                    monster.setCurrentState(new OrderlessState());
                    break;
                case KINGS_ROCK:
                    monster.setCurrentState(new StockpileState());
                    break;
                case DOKODEMO_DOOR:
                    monster.setCurrentState(new TeleportState());
                    break;
            }
        }
    }

    private enum TreasureType {
        SUPER_STAR,
        POISON,
        ACCELERATING_POTION,
        HEALING_POTION,
        DEVIL_FRUIT,
        KINGS_ROCK,
        DOKODEMO_DOOR
    }
} 
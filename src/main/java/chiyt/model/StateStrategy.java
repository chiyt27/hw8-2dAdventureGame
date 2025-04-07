package chiyt.model;

import chiyt.model.State.State;
import chiyt.model.State.AcceleratedState;
import chiyt.model.State.HealingState;
import chiyt.model.State.InvincibleState;
import chiyt.model.State.OrderlessState;
import chiyt.model.State.PoisonedState;
import chiyt.model.State.StockpileState;
import chiyt.model.State.TeleportState;


public class StateStrategy {
    public State applyEffect(Role target, Treasure.TreasureType type) {
        switch (type) {
            case SUPER_STAR: 
                return new InvincibleState(target);
            case POISON: 
                return new PoisonedState(target);
            case ACCELERATING_POTION: 
                return new AcceleratedState(target);
            case HEALING_POTION: 
                return new HealingState(target);
            case DEVIL_FRUIT: 
                return new OrderlessState(target);
            case KINGS_ROCK: 
                return new StockpileState(target);
            case DOKODEMO_DOOR: 
                return new TeleportState(target);
            default: 
                throw new IllegalArgumentException("Unknown TreasureType: " + type);
        }
    }
}

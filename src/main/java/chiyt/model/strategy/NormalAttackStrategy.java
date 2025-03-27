package chiyt.model.strategy;

import chiyt.model.Map;
import chiyt.model.Role;

public class NormalAttackStrategy implements AttackStrategy {
    @Override
    public void attack(Role attacker, Role target, Map map) {
        target.takeDamage(50);
    }
}
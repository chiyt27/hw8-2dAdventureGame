package chiyt.model.strategy;

import chiyt.model.Map;
import chiyt.model.Role;
import chiyt.model.Monster;

public class EruptingAttackStrategy implements AttackStrategy {
    @Override
    public void attack(Role attacker, Role target, Map map) {
        // 攻擊所有怪物
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                var object = map.getObject(i, j);
                if (object instanceof Monster) {
                    ((Monster) object).takeDamage(50);
                    if (((Monster) object).isDead()) {
                        map.removeObject(i, j);
                    }
                }
            }
        }
    }
}
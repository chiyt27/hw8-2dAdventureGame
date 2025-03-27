package chiyt.model.strategy;

import chiyt.model.Map;
import chiyt.model.Role;

public class EruptingAttackStrategy implements AttackStrategy {
    @Override
    public void attack(Role attacker, Role target, Map map) {
		if(!attacker.equals(map.getPlayer()))
			map.getPlayer().takeDamage(50);
		
		for(Role monster : map.getMonsters()){
			if(attacker.equals(monster))
				continue;

			monster.takeDamage(50);
		}
    }
}
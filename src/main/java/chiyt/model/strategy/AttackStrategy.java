package chiyt.model.strategy;

import chiyt.model.Map;
import chiyt.model.Role;

public interface AttackStrategy {
	void attack(Role attacker, Role target, Map map);
}

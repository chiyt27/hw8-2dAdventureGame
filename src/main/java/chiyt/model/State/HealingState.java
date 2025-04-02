package chiyt.model.State;

import chiyt.model.Role;
import chiyt.model.Character;
import chiyt.model.Monster;

public class HealingState extends State{

	public HealingState(Role target) {
		super(target, "Healing", 3);
	}
	
	@Override
	public void startTurn() {
		// 每回合開始時恢復30點生命值，直到滿血。若滿血則立刻恢復至正常狀態
		target.heal(30);
		if(target.getHp() == target.getMaxHp()) 
			target.setState(new NormalState(target));
	}

	@Override
	public void handleDamage(int damage){
		super.handleDamage(damage);
		if (target.getHp() > 0) {
			if(target instanceof Character)
				target.setState(new InvincibleState(target));
		}
	}
}

package chiyt.model.State;

import chiyt.model.Role;
import chiyt.model.Character;
import chiyt.model.Monster;

public class PoisonedState extends State{

	public PoisonedState(Role target) {
			super(target, "Poisoned", 3);
		}
	
		@Override
	public void startTurn(){
		// 每回合開始時失去15點生命值
		System.out.println("Lose 15 HP due to poisoning.");
		super.handleDamage(15);
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

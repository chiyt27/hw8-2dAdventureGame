package chiyt.model.State;

import chiyt.model.Role;
import chiyt.model.Character;
import chiyt.model.Monster;

public class NormalState extends State{

	public NormalState(Role target) {
		super(target, "Normal", Integer.MAX_VALUE);
	}

	@Override
	public void handleDamage(int damage){
		super.handleDamage(damage);
		if (target.getHp() > 0) {
			if(target instanceof Character) {
				((Character) target).setState(new InvincibleState(target));
			}
		}
	}
}

package chiyt.model.State;

import chiyt.model.Role;

public class InvincibleState extends State{

	public InvincibleState(Role target) {
		super(target, "Invincible", 2);
	}

	@Override
	public void handleDamage(int damage){
		// 受到攻擊時並不會有任何生命損失
		System.out.println(String.format("%s(%d,%d) is invincible and will not take damage!", target.getClass().getSimpleName(), target.getY(), target.getX()));	
	}
}

package chiyt.model.State;

import chiyt.model.Role;
import chiyt.model.Character;
import chiyt.model.Monster;

public class EruptingState extends State{

	public EruptingState(Role target) {
		super(target, "Erupting", 3);
	}
	
	@Override
	public void endTurn() {
		//三回合過後取得瞬身狀態
		if (target.getDuration() == 0) {
			target.setState(new TeleportState(target));
		}
	}

	@Override
	public void handleDamage(int damage){
		super.handleDamage(damage);
		if (target.getHp() > 0) {
			if(target instanceof Character)
				target.setState(new InvincibleState(target));
		}
	}

	@Override
	public void handleAttack(Role attackedTarget){
		Role attacker = this.target;
		Role target = attackedTarget;
		// 角色的攻擊範圍擴充至「全地圖」，且攻擊行為變成「全場攻擊」：每一次攻擊時都會攻擊到地圖中所有其餘角色，且攻擊力為50。
		System.out.println(String.format("%s(%d,%d) attacks all roles in the map!", attacker.getSimpleName(), attacker.getY(), attacker.getX()));
		if(!attacker.equals(attacker.getMap().getPlayer()))
			attacker.getMap().getPlayer().takeDamage(50);
		
		for(Role monster : attacker.getMap().getMonsters()){
			if(attacker.equals(monster))
				continue;

			monster.takeDamage(50);
		}
	}
}

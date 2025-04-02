package chiyt.model.State;

import chiyt.model.Role;

import chiyt.model.Character;
import chiyt.model.Map;
import chiyt.model.Monster;

public class TeleportState extends State{

	public TeleportState(Role target) {
		super(target, "Teleport", 1);
	}

	@Override
	public void endTurn() {
		//一回合後角色的位置將被隨機移動至任一空地
		if (target.getDuration() == 0) {
			Map map = target.getMap();
			map.randomPlaceObject(target);
			target.setState(new NormalState(target));
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
}

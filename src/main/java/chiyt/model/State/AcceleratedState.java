package chiyt.model.State;

import chiyt.model.Role;

public class AcceleratedState extends State{

	public AcceleratedState(Role target) {
		super(target, "Accelerated", 3);
	}
	
	@Override
	public void handleDamage(int damage){
		// 若在期間遭受攻擊則立刻恢復至正常狀態
		super.handleDamage( damage);
		System.out.println(String.format("%s(%d,%d) in ACCELERATED is attacked and will return to normal state!", target.getClass().getSimpleName(), target.getY(), target.getX()));
		target.setState(new NormalState(target));
	}

	@Override
	public void executeTurnAction(){
		// 每回合中可以進行「兩次動作」
		for(int i = 0; i < 2; i++)
			super.executeTurnAction();
	}
}

package chiyt.model.State;

import chiyt.model.Role;

public class StockpileState extends State{

	public StockpileState(Role target) {
		super(target, "Stockpile", 2);
	}
	
	@Override
	public void endTurn() {
		// 兩回合後進入爆發狀態
		if (target.getDuration() == 0) {
			target.setState(new EruptingState(target));
		}
	}

	@Override
	public void handleDamage(int damage){
		super.handleDamage( damage);
		// 若在期間遭受攻擊則立刻恢復至正常狀態
		System.out.println(String.format("%s(%d,%d) in STOCKPILE is attacked and will return to normal state!", target.getSimpleName(), target.getY(), target.getX()));
		target.setState(new NormalState(target));
	}
}

package chiyt.model;

import chiyt.model.strategy.AttackStrategy;
import chiyt.model.strategy.MoveStrategy;
import chiyt.model.strategy.NormalAttackStrategy;
import chiyt.model.strategy.NormalMoveStrategy;
import chiyt.model.strategy.OrderlessMoveStrategy;

public enum State {
	NORMAL("Normal", 0, new NormalMoveStrategy(), new NormalAttackStrategy()) {
		@Override
		public void applyEffect(Role target) {
			// 什麼都不做
		}
	},
	INVINCIBLE("Invincible", 2, new NormalMoveStrategy(), new NormalAttackStrategy()) {
		@Override
		public void applyEffect(Role target) {
			// 受到攻擊時並不會有任何生命損失
		}
	},
	POISONED("Poisoned", 3, new NormalMoveStrategy(), new NormalAttackStrategy()) {
		@Override
		public void applyEffect(Role target) {
			// 每回合開始時失去15點生命值
			target.takeDamage(15);
		}
	},
	ACCELERATED("Accelerated", 3, new NormalMoveStrategy(), new NormalAttackStrategy()) {
		@Override
		public void applyEffect(Role target) {
			// 每回合中可以進行「兩次動作」，若在期間遭受攻擊則立刻恢復至正常狀態
		}
	},
	HEALING("Healing", 5, new NormalMoveStrategy(), new NormalAttackStrategy()) {
		@Override
		public void applyEffect(Role target) {
			// 每回合開始時恢復30點生命值，直到滿血。若滿血則立刻恢復至正常狀態
			target.heal(30);
			if(target.getHp() == target.getMaxHp()) 
				target.setState(State.NORMAL);
		}
	},
	ORDERLESS("Orderless", 3, new OrderlessMoveStrategy(), new NormalAttackStrategy()) {
		@Override
		public void applyEffect(Role target) {
			// 每回合隨機取得以下其中一種效果：
			// 1. 只能進行上下移動 
			// 2. 只能進行左右移動（角色只能移動，不能選擇做其他操作）
			// Random rand = new Random();
			// if (rand.nextBoolean()) {
			//	 // 只能上下移動
			//	 System.out.println("Can only move UP or DOWN.");
			// } else {
			//	 // 只能左右移動
			//	 System.out.println("Can only move LEFT or RIGHT.");
			// }
		}
	},
	STOCKPILE("Stockpile", 2, new NormalMoveStrategy(), new NormalAttackStrategy()) {
		@Override
		public void applyEffect(Role target) {
			// 兩回合後進入爆發狀態，若在期間遭受攻擊則立刻恢復至正常狀態
			if (target.getDuration() == 0) {
				target.setState(State.ERUPTING);
			}
		}
	},
	ERUPTING("Erupting", 3, new NormalMoveStrategy(), new NormalAttackStrategy()) {
		@Override
		public void applyEffect(Role target) {
			// 角色的攻擊範圍擴充至「全地圖」，且攻擊行為變成「全場攻擊」：每一次攻擊時都會攻擊到地圖中所有其餘角色，且攻擊力為50。三回合過後取得瞬身狀態。
			// if (target.stateDuration == 0) {
			//	 target.state = TELEPORT;
			//	 target.stateDuration = TELEPORT.defaultDuration;
			// }
		}
	},
	TELEPORT("Teleport", 1, new NormalMoveStrategy(), new NormalAttackStrategy()) {
		@Override
		public void applyEffect(Role target) {
			// 一回合後角色的位置將被隨機移動至任一空地
			if(target.getDuration()	== 0){
				Map map = target.getMap();
				map.placeObject(target);
			}
		}
	};

	private final String name;
	private final int duration;
	private final MoveStrategy moveStrategy;
	private final AttackStrategy attackStrategy;

	State(String name, int duration, MoveStrategy moveStrategy, AttackStrategy attackStrategy) {
		this.name = name;
		this.duration = duration;
		this.moveStrategy= moveStrategy;
		this.attackStrategy = attackStrategy;
	}

	public String getName() {
		return name;
	}

	public int getDuration() {
		return duration;
	}

	public MoveStrategy getMoveStrategy(){
		return moveStrategy;
	}

	public AttackStrategy getAttackStrategy(){
		return attackStrategy;
	}

	public void handleDamage(Role target, int damage){
		target
	}

	// 每個狀態需要實現自己的效果
	public abstract void applyEffect(Role target);
}
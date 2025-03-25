package chiyt.model;

import java.util.Random;

public enum State {
	NORMAL("Normal", 0) {
		@Override
		public void applyEffect(Role target) {
			// 什麼都不做
		}
	},
	INVINCIBLE("Invincible", 2) {
		@Override
		public void applyEffect(Role target) {
			// 受到攻擊時並不會有任何生命損失
		}
	},
	POISONED("Poisoned", 3) {
		@Override
		public void applyEffect(Role target) {
			// 每回合開始時失去15點生命值
			target.takeDamage(15); 
		}
	},
	ACCELERATED("Accelerated", 3) {
		@Override
		public void applyEffect(Role target) {
			// 每回合中可以進行「兩次動作」，若在期間遭受攻擊則立刻恢復至正常狀態
		}
	},
	HEALING("Healing", 5) {
		@Override
		public void applyEffect(Role target) {
			// 每回合開始時恢復30點生命值，直到滿血。若滿血則立刻恢復至正常狀態
			target.heal(30);
			if(target.getHp() == target.getMaxHp()) 
				target.setState(State.NORMAL);
		}
	},
	ORDERLESS("Orderless", 3) {
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
	STOCKPILE("Stockpile", 2) {
		@Override
		public void applyEffect(Role target) {
			// 兩回合後進入爆發狀態，若在期間遭受攻擊則立刻恢復至正常狀態
			// if (target.stateDuration == 0) {
			//	 target.state = ERUPTING;
			//	 target.stateDuration = ERUPTING.defaultDuration;
			// }
		}
	},
	ERUPTING("Erupting", 3) {
		@Override
		public void applyEffect(Role target) {
			// 角色的攻擊範圍擴充至「全地圖」，且攻擊行為變成「全場攻擊」：每一次攻擊時都會攻擊到地圖中所有其餘角色，且攻擊力為50。三回合過後取得瞬身狀態。
			// if (target.stateDuration == 0) {
			//	 target.state = TELEPORT;
			//	 target.stateDuration = TELEPORT.defaultDuration;
			// }
		}
	},
	TELEPORT("Teleport", 1) {
		@Override
		public void applyEffect(Role target) {
			// 一回合後角色的位置將被隨機移動至任一空地
			// Random r = new Random();
			// int newX, newY;
			// do {
			//	 newX = r.nextInt(10); // 假設地圖大小為 10x10
			//	 newY = r.nextInt(10);
			// } while (!target.map.isPositionValid(newX, newY)); // 假設有方法檢查位置是否有效
			// target.x = newX;
			// target.y = newY;
		}
	};

	private final String name;
	private final int duration;

	State(String name, int duration) {
		this.name = name;
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public int getDuration() {
		return duration;
	}

	// 每個狀態需要實現自己的效果
	public abstract void applyEffect(Role target);
}
package chiyt.model;

import java.util.Random;
import java.util.Scanner;

public enum State {
	NORMAL("Normal", 0) {
	},
	INVINCIBLE("Invincible", 2) {
		@Override
		public void handleDamage(Role target, int damage){
			// 受到攻擊時並不會有任何生命損失
			System.out.println(String.format("%s(%d,%d) is invincible and will not take damage!", target.getClass().getSimpleName(), target.getY(), target.getX()));	
		}
	},
	POISONED("Poisoned", 3) {
		@Override
		public void applyEffect(Role target) {
			// 每回合開始時失去15點生命值
			target.takeDamage(15);
			super.applyEffect(target);
		}
	},
	ACCELERATED("Accelerated", 3) {
		@Override
		public void handleDamage(Role target, int damage){
			// 若在期間遭受攻擊則立刻恢復至正常狀態
			System.out.println(String.format("%s(%d,%d) in ACCELERATED is attacked and will return to normal state!", target.getClass().getSimpleName(), target.getY(), target.getX()));
			target.setState(State.NORMAL);
		}

		@Override
		public void handleTurnAction(Role target){
			// 每回合中可以進行「兩次動作」
			for(int i = 0; i < 2; i++)
				super.handleTurnAction(target);
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
		@Override
		public void handleTurnAction(Role target){
			target.getMap().printMap(target.getX(), target.getY());
			String msg = "\u001B[42;37m" + target.getClass().getSimpleName() + "\u001B[0m HP: " 
							+ target.getHp() + ", State: " + target.getState().getName();
			if(!target.getState().equals(State.NORMAL))
				msg += (", Duration: " + target.getDuration());
	
			System.out.println(msg);
			
			//隨機決定只能上下移動，或左右移動
			boolean isVertical = true;
			msg = ""; 
			Random rand = new Random();
			
			if (rand.nextBoolean()) {
				// 只能上下移動
				isVertical = true;
				System.out.println("Can only move UP or DOWN.");
				msg = "Choose direction: U:↑, D:↓";
			} else {
				// 只能左右移動
				isVertical = false;
				System.out.println("Can only move LEFT or RIGHT.");
				msg = "Choose direction: L:←, R:→";
			}

			//
			if(target instanceof Character){
				System.out.println(msg);
				Scanner scanner = new Scanner(System.in);
			}
		}
	},
	STOCKPILE("Stockpile", 2) {
		@Override
		public void applyEffect(Role target) {
			// 兩回合後進入爆發狀態
			if (target.getDuration() == 0) {
				target.setState(State.ERUPTING);
			}
		}

		@Override
		public void handleDamage(Role target, int damage){
			// 若在期間遭受攻擊則立刻恢復至正常狀態
			System.out.println(String.format("%s(%d,%d) in STOCKPILE is attacked and will return to normal state!", target.getClass().getSimpleName(), target.getY(), target.getX()));
			target.setState(State.NORMAL);
		}
	},
	ERUPTING("Erupting", 3) {
		@Override
		public void applyEffect(Role target) {
			//三回合過後取得瞬身狀態。
			if (target.getDuration() == 0) {
				target.setState(State.TELEPORT);
			}
		}

		@Override
		public void handleAttack(Role attacker, Role target){
			// 角色的攻擊範圍擴充至「全地圖」，且攻擊行為變成「全場攻擊」：每一次攻擊時都會攻擊到地圖中所有其餘角色，且攻擊力為50。
			System.out.println(String.format("%s(%d,%d) attacks all roles in the map!", attacker.getClass().getSimpleName(), attacker.getY(), attacker.getX()));
			if(!attacker.equals(attacker.getMap().getPlayer()))
				attacker.getMap().getPlayer().takeDamage(50);
			
			for(Role monster : attacker.getMap().getMonsters()){
				if(attacker.equals(monster))
					continue;

				monster.takeDamage(50);
			}
		}
	},
	TELEPORT("Teleport", 1) {
		@Override
		public void applyEffect(Role target) {
			// 一回合後角色的位置將被隨機移動至任一空地
			if(target.getDuration()	== 0){
				Map map = target.getMap();
				map.placeObject(target);
			}
			super.applyEffect(target);
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

	public void handleDamage(Role target, int damage){
		int hp = Math.max(0, target.getHp() - damage);
		target.setHp(hp);
		if (hp == 0) {
			System.out.println(String.format("%s(%d,%d) dies!", target.getClass().getSimpleName(), target.getY(), target.getX()));
			target.getMap().removeObj(target);
		}
	}

	public void handleAttack(Role attacker, Role target){
		System.out.println(
			String.format("%s(%d,%d) attacks %s (%d,%d)", 
			attacker.getClass().getSimpleName(), attacker.getY(), attacker.getY(), 
			target.getClass().getSimpleName(), target.getY(), target.getX()
		));
		target.takeDamage(attacker.getAttackPower());
	}

	public void handleTurnAction(Role target){
		target.getMap().printMap(target.getX(), target.getY());
		String msg = "\u001B[42;37m" + target.getClass().getSimpleName() + "\u001B[0m HP: " 
						+ target.getHp() + ", State: " + target.getState().getName();
		if(!target.getState().equals(State.NORMAL))
			msg += (", Duration: " + target.getDuration());

		System.out.println(msg);
		target.executeTurnAction();
	}

	public void applyEffect(Role target){
		if (target.getDuration() == 0 && !this.equals(State.NORMAL)) {
			target.setState(State.NORMAL);
		}
	}
}
package chiyt.model.State;

import chiyt.model.Role;

import java.util.Random;
import java.util.Scanner;

import chiyt.model.Character;
import chiyt.model.Direction;
import chiyt.model.Monster;

public class OrderlessState extends State{

	public OrderlessState(Role target) {
		super(target, "Orderless", 3);
	}
	
	@Override
	public void handleDamage(int damage){
		super.handleDamage( damage);
		if (target.getHp() > 0) {
			if(target instanceof Character)
				target.setState(new InvincibleState(target));
		}
	}

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
	public void executeTurnAction(){
		target.getMap().printMap(target.getX(), target.getY());
		String msg = "\u001B[42;37m" + target.getClass().getSimpleName() + "\u001B[0m HP: " 
						+ target.getHp() + ", State: " + target.getState().getName();
		if(!target.getState().equals(new NormalState(target)))
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
		Direction nextDir = null;
		if(target instanceof Character){
			System.out.println(msg);
			Scanner scanner = new Scanner(System.in);
			boolean validInput = false;
			
			while (!validInput) {
				validInput = true;
				char dir = scanner.next().toUpperCase().charAt(0);
				if (isVertical && dir == 'U'){
					nextDir = Direction.UP;
				} else if (isVertical && dir == 'D') {
					nextDir = Direction.DOWN;
				} else if (!isVertical && dir == 'L') {
					nextDir = Direction.LEFT;
				} else if (!isVertical && dir == 'R') {
					nextDir = Direction.RIGHT;
				} else {
					System.out.println("Invalid input. " + msg);
					validInput = false;
				}
			}
		}
		else if (target instanceof Monster) {
			boolean validInput = false;
			while (!validInput) {
				validInput = true;
				int dir = rand.nextInt(4);
				if (isVertical && dir == 0){
					nextDir = Direction.UP;
				} else if (isVertical && dir == 1) {
					nextDir = Direction.DOWN;
				} else if (!isVertical && dir == 2) {
					nextDir = Direction.LEFT;
				} else if (!isVertical && dir == 3) {
					nextDir = Direction.RIGHT;
				} else {
					validInput = false;
				}
			}
		}
		target.move(nextDir);
	}
}

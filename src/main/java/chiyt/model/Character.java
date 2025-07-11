package chiyt.model;

import java.util.Random;
import java.util.Scanner;


public class Character extends Role {
	Direction direction;

	public Character(int x, int y, Map map) {
		this(x, y, Direction.values()[new Random().nextInt(Direction.values().length)], map);
	}

	public Character(int x, int y, Direction direction, Map map) {
		super(x, y, 300, map);
		this.direction = direction;
	}

	@Override
	public String getSymbol() {
		return direction.getSymbol();
	}

	@Override
	public String getSimpleName() {
		return "Character";
	}

	@Override
	public void move(Direction dir){
		this.direction = dir;
		super.move(dir);
	}

	@Override
	public void executeTurnAction() {
		System.out.println("Choose action: 1. Move (U/D/L/R) 2. Attack");
		Scanner scanner = new Scanner(System.in);
		char choice = scanner.next().toUpperCase().charAt(0);
		if (choice == '1') {
			Direction nextDir = null;
			boolean validInput = false;
			while (!validInput) {
				validInput = true;
				System.out.println("Choose direction: U:↑, D:↓, L:←, R:→");
				char dir = scanner.next().toUpperCase().charAt(0);
				if (dir == 'U'){
					nextDir = Direction.UP;
				} else if (dir == 'D') {
					nextDir = Direction.DOWN;
				} else if (dir == 'L') {
					nextDir = Direction.LEFT;
				} else if (dir == 'R') {
					nextDir = Direction.RIGHT;
				} else {
					System.out.println("Invalid input. Please enter U, D, L or R.");
					validInput = false;
				}
			}
			move(nextDir);
		} else {
			int dx = 0, dy = 0;
			switch (direction) {
				case UP: dy = -1; break;
				case DOWN: dy = 1; break;
				case LEFT: dx = -1; break;
				case RIGHT: dx = 1; break;
			}

			int tx = this.getX();
			int ty = this.getY();
			while (true) {
				tx += dx;
				ty += dy;
				try{
					MapObject obj = map.getObject(tx, ty);
					if (obj instanceof Obstacle) {
						System.out.println("Attack stopped: hit an obstacle.");
						break;
					}
					if (obj instanceof Monster) {
						Monster monster = (Monster)obj;
						attack(monster);
					}
				}catch(Exception e){
					break;
				} 
			}
		}
	}

	@Override
	public int getAttackPower() {
		return 100;
	}
}

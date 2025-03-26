package chiyt;

import chiyt.model.Map;
import chiyt.model.Monster;

public class Game {
	private Map map;
	

	public Game(Map map) {
		this.map = map;
	}

	public void startGame() {
		while (!endGame()) {
			map.getPlayer().playTurn();
			for(Monster m : map.getMonsters()) {
				if (m != null) {
					m.playTurn();
				}
			}
		}
	}

	public boolean endGame() {
		if(map.getPlayer().getHp() <= 0) {
			System.out.println("You Lose!");
			return true;
		}

		if(map.getMonsters().size() > 0)
			return false;

		System.out.println("You Win!");
		return true;
	}
}

package chiyt;

import java.util.ArrayList;
import java.util.List;

import chiyt.model.Character;
import chiyt.model.Map;
import chiyt.model.MapObject;
import chiyt.model.Monster;

public class Game {
    private Map map;
    private Character player;
    private List<Monster> monsters;

    public Game(Map map, List<MapObject> objs) {
        this.map = map;
        map.generateMap(objs);

        this.player = (Character) objs.get(0);
        this.monsters = new ArrayList<>();
        for (int i = 1; i < objs.size(); i++) {
            if (objs.get(i) instanceof Monster) {
                monsters.add((Monster) objs.get(i));
            }
        }
    }

    public void startGame() {
        while (!endGame()) {
            map.printMap();
            System.out.println("Player HP: " + player.getHp() + ", State: " + player.getState().getName());
            player.playTurn();
            for(Monster m : monsters) {
                if (m != null) {
                    m.playTurn();
                }
            }
        }
    }

    public boolean endGame() {
        if(player.getHp() <= 0) {
            System.out.println("You Lose!");
            return true;
        }

        for (Monster m : monsters) {
            if (m != null && m.getHp() > 0) 
                return false;
        }
        System.out.println("You Win!");
        return true;
    }
}

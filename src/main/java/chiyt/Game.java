package chiyt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import chiyt.model.Character;
import chiyt.model.Character.Direction;
import chiyt.model.Map;
import chiyt.model.MapObject;
import chiyt.model.Monster;
import chiyt.model.Treasure;

public class Game {
    private Map map;
    private Character player;
    private List<Monster> monsters;
    // private Treasure[] treasures;

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
            playerTurn();
        //     monsterTurn();
        //     player.updateState();
        //     for (Monster m : monsters) {
        //         if (m != null) m.updateState();
        //     }
        }
        
    }

    public void playerTurn() {
        System.out.println("Choose action: 1. Move (U/D/L/R) 2. Attack");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if (choice == 1) {
            Direction nextDir;
            while (true) {
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
                }
            }
            player.move(nextDir);
        } else {
            player.attack();
        }
    }

    // public void monsterTurn() {
    //     for(Monster m : monsters) {
    //         if (m != null) {
    //             m.move();
    //             if (m.canAttack(player)) {
    //                 m.attack(player);
    //             }
    //         }
    //     }
    // }

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

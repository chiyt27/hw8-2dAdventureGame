package chiyt.model;

import java.util.Random;

import chiyt.Game;

public class Monster extends Role {
    
    public Monster(int x, int y) {
        super(x, y, 1);
    }

    @Override
    public String getSymbol() {
        return "M";
    }

    // public void move() {
    //     Random rand = new Random();
    //     int dir = rand.nextInt(4);
    //     map[y][x] = '.';
    //     int newX = x, newY = y;
    //     switch (dir) {
    //         case 0: newY--; break;
    //         case 1: newY++; break;
    //         case 2: newX--; break;
    //         case 3: newX++; break;
    //     }
    //     if (newX >= 0 && newX < 10 && newY >= 0 && newY < 10 && map[newY][newX] != '□') {
    //         x = newX;
    //         y = newY;
    //         if (map[y][x] == 'x') {
    //             for (Treasure t : Game.this.treasures) {
    //                 if (t != null && t.x == x && t.y == y) {
    //                     touch(t);
    //                     break;
    //                 }
    //             }
    //         }
    //     }
    //     map[y][x] = 'M';
    // }

    // public void attack(Character target) {
    //     if (canAttack(target)) {
    //         target.hp -= 50;
    //         if (target.state == null || (!target.state.name.equals("Stockpile") && !target.state.name.equals("Accelerated"))) {
    //             target.state = new State("Invincible", 2);
    //             target.stateDuration = 2;
    //         }
    //     }
    // }

    // public boolean canAttack(Character target) {
    //     return (Math.abs(target.x - x) <= 1 && target.y == y) || (Math.abs(target.y - y) <= 1 && target.x == x);
    // }

    // public void touch(MapObject obj) {
    //     if (obj instanceof Treasure) {
    //         ((Treasure) obj).applyEffect(this);
    //         for (int i = 0; i < Game.this.treasures.length; i++) {
    //             if (Game.this.treasures[i] == obj) {
    //                 Game.this.treasures[i] = null;
    //                 break;
    //             }
    //         }
    //     }
    // }

    // public void updateState() {
    //     if (state != null) {
    //         state.applyEffect(this);
    //         stateDuration--;
    //         if (stateDuration <= 0) {
    //             state.removeEffect(this);
    //             state = null;
    //         }
    //     }
    // }

    
}

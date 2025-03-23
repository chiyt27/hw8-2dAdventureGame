package chiyt.model;

import java.util.Random;

import chiyt.Game;

public class Character extends Role {

    public enum Direction {
        UP("↑"), DOWN("↓"), LEFT("←"), RIGHT("→");

        String symbol;

        Direction(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }
    }

    Direction direction;

    public Character(int x, int y) {
        this(x, y, Direction.values()[new Random().nextInt(Direction.values().length)]);
    }

    public Character(int x, int y, Direction direction) {
        super(x, y, 300);
        this.direction = direction;
    }

    @Override
    public String getSymbol() {
        return direction.getSymbol();
    }

    public void move(String dir) {
        map[y][x] = '.';
        int newX = x, newY = y;
        switch (dir) {
            case 'U': newY--; direction = '↑'; break;
            case 'D': newY++; direction = '↓'; break;
            case 'L': newX--; direction = '←'; break;
            case 'R': newX++; direction = '→'; break;
        }
        if (newX >= 0 && newX < 10 && newY >= 0 && newY < 10 && map[newY][newX] != '□') {
            x = newX;
            y = newY;
            if (map[y][x] == 'x') {
                for (Treasure t : Game.this.treasures) {
                    if (t != null && t.x == x && t.y == y) {
                        touch(t);
                        break;
                    }
                }
            } else if (map[y][x] == 'M') {
                x = newX; y = newY; // Stay in place if touching monster
            }
        }
        map[y][x] = direction;
    }

    // public void attack() {
    //     int dx = 0, dy = 0;
    //     switch (direction) {
    //         case '↑': dy = -1; break;
    //         case '↓': dy = 1; break;
    //         case '←': dx = -1; break;
    //         case '→': dx = 1; break;
    //     }
    //     int tx = x, ty = y;
    //     while (tx >= 0 && tx < 10 && ty >= 0 && ty < 10 && map[ty][tx] != '□') {
    //         if (map[ty][tx] == 'M') {
    //             for (int i = 0; i < Game.this.monsters.length; i++) {
    //                 if (Game.this.monsters[i] != null && Game.this.monsters[i].x == tx && Game.this.monsters[i].y == ty) {
    //                     Game.this.monsters[i].hp = 0;
    //                     map[ty][tx] = '.';
    //                     Game.this.monsters[i] = null;
    //                 }
    //             }
    //         }
    //         tx += dx;
    //         ty += dy;
    //     }
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

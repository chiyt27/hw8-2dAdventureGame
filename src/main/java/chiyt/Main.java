package chiyt;

import java.util.ArrayList;
import java.util.List;

import chiyt.model.Character;
import chiyt.model.Map;
import chiyt.model.MapObject;
import chiyt.model.Monster;
import chiyt.model.Obstacle;
import chiyt.model.Treasure;

public class Main
{
    public static void main(String[] args) {
        int width = 10, height = 10;
        Map map = new Map(width, height);

        int monsterCount = 15, treasureCount = 10, obstacleCount = 20;
        List<MapObject> objs = new ArrayList<>();
        objs.add(new Character(0, 0, map));
        for (int i = 0; i < monsterCount; i++) {
            objs.add(new Monster(0, 0, map));
        }
        for (int i = 0; i < treasureCount; i++) {
            objs.add(new Treasure(0, 0));
        }
        for (int i = 0; i < obstacleCount; i++) {
            objs.add(new Obstacle(0, 0));
        }

        
        new Game(map, objs).startGame();
        
    }
}

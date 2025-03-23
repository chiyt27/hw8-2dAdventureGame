package chiyt.model;

import java.util.List;
import java.util.Random;

public class Map {
    private int width;
    private int height;
    private MapObject[][] grid;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new MapObject[height][width];
    }

    public void generateMap(List<MapObject> objs) {
        // 確保地圖不超過容量
        int maxObjects = width * height; // 預留主角位置
        int totalObjects = objs.size();
        if (totalObjects > maxObjects) {
            throw new IllegalArgumentException("Too many objects for the map size!");
        }
    
        // 隨機放置物件
        for(MapObject obj : objs) {
            placeObject(obj);
        }
    }

    private void placeObject(MapObject obj) {
        Random rand = new Random();
        int x, y;
    
        do {
            x = rand.nextInt(width);
            y = rand.nextInt(height);
        } while (grid[y][x] != null); // 確保該位置沒有物件
    
        grid[y][x] = obj;
        obj.setPosition(x, y);
    }

    public void printMap() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(
                    String.format(" %s ", grid[i][j] == null ? "." : grid[i][j].getSymbol()));
            }
            System.out.println();
        }
    }
}

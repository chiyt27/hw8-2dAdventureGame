package chiyt.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
	private int width;
	private int height;
	private MapObject[][] grid;
	private Character player;
	private List<Monster> monsters;


	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		this.grid = new MapObject[height][width];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isValidPosition(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	public void generateMap(List<MapObject> objs) {
		// 確保物件不超過地圖容量
		int maxObjects = width * height;
		int totalObjects = objs.size();
		if (totalObjects > maxObjects) {
			throw new IllegalArgumentException("Too many objects for the map size!");
		}

		// 隨機放置物件
		for(MapObject obj : objs) {
			if(obj instanceof Character) {
				this.player = (Character) obj;
			} else if(obj instanceof Monster) {
				monsters.add((Monster) obj);
			}
			randomPlaceObject(obj);
		}
	}

	public void randomPlaceObject(MapObject obj) {
		Random rand = new Random();
		int x, y;
	
		do {
			x = rand.nextInt(width);
			y = rand.nextInt(height);
		} while (grid[y][x] != null); // 確保該位置沒有物件
	
		grid[y][x] = obj;
		obj.setPosition(x, y);
	}

	public Character getPlayer(){
		return player;
	}

	public List<Monster> getMonsters(){
		return monsters;
	}

	public void moveObject(MapObject obj, int x, int y) {
		// 檢查有沒有超出範圍
		if(!isValidPosition(x,y)) {
			System.out.println("Out of map range!");
			return;
		}

		// 檢查該位置有沒有物件
		if(grid[y][x] != null) {
			if(!obj.touch(grid[y][x])) {//碰撞後決定是否移動到新位置
				return;
			}
		}
		
		// 移動物件
		System.out.println(
			String.format("%s(%d,%d) moves to (%d,%d)", 
			obj.getClass().getSimpleName(), obj.getY(), obj.getX(), y, x
		));
		grid[obj.getY()][obj.getX()] = null;
		grid[y][x] = obj;
		obj.setPosition(x, y);
	}

	public void printMap() {
		printMap(-1, -1);
	}

	public void printMap(int x, int y) {
		// for (int i = 0; i < height; i++) {
		// 	for (int j = 0; j < width; j++) {
		// 		System.out.print(
		// 			String.format(" %s ", grid[i][j] == null ? "." : grid[i][j].getSymbol()));
		// 	}
		// 	System.out.println();
		// }
		// 打印列座標
		System.out.print(String.format("%4s", "\\"));
		for (int j = 0; j < width; j++) {
			System.out.print(String.format("%4d", j));
		}
		System.out.println();
	
		// 打印地圖內容和行座標
		for (int i = 0; i < height; i++) {
			System.out.print(String.format("%4d", i)); // 打印行座標
			for (int j = 0; j < width; j++) {
				String symbol = grid[i][j] == null ? "." : grid[i][j].getSymbol();
				if(i==y && j==x){
					symbol = "\u001B[42;37m" + symbol + "\u001B[0m";
				}
				System.out.print(
					String.format("   %s", symbol));
			}
			System.out.println();
		}
	}

	public MapObject getObject(int x, int y) {
		if(!isValidPosition(x,y)) {
			throw new IllegalArgumentException("Failed to retrieve the object: Out of map range!");
		}
		return grid[y][x];
	}

	public void removeObj(MapObject obj) {
		grid[obj.getY()][obj.getX()] = null;

		if(obj instanceof Monster){
			monsters.remove(obj);
		}
	}
}

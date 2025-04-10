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
		this.monsters = new ArrayList<>();
	}

	public Character getPlayer(){
		return player;
	}

	public List<Monster> getMonsters(){
		return monsters;
	}

	private boolean isValidPosition(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}

	public void addObjectsToMap(List<MapObject> objs) {
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

	/***
	 * 隨機放置物件
	 * @param obj
	 */
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

	/***
	 * 移動物件，會把原本位置的物件設為null
	 * @param mover
	 * @param newX
	 * @param newY
	 */
	public void moveObject(MapObject mover, int newX, int newY) {
		// 檢查有沒有超出範圍
		if(!isValidPosition(newX, newY)) {
			System.out.println("Failed to move: Out of map range. Movement aborted.");
			return;
		}

		// 檢查該位置有沒有物件，如果有的話 進行碰撞處理
		Object target = grid[newY][newX];
		if(target != null && target instanceof MapObject) {
			if(!((MapObject)target).touch(mover)) {//碰撞後決定是否移動到新位置
				return;
			}
		}
		
		// 移動物件
		System.out.println(
			String.format("%s(%d,%d) moves to (%d,%d)", 
			mover.getSimpleName(), mover.getY(), mover.getX(), newY, newX
		));
		grid[mover.getY()][mover.getX()] = null;
		grid[newY][newX] = mover;
		mover.setPosition(newX, newY);
	}

	public void printMap(MapObject obj) {
		// for (int i = 0; i < height; i++) {
		// 	for (int j = 0; j < width; j++) {
		// 		System.out.print(
		// 			String.format(" %s ", grid[i][j] == null ? "." : grid[i][j].getSymbol()));
		// 	}
		// 	System.out.println();
		// }

		int x = obj.getX(), y = obj.getY();
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

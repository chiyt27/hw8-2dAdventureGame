public class Map {
    private MapObject[][] map;
    private int width;
    private int height;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        map = new MapObject[height][width];
        initializeMap();
    }

    private void initializeMap() {
        // 初始化障礙物
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (Math.random() < 0.2) { // 20%的機率生成障礙物
                    map[i][j] = new Obstacle(j, i);
                }
            }
        }
    }

    public void setObject(int x, int y, MapObject object) {
        if (isValidPosition(x, y)) {
            map[y][x] = object;
        }
    }

    public MapObject getObject(int x, int y) {
        if (isValidPosition(x, y)) {
            return map[y][x];
        }
        return null;
    }

    public void removeObject(int x, int y) {
        if (isValidPosition(x, y)) {
            map[y][x] = null;
        }
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void display() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                MapObject obj = map[i][j];
                if (obj == null) {
                    System.out.print("  ");
                } else {
                    System.out.print(obj.getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }
} 
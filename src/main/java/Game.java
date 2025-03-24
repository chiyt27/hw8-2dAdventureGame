import java.util.Scanner;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import states.*;

public class Game {
    private Map map;
    private Character player;
    private List<Monster> monsters;
    private List<Treasure> treasures;
    private Scanner scanner;
    private Random random;
    private boolean isGameOver;

    public Game() {
        map = new Map(10, 10); // 10x10的地圖
        monsters = new ArrayList<>();
        treasures = new ArrayList<>();
        scanner = new Scanner(System.in);
        random = new Random();
        isGameOver = false;
        
        // 初始化玩家
        initializePlayer();
        
        // 初始化怪物和寶物
        initializeMonstersAndTreasures();
    }

    private void initializePlayer() {
        int x, y;
        do {
            x = random.nextInt(map.getWidth());
            y = random.nextInt(map.getHeight());
        } while (!map.isValidPosition(x, y));
        
        Direction[] directions = Direction.values();
        Direction initialDirection = directions[random.nextInt(directions.length)];
        player = new Character(x, y, initialDirection);
        map.setObject(x, y, player);
    }

    private void initializeMonstersAndTreasures() {
        // 初始化5個怪物
        for (int i = 0; i < 5; i++) {
            spawnMonster();
        }
        
        // 初始化10個寶物
        for (int i = 0; i < 10; i++) {
            spawnTreasure();
        }
    }

    private void spawnMonster() {
        int x, y;
        do {
            x = random.nextInt(map.getWidth());
            y = random.nextInt(map.getHeight());
        } while (!map.isValidPosition(x, y));
        
        Monster monster = new Monster(x, y);
        monsters.add(monster);
        map.setObject(x, y, monster);
    }

    private void spawnTreasure() {
        int x, y;
        do {
            x = random.nextInt(map.getWidth());
            y = random.nextInt(map.getHeight());
        } while (!map.isValidPosition(x, y));
        
        Treasure treasure = Treasure.generateRandomTreasure(x, y);
        treasures.add(treasure);
        map.setObject(x, y, treasure);
    }

    public void start() {
        while (!isGameOver) {
            // 顯示遊戲狀態
            displayGameState();
            
            // 玩家回合
            playerTurn();
            
            // 檢查遊戲是否結束
            if (monsters.isEmpty()) {
                System.out.println("恭喜你贏得遊戲！");
                isGameOver = true;
                continue;
            }
            
            // 怪物回合
            monsterTurn();
            
            // 檢查玩家是否死亡
            if (player.getHp() <= 0) {
                System.out.println("遊戲結束！你輸了！");
                isGameOver = true;
            }
            
            // 隨機生成新的怪物和寶物
            if (random.nextDouble() < 0.1) { // 10%機率生成新怪物
                spawnMonster();
            }
            if (random.nextDouble() < 0.1) { // 10%機率生成新寶物
                spawnTreasure();
            }
        }
    }

    private void displayGameState() {
        System.out.println("\n當前地圖：");
        map.display();
        System.out.println("玩家生命值：" + player.getHp());
        System.out.println("玩家狀態：" + player.getCurrentState().getClass().getSimpleName());
    }

    private void playerTurn() {
        System.out.println("\n請選擇動作：");
        System.out.println("1. 移動");
        System.out.println("2. 攻擊");
        
        int choice = scanner.nextInt();
        
        if (choice == 1) {
            System.out.println("請選擇方向：");
            System.out.println("1. 上");
            System.out.println("2. 右");
            System.out.println("3. 下");
            System.out.println("4. 左");
            
            int directionChoice = scanner.nextInt();
            Direction direction = Direction.values()[directionChoice - 1];
            player.move(direction, map);
        } else if (choice == 2) {
            player.attack(map);
        }
    }

    private void monsterTurn() {
        for (Monster monster : new ArrayList<>(monsters)) {
            if (monster.isDead()) {
                monsters.remove(monster);
                map.removeObject(monster.getX(), monster.getY());
                continue;
            }
            
            monster.takeTurn(map, player);
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
} 
package commands;

import Map;
import MapObject;
import Direction;
import Treasure;

public class MoveCommand {
    private Map map;
    private MapObject object;
    private Direction direction;

    public MoveCommand(Map map, MapObject object, Direction direction) {
        this.map = map;
        this.object = object;
        this.direction = direction;
    }

    public boolean execute() {
        int newX = object.getX() + direction.getDx();
        int newY = object.getY() + direction.getDy();

        // 檢查是否超出邊界
        if (!map.isValidPosition(newX, newY)) {
            return false;
        }

        // 檢查是否碰到障礙物
        MapObject target = map.getObject(newX, newY);
        if (target != null && !(target instanceof Treasure)) {
            return false;
        }

        // 移動物件
        map.removeObject(object.getX(), object.getY());
        object.setX(newX);
        object.setY(newY);
        map.setObject(newX, newY, object);

        // 如果碰到寶藏，觸發效果
        if (target instanceof Treasure) {
            ((Treasure) target).applyEffect(object);
        }

        return true;
    }
} 
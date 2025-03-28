package chiyt.model.strategy;

import chiyt.model.Direction;
import chiyt.model.Map;
import chiyt.model.Role;
import chiyt.model.Treasure;

public class OrderlessMoveStrategy implements MoveStrategy {
    private final boolean isVertical;

    public OrderlessMoveStrategy(boolean isVertical) {
        this.isVertical = isVertical;
    }

    @Override
    public boolean move(Role role, Direction dir) {
        // 檢查移動方向是否符合限制
        if (isVertical && (dir == Direction.LEFT || dir == Direction.RIGHT)) {
            return false;
        }
        if (!isVertical && (dir == Direction.UP || dir == Direction.DOWN)) {
            return false;
        }

		Map map = role.getMap();
        int newX = role.getX(), newY = role.getY();
		if(dir.equals(Direction.UP))
			newY -= 1;
		else if (dir.equals(Direction.DOWN)) 
			newY += 1;
		else if (dir.equals(Direction.RIGHT)) 
			newX += 1;
		else if (dir.equals(Direction.LEFT)) 
			newX -= 1;

        if (!map.isValidPosition(newX, newY)) {
            return false;
        }

        if (!map.isValidPosition(newX, newY)) {
            return false;
        }

        var target = map.getObject(newX, newY);
        if (target != null && !(target instanceof Treasure)) {
            return false;
        }

        map.moveObject(role, newX, newY);

        // if (target instanceof Treasure) {
        //     ((Treasure) target).applyEffect(role);
        // }

        return true;
    }
}

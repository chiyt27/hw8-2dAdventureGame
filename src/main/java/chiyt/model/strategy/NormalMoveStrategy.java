package chiyt.model.strategy;

import chiyt.model.Direction;
import chiyt.model.Map;
import chiyt.model.Role;
import chiyt.model.Treasure;

public class NormalMoveStrategy implements MoveStrategy {
    @Override
    public boolean move(Role role, Direction dir, Map map) {
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

        var target = map.getObject(newX, newY);
        if (target != null && !(target instanceof Treasure)) {
            return false;
        }

        map.removeObject(role.getX(), role.getY());
        map.moveRole(role, newX, newY);

        if (target instanceof Treasure) {
            ((Treasure) target).applyEffect(role);
        }

        return true;
    }
}

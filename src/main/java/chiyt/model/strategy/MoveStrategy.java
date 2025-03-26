package chiyt.model.strategy;

import chiyt.model.Direction;
import chiyt.model.Map;
import chiyt.model.Role;

public interface MoveStrategy {
    boolean move(Role role, Direction direction, Map map);
}
package com.eternalcode.lobby.configuration.composer;

import panda.std.Result;
import com.eternalcode.lobby.position.Position;

public class PositionComposer implements SimpleComposer<Position> {

    @Override
    public Result<Position, Exception> deserialize(String source) {
        return Result.supplyThrowing(IllegalArgumentException.class, () -> Position.parse(source));
    }

    @Override
    public Result<String, Exception> serialize(Position entity) {
        return Result.ok(entity.toString());
    }
}

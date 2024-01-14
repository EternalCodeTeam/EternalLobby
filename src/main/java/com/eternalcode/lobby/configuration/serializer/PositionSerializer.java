package com.eternalcode.lobby.configuration.serializer;

import com.eternalcode.lobby.position.Position;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.jetbrains.annotations.NotNull;

public class PositionSerializer implements ObjectSerializer<Position> {

    @Override
    public boolean supports(@NotNull Class<? super Position> type) {
        return Position.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull Position position, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.setValue(position.toString());
    }

    @Override
    public Position deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        return Position.parse(data.getValue(String.class));
    }
}
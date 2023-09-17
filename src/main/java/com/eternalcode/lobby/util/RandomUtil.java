package com.eternalcode.lobby.util;

import panda.std.Option;
import panda.std.stream.PandaStream;

import java.util.Collection;
import java.util.Random;

public final class RandomUtil {

    private static final Random RANDOM = new Random();

    private RandomUtil() {
        throw new UnsupportedOperationException("This class is not meant to be instantiated");
    }

    public static <T> Option<T> randomElement(Collection<T> collection) {
        if (collection.isEmpty()) {
            return Option.none();
        }

        return PandaStream.of(collection)
            .skip(RANDOM.nextInt(collection.size()))
            .head();
    }
}

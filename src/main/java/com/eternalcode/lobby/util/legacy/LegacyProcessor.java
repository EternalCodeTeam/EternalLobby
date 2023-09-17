package com.eternalcode.lobby.util.legacy;

import net.kyori.adventure.text.Component;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class LegacyProcessor implements UnaryOperator<Component> {

    @Override
    public Component apply(Component component) {
        return Legacy.RESET_ITEM.append(component.replaceText(builder -> builder.match(Pattern.compile(".*"))
            .replacement((matchResult, builder1) -> Legacy.component(matchResult.group()))));
    }

}

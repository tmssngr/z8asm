package com.syntevo.z8asm;

import org.jetbrains.annotations.*;

/**
 * @author Thomas Singer
 */
public record Token(@NotNull TokenType type, @NotNull String text, int intValue, int size, @NotNull Location location) {
}

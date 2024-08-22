package com.sanyavertolet.interview;

/**
 * A record that encapsulates all the configuration settings for an application.
 *
 * @param isDebug a flag that defines if the debug panel should be shown. When set to {@code true},
 *                the debug panel will be displayed; otherwise, it will be hidden.
 */
public record Configuration(boolean isDebug) { }

package com.jumia.skylens.telemetry.api;

import com.jumia.skylens.telemetry.api.tags.MeterTag;
import com.jumia.skylens.telemetry.api.types.TimerType;

import java.util.Set;
import java.util.function.Supplier;

public interface TimerRecorder {

    /**
     * Records the duration of a task.
     *
     * @param timerType the type of timer
     * @param tags      the tags to associate with the timer
     * @param task      the task to time
     * @param <T>       the return type of the task
     * @return the result of the task
     */
    <T> T record(TimerType timerType, Set<MeterTag> tags, Supplier<T> task);

    /**
     * Records the duration of a task that doesn't return a value.
     *
     * @param timerType the type of timer
     * @param tags      the tags to associate with the timer
     * @param task      the task to time
     */
    void record(TimerType timerType, Set<MeterTag> tags, Runnable task);

    /**
     * Records an already measured duration.
     *
     * @param timerType      the type of timer
     * @param tags           the tags to associate with the timer
     * @param durationMillis the duration in milliseconds
     */
    void recordDuration(TimerType timerType, Set<MeterTag> tags, long durationMillis);
}

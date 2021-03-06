/*
 * The MIT License (MIT)
 * Copyright (c) 2018 Microsoft Corporation
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.microsoft.azure.cosmosdb.internal;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.collect.ImmutableList;
import com.microsoft.azure.cosmosdb.internal.directconnectivity.rntbd.RntbdObjectMapper;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Iterator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents the time and duration of important events in the lifetime of a request.
 * <p>
 * A {@link RequestTimeline} represents a timeline as a sequence of {@link Event} instances with name, time, and
 * duration properties. Hence, one might use this class to represent any timeline.
 * </p>
 * A {@link RequestTimeline} serializes to JSON as an array of {@link Event} instances. This is the default
 * serialization for any class that implements {@link Iterable}.
 * <p>
 * <b>Example:</b>
 * <pre>{@code OffsetDateTime startTime = OffsetDateTime.parse("2020-01-07T11:24:12.842749-08:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME);
 * sys.out.println(RequestTimeline.of(
 *     new RequestTimeline.Event("foo", startTime, startTime.plusSeconds(1)),
 *     new RequestTimeline.Event("bar", startTime.plusSeconds(1), startTime.plusSeconds(2))));}</pre>
 * JSON serialization:
 * <pre>{@code [{"name":"foo","time":"2020-01-07T11:24:12.842749-08:00","duration":"PT1S"},{"name":"bar","time":"2020-01-07T11:24:13.842749-08:00","duration":"PT1S"}])}</pre>
 */
public final class RequestTimeline implements Iterable<RequestTimeline.Event> {

    private static final RequestTimeline EMPTY = new RequestTimeline();
    private final ImmutableList<Event> events;

    private RequestTimeline() {
        this.events = ImmutableList.of();
    }

    private RequestTimeline(final ImmutableList<Event> events) {
        checkNotNull(events, "expected non-null events");
        this.events = events;
    }

    /**
     * Returns an empty {@link RequestTimeline}.
     *
     * The empty time line returned is static.
     *
     * @return an empty {@link RequestTimeline}.
     */
    public static RequestTimeline empty() {
        return EMPTY;
    }

    /**
     * Returns an iterator for enumerating the {@link Event} instances in this {@link RequestTimeline}.
     *
     * @return an iterator for enumerating the {@link Event} instances in this {@link RequestTimeline}.
     */
    @Override
    public Iterator<Event> iterator() {
        return this.events.iterator();
    }

    /**
     * Returns an empty {@link RequestTimeline}.
     *
     * The empty time line returned is static and equivalent to calling {@link RequestTimeline#empty}.
     *
     * @return an empty request timeline.
     */
    public static RequestTimeline of() {
        return EMPTY;
    }

    /**
     * Returns a new {@link RequestTimeline} with a single event.
     *
     * @return a new {@link RequestTimeline} with a single event.
     */
    public static RequestTimeline of(final Event event) {
        return new RequestTimeline(ImmutableList.of(event));
    }

    /**
     * Returns a new {@link RequestTimeline} with a pair of events.
     *
     * @return a new {@link RequestTimeline} with a pair of events.
     */
    public static RequestTimeline of(final Event e1, final Event e2) {
        return new RequestTimeline(ImmutableList.of(e1, e2));
    }

    /**
     * Returns a new {@link RequestTimeline} with three events.
     *
     * @return a new {@link RequestTimeline} with three events.
     */
    public static RequestTimeline of(final Event e1, final Event e2, final Event e3) {
        return new RequestTimeline(ImmutableList.of(e1, e2, e3));
    }

    /**
     * Returns a new {@link RequestTimeline} with four events.
     *
     * @return a new {@link RequestTimeline} with four events.
     */
    public static RequestTimeline of(final Event e1, final Event e2, final Event e3, final Event e4) {
        return new RequestTimeline(ImmutableList.of(e1, e2, e3, e4));
    }

    /**
     * Returns a new {@link RequestTimeline} with five events.
     *
     * @return a new {@link RequestTimeline} with five events.
     */
    public static RequestTimeline of(final Event e1, final Event e2, final Event e3, final Event e4, final Event e5) {
        return new RequestTimeline(ImmutableList.of(e1, e2, e3, e4, e5));
    }

    /**
     * Returns a new {@link RequestTimeline} with an arbitrary number of events.
     *
     * @return a new {@link RequestTimeline} with an arbitrary number of events.
     */
    public static RequestTimeline of(final Event... events) {
        return new RequestTimeline(ImmutableList.copyOf(events));
    }

    /**
     * Returns a textual representation of this {@link RequestTimeline}.
     * <p>
     * The textual representation returned is a string of the form {@code RequestTimeline(}<i> &lt;event-array&gt;</i>
     * {@code )}.
     */
    @Override
    public String toString() {
        return RntbdObjectMapper.toString(this);
    }

    @JsonPropertyOrder({ "name", "time", "duration" })
    public static final class Event {

        @JsonSerialize(using = ToStringSerializer.class)
        private final Duration duration;

        private final String name;

        @JsonSerialize(using = ToStringSerializer.class)
        private final OffsetDateTime time;

        public Event(final String name, final OffsetDateTime from, final OffsetDateTime to) {

            checkNotNull(name, "expected non-null name");

            this.name = name;
            this.time = from;

            this.duration = from == null ? null : to == null ? Duration.ZERO : Duration.between(from, to);
        }

        public Duration getDuration() {
            return this.duration;
        }

        public String getName() {
            return name;
        }

        public OffsetDateTime getTime() {
            return time;
        }
    }
}

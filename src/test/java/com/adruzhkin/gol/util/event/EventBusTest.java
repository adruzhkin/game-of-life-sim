package com.adruzhkin.gol.util.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventBusTest {

    private EventBus eventBus;

    @BeforeEach
    void setUp() {
        this.eventBus = new EventBus();
    }

    @Test
    void singleListenerCalled_oneEvent() {
        HelloEvent helloEvent = new HelloEvent();
        TestEventListener<HelloEvent> listener = new TestEventListener<>();

        eventBus.listenFor(HelloEvent.class, listener);
        eventBus.emit(helloEvent);

        assertTrue(listener.listenerCalled);
    }

    @Test
    void multipleListenersCalled_oneEvent() {
        HelloEvent helloEvent = new HelloEvent();
        TestEventListener<HelloEvent> listener1 = new TestEventListener<>();
        TestEventListener<HelloEvent> listener2 = new TestEventListener<>();

        eventBus.listenFor(HelloEvent.class, listener1);
        eventBus.listenFor(HelloEvent.class, listener2);

        eventBus.emit(helloEvent);

        assertTrue(listener1.listenerCalled);
        assertTrue(listener2.listenerCalled);
    }

    @Test
    void multipleListenersCalled_multipleEvents() {
        HelloEvent helloEvent = new HelloEvent();
        WorldEvent worldEvent = new WorldEvent();
        TestEventListener<HelloEvent> helloListener = new TestEventListener<>();
        TestEventListener<WorldEvent> worldListener = new TestEventListener<>();

        eventBus.listenFor(HelloEvent.class, helloListener);
        eventBus.listenFor(WorldEvent.class, worldListener);

        eventBus.emit(helloEvent);
        eventBus.emit(worldEvent);

        assertTrue(helloListener.listenerCalled);
        assertTrue(worldListener.listenerCalled);
    }

    private static class TestEventListener<T extends Event> implements EventListener<T> {

        private boolean listenerCalled;

        @Override
        public void handle(T event) {
            listenerCalled = true;
        }
    }

    private static class HelloEvent implements Event {
        private String value = "Hello";
    }

    private static class WorldEvent implements Event {
        private String value = "World";
    }

}
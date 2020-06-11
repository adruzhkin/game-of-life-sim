package com.adruzhkin.gol.util;

import com.adruzhkin.gol.viewmodel.SimpleChangeListener;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {

    @Test
    void constructWithInitialValue() {
        Property<String> stringProperty = new Property<>("Hello World");

        assertEquals("Hello World", stringProperty.get());
    }

    @Test
    void constructWithNoInitialValue() {
        Property<String> stringProperty = new Property<>();

        assertNull(stringProperty.get());
    }

    @Test
    void notifiesListener() {
        DoubleListener listener = new DoubleListener();
        Property<Double> doubleProperty = new Property<>(1.0);
        doubleProperty.listen(listener);
        doubleProperty.set(2.0);

        assertTrue(listener.notified);
        assertEquals(2.0, listener.value);
        assertEquals(2.0, doubleProperty.get());
    }

    private class DoubleListener implements SimpleChangeListener<Double> {
        private boolean notified = false;
        private double value;

        @Override
        public void valueChanged(Double value) {
            this.value = value;
            notified = true;
        }
    }
}
package com.adruzhkin.gol.util;

import com.adruzhkin.gol.viewmodel.SimpleChangeListener;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {

    @Test
    void constructWithInitialValue() {
        Property<String> stringProperty = new Property<>("Hello World");

        assertEquals("Hello World", stringProperty.getValue());
    }

    @Test
    void constructWithNoInitialValue() {
        Property<String> stringProperty = new Property<>();

        assertNull(stringProperty.getValue());
    }

    @Test
    void notifiesListener() {
        DoubleListener listener = new DoubleListener();
        Property<Double> doubleProperty = new Property<>(1.0);
        doubleProperty.listen(listener);
        doubleProperty.setValue(2.0);

        assertTrue(listener.notified);
        assertEquals(2.0, listener.value);
        assertEquals(2.0, doubleProperty.getValue());
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
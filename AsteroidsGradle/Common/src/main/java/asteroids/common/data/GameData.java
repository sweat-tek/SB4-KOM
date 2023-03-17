package asteroids.common.data;

import asteroids.common.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A simple data object, containing all the game data, that doesn't fit into the World.
 */
public class GameData {

    private final GameKeys keys = new GameKeys();
    private final List<Event> events = new CopyOnWriteArrayList<>();
    private float deltaTime;
    private int displayWidth;
    private int displayHeight;

    /**
     * Get the number of milliseconds between frames.
     */
    public float getDeltaTime() {
        return deltaTime;
    }

    /**
     * Update the deltaTime i.e. the time in milliseconds between frames.
     */
    public void setDeltaTime(float deltaTime) {
        this.deltaTime = deltaTime;
    }

    /**
     * Get the width of the window in pixels.
     */
    public int getDisplayWidth() {
        return displayWidth;
    }

    /**
     * Set the width of the window in pixels.
     */
    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    /**
     * Get the height of the window in pixels.
     */
    public int getDisplayHeight() {
        return displayHeight;
    }

    /**
     * Set the height of the window in pixels.
     */
    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    /**
     * Return the game keys.
     */
    public GameKeys getKeys() {
        return keys;
    }

    /**
     * Add a new event to the event handler.
     */
    public void addEvent(Event e) {
        events.add(e);
    }

    /**
     * Remove an event from the event handler.
     */
    public void removeEvent(Event e) {
        events.remove(e);
    }

    /**
     * Get a list of all the events.
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Get a list of events with a specific event type and source id.
     */
    public <E extends Event> List<Event> getEvents(Class<E> type, String sourceID) {
        List<Event> r = new ArrayList<>();
        for (Event event : events) {
            if (event.getClass().equals(type) && event.getSource().getID().equals(sourceID)) {
                r.add(event);
            }
        }

        return r;
    }
}

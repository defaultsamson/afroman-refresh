package api.resource;

import java.util.ArrayList;
import java.util.List;

public class Holder<T> implements IDisposable {
    private List<T> entries;

    /**
     * A holder for entries of a defined class type.
     */
    public Holder() {
        entries = new ArrayList<T>();
    }

    /**
     * @return the entries in this.
     */
    public List<T> getEntries() {
        return entries;
    }

    /**
     * @return if there's no entries in this.
     */
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    /**
     * Removes an entry from this.
     *
     * @param entry the entry to remove
     */
    public void unregisterEntry(T entry) {
        entries.remove(entry);
    }

    /**
     * Adds an entry to this.
     *
     * @param entry the entry to add
     */
    public void registerEntry(T entry) {
        entries.add(entry);
    }

    /**
     * Disposes of this.
     */
    @Override
    public void dispose() {
        for (T entry : entries) {
            if (entry instanceof IDisposable) {
                ((IDisposable) entry).dispose();
            }
        }

        entries.clear();
    }
}

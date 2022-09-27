package model.datahandling;


import model.Date;

import java.util.HashMap;

/**
 * A class that extends {@link HashMap} but treats Keys of the type {@link Date} differently.
 *
 * @param <K> The parametrized type of the Keys.
 * @param <V> The parametrized type of the Values.
 */
public class DateHashMap<K, V> extends HashMap<K, V> {

    /**
     * An implementation of the {@link HashMap} method {@link HashMap#get(Object)} that treats Keys of type {@link Date}
     * differently and calls {@link HashMap#get(Object)} on objects of all other types. Keys of type {@link Date}
     * returns their corresponding Value not if the reference-value is the same, but if the Date they represent is the
     * same.
     *
     * @param key the key whose associated value is to be returned
     * @return Returns the Value of the given Key.
     */
    @Override
    public V get(Object key) {
        if (key instanceof Date)
            for (K k : this.keySet())
                if (((Date) key).isEqualTo((Date) k))
                    return super.get(k);
        return super.get(key);
    }

    /**
     *  An implementation of the {@link HashMap} method {@link HashMap#containsKey(Object)} that treats Keys of type
     *  {@link Date} differently and calls {@link HashMap#get(Object)} on objects of all other types. Keys of type
     *  {@link Date} return true not if the reference-value is the same, but if the Date they represent is the same.
     *
     * @param key   The key whose presence in this map is to be tested.
     * @return The {@link Boolean} value of the comparison.
     */
    @Override
    public boolean containsKey(Object key) {
        if (key instanceof Date)
            for (K k : this.keySet())
                if (((Date) key).isEqualTo((Date) k))
                    return true;
        return super.containsKey(key);
    }
}

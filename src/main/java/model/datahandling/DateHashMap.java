package model.datahandling;


import model.Date;

import java.util.HashMap;

public class DateHashMap<K,V> extends HashMap<K,V> {

    @Override
    public V get(Object key) {
        if (key instanceof Date)
            for (K k : this.keySet())
                if (((Date) key).isEqualTo((Date) k))
                    return super.get(k);
        return super.get(key);
    }
}

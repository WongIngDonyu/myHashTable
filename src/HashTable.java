import java.util.Iterator;
import java.util.LinkedList;

public class HashTable <K, V> implements Iterable<KeyValue<K, V>>{
    private static final int INITIAL_CAPACITY =16;
    private static final double LOAD_FACTOR =0.8d;
    private LinkedList<KeyValue<K, V>>[] slots;
    private int count;
    private int capacity;
    public HashTable() {
        this(INITIAL_CAPACITY);
    }

    public HashTable(int capacity) {
        this.capacity=capacity;
        this.slots=new LinkedList[capacity];
        for (int i=0;i<capacity;i++){
            slots[i]=new LinkedList<>();
        }
    }

    public int countCollisions(){
        int collisions=0;
        for(LinkedList<KeyValue<K,V>>slot:slots){
            if(slot.size()>1){
                collisions+=slot.size()-1;
            }
        }
        return collisions;
    }

    public void printCollisions() {
        for (int i = 0; i < capacity; i++) {
            LinkedList<KeyValue<K, V>> slot = slots[i];
            if (slot.size() > 1) {
                System.out.println("Колизия в слоте " + i + ":");
                for (KeyValue<K, V> entry : slot) {
                    System.out.println(" Ключ " + entry.getKey() + " -> Значение " + entry.getValue());
                }
            }
        }
    }

    public void add(K key, V value) {
        growIfNeeded();
        int slotNumber=findSlotNumber(key);
        LinkedList<KeyValue<K,V>> slot = slots[slotNumber];
        slot.add(new KeyValue<>(key, value));
        count++;
    }

    private int findSlotNumber(K key) {
        return Math.abs(key.hashCode()) % this.slots.length;
    }

    private void growIfNeeded() {
        if((double) (this.size()+1) / this.capacity()>LOAD_FACTOR){
            this.grow();
        }
    }

    private void grow() {
        int newCapacity = this.capacity*2;
        LinkedList<KeyValue<K, V>> [] newSlots = new LinkedList[newCapacity];
        for(int i=0;i<newCapacity;i++){
            newSlots[i]=new LinkedList<>();
        }
        for(LinkedList<KeyValue<K, V>> slot: slots){
            for(KeyValue<K,V>entry:slot){
                int newSlotNumber = Math.abs(entry.getKey().hashCode()) % newCapacity;
                newSlots[newSlotNumber].add(entry);
            }
        }
        this.capacity=newCapacity;
        this.slots=newSlots;
    }

    public int size() {
        return this.count;
    }

    public int capacity() {
        return this.capacity;
    }

    public boolean addOrReplace(K key, V value) {
        int slotNumber = findSlotNumber(key);
        LinkedList<KeyValue<K, V>> slot = slots[slotNumber];
        for(KeyValue<K,V>entry:slot){
            if(entry.getKey().equals(key)){
                entry.setValue(value);
                return true;
            }
        }
        slot.add(new KeyValue<>(key, value));
        count++;
        return false;
    }

    public V get(K key) {
        int slotNumber = findSlotNumber(key);
        LinkedList<KeyValue<K, V>> slot = slots[slotNumber];
        for(KeyValue<K, V>entry: slot){
            if(entry.getKey().equals(key)){
                return entry.getValue();
            }
        }
        return null;
    }
    public KeyValue<K, V>find(K key) {
        int slotNumber = findSlotNumber(key);
        LinkedList<KeyValue<K, V>> slot = slots[slotNumber];
        for(KeyValue<K,V>entry:slot){
            if(entry.getKey().equals(key)){
                return entry;
            }
        }
        return null;
    }

    public boolean containsKey(K key) {
        return find(key) !=null;
    }

    public boolean remove(K key) {
        int slotNumber = findSlotNumber(key);
        LinkedList<KeyValue<K, V>> slot = slots[slotNumber];
        Iterator<KeyValue<K, V>> iterator = slot.iterator();
        while (iterator.hasNext()){
            KeyValue<K, V> entry = iterator.next();
            if(entry.getKey().equals(key)){
                iterator.remove();
                count--;
                return true;
            }
        }
        return false;
    }

    public void clear() {
        for(int i=0;i<capacity;i++){
            slots[i].clear();
        }
        count=0;
    }

    public Iterable<K>keys() {
        LinkedList<K> keyList = new LinkedList<>();
        for (LinkedList<KeyValue<K, V>>slot: slots){
            for(KeyValue<K,V>entry:slot){
                keyList.add(entry.getKey());
            }
        }
        return keyList;
    }

    public Iterable<V>values() {
        LinkedList<V> valueList = new LinkedList<>();
        for (LinkedList<KeyValue<K, V>>slot: slots){
            for(KeyValue<K,V>entry:slot){
                valueList.add(entry.getValue());
            }
        }
        return valueList;
    }

    @Override
    public Iterator<KeyValue<K, V>> iterator() {
        LinkedList<KeyValue<K,V>> allEntries = new LinkedList<>();
        for(LinkedList<KeyValue<K, V>> slot:slots){
            allEntries.addAll(slot);
        }
        return allEntries.iterator();
    }
}

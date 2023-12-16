import java.util.Random;

public class Main {
    public static void main(String[] args) {
        HashTable<Integer, Integer> hashTable = new HashTable<>();
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            int key = random.nextInt(Integer.MAX_VALUE);
            int value = random.nextInt(Integer.MAX_VALUE);
            hashTable.add(key, value);
        }
        System.out.println("Общее кол-во элементов: " + hashTable.size());
        System.out.println("Размер хеш-таблицы: " + hashTable.capacity());
        System.out.println("Кол-во коллизий: " + hashTable.countCollisions());
        //hashTable.printCollisions();
        //System.out.println(hashTable.values());
        //System.out.println(hashTable.keys());
        System.out.println(" ");
        HashTable<String, Integer> hashTable2 = new HashTable<>();
        hashTable2.add("One", 1);
        hashTable2.add("Two", 2);
        hashTable2.add("Three", 3);
        hashTable2.add("Four", 4);
        hashTable2.add("Five", 5);
        hashTable2.add("Six", 6);
        System.out.println("Общее кол-во элементов: " + hashTable2.size());
        System.out.println("Размер хеш-таблицы: " + hashTable2.capacity());
        System.out.println("Значение ключа 'Two': " + hashTable2.get("Two"));
        System.out.println("Значение ключа  'Four': " + hashTable2.get("Four"));
        hashTable2.addOrReplace("Two", 22);
        System.out.println("Изменение значение ключа 'Two': " + hashTable2.get("Two"));
        System.out.println("Имеется ли ключ 'One': " + hashTable2.containsKey("One"));
        System.out.println("Имеется ли ключ 'Five': " + hashTable2.containsKey("Five"));
        hashTable2.remove("Three");
        System.out.println("Размер после удаление 'Three': " + hashTable2.size());
        System.out.println("Ключи: " + hashTable2.keys());
        System.out.println("Значения: " + hashTable2.values());
        hashTable.clear();
        System.out.println("Размер после удаления: " + hashTable2.size());
    }
}

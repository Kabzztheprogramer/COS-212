public class PatientCatalogue<V> {
    // Record class for the linked lists used in each bucket
    private static class Record<V> {
        String key;
        V value;
        Record<V> next;

    Record(String key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Record<V>[] buckets; // Array of linked lists
    private final HashFunctions primaryHashFunction;
    private final HashFunctions secondaryHashFunction;
    private final HashFunctions tertiaryHashFunction;

    @SuppressWarnings("unchecked")
    public PatientCatalogue(int capacity, HashFunctions primary, HashFunctions secondary, HashFunctions tertiary) {
       
        buckets = new Record[capacity];
        primaryHashFunction = primary ;
        secondaryHashFunction = secondary ;
        tertiaryHashFunction = tertiary ;
        
    }

    public int getBucketIndex(String key) {
        
        if(key ==  null || key.isEmpty()){
            return -1 ;
        }
        int primaryKey = primaryHashFunction.hash(key);
        
        String SprimaryKey = Integer.toString(primaryKey);
        
        int secondaryKey = secondaryHashFunction.hash(SprimaryKey);
        
        String SsecondaryKey = Integer.toString(secondaryKey);
        
        return tertiaryHashFunction.hash(SsecondaryKey);
        
    }

    public V addRecord(String key, V value) {
        int index = getBucketIndex(key);

        if(index == -1 ){
            return null ;
        }

        Record<V> newRecord = new Record<V>(key, value) ;
        if(buckets[index]== null){
            buckets[index] = newRecord ;
            return null;      
        }else{
            Record<V> current = buckets[index] ;
            Record<V> pre = null ;

            while (current != null) { 
                if(current.key.equals(key)){
                    V oldValue = current.value ;
                    current.value = newRecord.value ;
                    return oldValue;
                }
                pre = current ;
                current = current.next ;
            }

            pre.next = newRecord ;
        }
        return null;
    }

    public V deleteRecord(String key) {

        int index = getBucketIndex(key);
        if(index == -1 || buckets[index]== null ){
            return null;      
        }else{
            Record<V> current = buckets[index] ;
            V value = current.value ;
            if( current.key.equals(key)){
                if(current.next!= null){
                    buckets[index] = current.next ;
                }else{
                    buckets[index]= null;
                }
                return value ;
            }

            Record<V> pre = null ;

            while (current != null) { 

                if(current.key.equals(key)){
                    if( pre != null ){
                        pre.next = current.next;
                    }
                    return current.value;
                }
                pre = current ;
                current = current.next ;
            }
        }
        return null ;
    }

//DO NOT MODIFY THIS METHOD AS IT WILL NEGATIVELY AFFECT YOUR MARKS
    public String printMap() {
        StringBuilder result = new StringBuilder();
        result.append("\n========== HASH MAP STRUCTURE ==========\n");

        for (int i = 0; i < buckets.length; i++) {
            Record<V> current = buckets[i];

            result.append("Bucket[").append(i).append("]: ");

            if (current == null) {
                result.append("[ empty ]\n");
                continue;
            }

            result.append("[ ").append(current.key).append(":").append(current.value).append(" ]");
            current = current.next;

            while (current != null) {
                result.append(" --> [ ").append(current.key).append(":").append(current.value).append(" ]");
                current = current.next;
            }
            result.append("\n");
        }

        return result.toString();
    }

}

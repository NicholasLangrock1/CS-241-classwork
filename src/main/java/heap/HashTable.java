package heap;

// Name: nicholas Langrock
//date: 04/23/2022
//generates hashtables with keys and values where the values of the hashtables are stored as linked lists

/** A hash table modeled after java.util.Map. It uses chaining for collision
 * resolution and grows its underlying storage by a factor of 2 when the load
 * factor exceeds 0.8. */
public class HashTable<K,V> {

    protected Pair[] buckets; // array of list nodes that store K,V pairs
    protected int size; // how many items currently in the map


    /** class Pair stores a key-value pair and a next pointer for chaining
     * multiple values together in the same bucket, linked-list style*/
    public class Pair {
        protected K key;
        protected V value;
        protected Pair next;

        /** constructor: sets key and value */
        public Pair(K k, V v) {
            key = k;
            value = v;
            next = null;
        }

        /** constructor: sets key, value, and next */
        public Pair(K k, V v, Pair nxt) {
            key = k;
            value = v;
            next = nxt;
        }
        public V getValue(){
            return(value);
        }
        /** returns (k, v) String representation of the pair */
        public String toString() {
            return "(" + key + ", " + value + ")";
        }
    }

    /** constructor: initialize with default capacity 17 */
    public HashTable() {
        this(17);
    }

    /** constructor: initialize the given capacity */
    public HashTable(int capacity) {
        buckets = createBucketArray(capacity);
    }

    /** Return the size of the map (the number of key-value mappings in the
     * table) */
    public int getSize() {
        return size;
    }

    /** Return the current capacity of the table (the size of the buckets
     * array) */
    public int getCapacity() {
        return buckets.length;
    }

    /** Return the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     * Runtime: average case O(1); worst case O(size) */
    public V get(K key) {
        // TODO 2.1 - do this together with put.
        //throw new UnsupportedOperationException();
        Pair head = buckets[Math.abs(key.hashCode())%buckets.length]; // *math.abs exists so that I have a way of dealing with neagive numbers*
        for(int i=0;i<buckets.length;i++){
            if(head!=null){
                if(head!=null&&head.key==key){
                    return head.value;
                }
                head=head.next;
        }
        }
        //if(head!=null)
        //return(head.value);

        return(null);
    }

    /** Associate the specified value with the specified key in this map. If
     * the map previously contained a mapping for the key, the old value is
     * replaced. Return the previous value associated with key, or null if
     * there was no mapping for key. If the load factor exceeds 0.8 after this
     * insertion, grow the array by a factor of two and rehash.
     * Precondition: val is not null.
     * Runtime: average case O(1); worst case O(size + a.length)*/
    public V put(K key, V val) {
        // TODO 2.2
        //   do this together with get. For now, don't worry about growing the
        //   array and rehashing.
        //   Tips:
        //     - Use the key's hashCode method to find which bucket it belongs in.
        //     - It's possible for hashCode to return a negative integer.
        //
        // TODO 2.5 - modify this method to grow and rehash if the load factor
        //            exceeds 0.8.
        //throw new UnsupportedOperationException();
        
        Pair head = buckets[Math.abs(key.hashCode())%buckets.length];
        V oldval=get(key);
        if(get(key) == null){
            buckets[Math.abs(key.hashCode())%buckets.length] = new Pair(key, val, buckets[Math.abs(key.hashCode())%buckets.length]);
            size++;
            growIfNeeded();
        }
        
        else if(get(key)!=null){
            for(int i=0;i<buckets.length;i++){
                if(head.key==key){
                    head.value=val;
                    break;
                }
                head=head.next;
            }
        }
        return oldval;
        
    }

    /** Return true if this map contains a mapping for the specified key.
     *  Runtime: average case O(1); worst case O(size) */
    public boolean containsKey(K key) {
        // TODO 2.3
        return(get(key)!=null);
        
        //throw new UnsupportedOperationException();
    }

    /** Remove the mapping for the specified key from this map if present.
     *  Return the previous value associated with key, or null if there was no
     *  mapping for key.
     *  Runtime: average case O(1); worst case O(size)*/
    public V remove(K key) {
        V oldval =get(key);
        // TODO 2.4
        if(buckets[(key.hashCode())%buckets.length]!=null){
        


        if((key.hashCode()-1)%buckets.length>=0&&(key.hashCode()+1)%buckets.length<=buckets.length){ //this will do three two things, set the head node = to null, and set the
        if(buckets[(key.hashCode()-1)%buckets.length]!=null&&buckets[(key.hashCode()+1)%buckets.length]!=null){//previous node next pointer = to the head noed's next node
        
            buckets[(key.hashCode()-1)%buckets.length].next= buckets[(key.hashCode()+1)%buckets.length];
        }
    }
        buckets[(key.hashCode())%buckets.length]=null;
        size--;
}
        return(oldval);
    }

    
    // suggested helper method:
    /* check the load factor; if it exceeds 0.8, double the array size
     * (capacity) and rehash values from the old array to the new array */
    private void growIfNeeded() {
      if(Math.abs(Double.valueOf(size)/Double.valueOf(buckets.length))>0.8){

        Pair[] tempBucket = createBucketArray(2*buckets.length); 
        for (int i = 0; i < buckets.length; i++){
            Pair head = buckets[i];
            while(true){
                if(head==null){
                    break;
                }
                else{
                    tempBucket[Math.abs(head.key.hashCode())%tempBucket.length] = new Pair(head.key, head.value, tempBucket[Math.abs(head.key.hashCode())%(tempBucket.length)]);
                    head = head.next;
                }
            }
        }
        buckets = tempBucket.clone();
      }

        //throw new UnsupportedOperationException();

    }

    /* useful method for debugging - prints a representation of the current
     * state of the hash table by traversing each bucket and printing the
     * key-value pairs in linked-list representation */
    protected void dump() {
        System.out.println("Table size: " + getSize() + " capacity: " +
                getCapacity());
        for (int i = 0; i < buckets.length; i++) {
            System.out.print(i + ": --");
            Pair node = buckets[i];
            while (node != null) {
                System.out.print(">" + node + "--");
                node = node.next;

            }
            System.out.println("|");
        }
    }

    /*  Create and return a bucket array with the specified size, initializing
     *  each element of the bucket array to be an empty LinkedList of Pairs.
     *  The casting and warning suppression is necessary because generics and
     *  arrays don't play well together.*/
    @SuppressWarnings("unchecked")
    protected Pair[] createBucketArray(int size) {
        return (Pair[]) new HashTable<?,?>.Pair[size];
    }
}

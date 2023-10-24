import java.util.NoSuchElementException;

import org.w3c.dom.Node;
/**
 * Makes a linked list object.
 */
public class SLL<T> {
    private NodeSL<T> head;
    private NodeSL<T> tail;

    /** 
     * A constructor that makes an empty list. It should have head and tail references that are both null.
     */
    public SLL() {
        head = tail = null;
    }

    /**
     * Copy constructor that creates a copy of the SLL<T> object.
     */ 
    public SLL(SLL<T> list) {
        if (list.isEmpty() == true) {
            head = tail = null;
            return;
        }
        head = tail = null;

        for (NodeSL<T> item = list.head; item != list.tail; item = item.getNext()) {
            this.addLast(item.getData());
        }
        this.addLast(list.getTail().getData());
    }

    //Phase 1 Methods
    /**
     * A method to check if a list is empty.
     */
    public boolean isEmpty() {
        if (head == null) {
            return true;
        }
        return false;
    }

    /** 
     * Accessor for getHead.
     */ 
    public NodeSL<T> getHead() {
        return this.head;
    }

    /**
     * Accessor for getTail
     */ 
    public NodeSL<T> getTail() {
        return this.tail;
    }

    /**
    * Adds a new element at the front of the list. If the list is intially empty, the method creates a new item and assigns it to the haed of the list.
    */
    public void addFirst(T v) {
        if (isEmpty() == true) {
            NodeSL<T> newItem = new NodeSL<>(v, null);
            this.head = this.tail = newItem;
        }
        else {
            NodeSL<T> newItem = new NodeSL<>(v, this.head);
            this.head = newItem;
        }
        return;
    }

    /**
    * Allows the list to be printed.
    */ 
    public String toString() {

        String stringList = "[";

        if (this.isEmpty() == true) {
            stringList += "]";
        }
        else if (this.head == this.tail) {
            stringList += this.head.getData() + "]";
        }
        else {
            NodeSL<T> item = this.head;
            while(item.getNext() != this.tail) {
                stringList += item.getData() + ", ";
                item = item.getNext();
            }
            stringList += item.getData() + ", "; 
            stringList += this.tail.getData() + "]";
        }
        return stringList;
    }



    //Phase 2 methods
    /**
     * Adds a new node at the end of the linked list.
     */
    public void addLast(T v) {
        if (this.isEmpty() == true) {
            NodeSL<T> newItem = new NodeSL<>(v, null);
            this.head = this.tail = newItem;
            return;
        }
        else {
            NodeSL<T> newItem = new NodeSL<>(v, null);
            this.tail.setNext(newItem);
            this.tail = newItem;
            return;
        }
    }

    /**
     * Adds a new node after the given position in the linked list.
     */
    public void addAfter(NodeSL<T> here, T v) {
        NodeSL<T> newItem = new NodeSL<>(v, here.getNext());
        
        if (here == this.tail) {
            addLast(v);
            return;
        }
        else {
            NodeSL<T> item = this.head;
            while (item != here) {
                item = item.getNext();
            }
            here.setNext(newItem);
            return;
        }
    }
    
    /**
     * Removes the first node in a linked list.
     */
    public T removeFirst() {
        if (this.isEmpty() == true) {
            throw new NoSuchElementException();
        }
        T itemRemoved = this.head.getData();
        this.head = this.head.getNext();

        if (this.head == null) {
            this.tail = null;
        }
        return itemRemoved;
    }
    
    /**
     * Removes the last nose in a linked list.
     */
    public T removeLast() {
        if (this.isEmpty() == true) {
            throw new NoSuchElementException();
        }
        
        if (this.head == this.tail) {
            T result = this.head.getData();
            this.head = this.tail = null;
            return result;
        }
        
        NodeSL<T> item = this.head;
        while (item.getNext() != this.tail) {
            item = item.getNext();
        }
        item.setNext(null);
        T itemRemoved = this.tail.getData();
        this.tail = item;
        
        return itemRemoved;
    }

    /**
     * Removes the node after the given position in the linked list.
     */
    public T removeAfter(NodeSL<T> here) {
        if (this.isEmpty() == true) {
            throw new NoSuchElementException();
        }

        else if (here == this.tail) {
            T itemRemoved = this.tail.getData();
            removeLast();
            return itemRemoved;
        }

        else if (here == null) {
            T result = this.head.getData();
            removeFirst();
            return result;
        }

        else {
            T itemRemoved = here.getNext().getData();
            NodeSL<T> item = this.head;
            while (item != here) {
                item = item.getNext();
            }
            here.setNext(here.getNext().getNext());
            return itemRemoved;
        }
        
    }

    /**
     * Returns the number of nodes in the linked list.
     */
    public int size() {
        int numberOfNodes = 0;
        
        if (this.isEmpty()) {
            return numberOfNodes;
        }
        
        for (NodeSL<T> item = this.head; item != this.tail; item = item.getNext()) {
            numberOfNodes++;
        }
        numberOfNodes++;
        
        return numberOfNodes;
    }



    //Phase 3 methods
    /**
     * Returns a copy of a subsequence of the list.
     */
    public SLL<T> subseqByCopy(NodeSL<T> here, int n) {
        
        SLL<T> copyList = new SLL<T>();

        int i = 1;

        for(NodeSL<T> item = here; item != null; item = item.getNext()) {
            if(i < n+1) {
                copyList.addLast(item.getData());
                i++;
            }
        }
        return copyList;
    }
    
    /**
     * Copies the nodes of the list and adds them to this following the node afterHere.
     */
    public void spliceByCopy(SLL<T> list, NodeSL<T> afterHere) {
        SLL<T> listCopy = new SLL<>(list);
        if (list == this) {
            throw new SelfInsertException();
        }
        else if (listCopy.isEmpty() == true) {
            return;
        }
        else if (afterHere == null) {
            listCopy.tail.setNext(this.head);
            for (NodeSL<T> item = listCopy.head; item != listCopy.tail; item = item.getNext()) {
                addFirst(item.getData());
            }
            this.head = listCopy.head;
        } 
        else if (afterHere.getNext() == this.tail) {
            afterHere.setNext(listCopy.head);
        }
        else {
            listCopy.tail.setNext(afterHere.getNext());
            afterHere.setNext(listCopy.head);
        }
    }

    /**
     * Extracts a subsequence out of the original list and returns it as a new list (thus shortening the original list).
     */
    public SLL<T> subseqByTransfer(NodeSL<T> afterHere, NodeSL<T> toHere) {
        SLL<T> newList = new SLL<T>();
        NodeSL<T> afterToHere = toHere.getNext();
        if (afterHere == null) {
            newList.head = this.head;
            newList.tail = toHere;
            newList.tail.setNext(null);
            this.head = afterToHere;

        }
        else {
            newList.head = afterHere.getNext();
            newList.tail = toHere;
            newList.tail.setNext(null);
            afterHere.setNext(afterToHere);
        }
        return newList;
    }

    /**
     * Moves all the elements of the list into this just after the node afterHere. The input argument list should be empty after it's run.
     */
    public void spliceByTransfer(SLL<T> list, NodeSL<T> afterHere) {
        if (list == this) {
            throw new SelfInsertException();
        }
        else if (afterHere == null) {
            list.tail.setNext(this.head);
            this.head = list.head;
        } 
        else if (afterHere == this.tail) {
            this.tail.setNext(list.head);
        }
        else {
            list.tail.setNext(afterHere.getNext());
            afterHere.setNext(list.head);
        }

        list.head = list.tail = null;
    }


}
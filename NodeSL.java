// Accessors and manipulators in the Node class to getData, setData, getNext and setNext. (Typically LinkedList implementations don't have a setNext method because that could imperil the structure of the list, but because we haven't nested our Node classes we'll have to use this workaround.)

  /** Nested class to keep track of nodes */
  public class NodeSL<T> {
      /** The data at the node */
      private T data;
      
      /** Link to the next node */
      private NodeSL<T> next;

      /** A constructor */
      NodeSL(T data, NodeSL<T> next) {
          this.data = data;
          this.next = next;
      }

      /** @return data field */
      public T getData() {
        return data;
      }

      /** @param d new data vaue */
      public void setData(T d) {
        this.data = d;
      }

      /** @return next node */
      public NodeSL<T> getNext() {
        return next;
      }

      /** @param d new next node */
      public void setNext(NodeSL<T> next) {
        this.next = next;
      }
    }

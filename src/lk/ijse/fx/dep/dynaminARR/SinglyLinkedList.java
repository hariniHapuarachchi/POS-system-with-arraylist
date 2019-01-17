package lk.ijse.fx.dep.dynaminARR;

public class SinglyLinkedList<T> {

    private Node head;
    private Node tail;

    private int size;

    public int getSize() {
        return size;

    }

    public void add(T data){
        if (head == null){
            head = new Node(data,null);
            tail = head;
        } else{
            Node node = new Node(data, null);
            tail.setNext(node);
            tail = node;
        }
        size++;
    }

    public T get(int index){

        if (size == 0){
            System.err.println("Empty List");
            return null;
        }

        if (index >= size){
            System.err.println("Invalid Index");
            return null;
        }

        Node indexNode = head;
        for (int i = 0; i < index ; i++) {
            indexNode = indexNode.getNext();
        }

        return (T) indexNode.getData();

    }

    public void remove(int index){

        if (size == 0){
            System.err.println("Empty List");
            return;
        }

        if (index >= size){
            System.err.println("Invalid Index");
            return;
        }

        Node indexNode = head;
        Node previousNode = null;
        for (int i = 0; i < index; i++) {
            previousNode = indexNode;
            indexNode = indexNode.getNext();

            if (indexNode == tail){
                tail = previousNode;
            }
        }

        if (previousNode != null){
            previousNode.setNext(indexNode.getNext());
        }else{
            head = head.getNext();
        }
        size--;
    }

    public void printAllNodes(){
        if (size == 0){
            System.out.println("[]");
            return;
        }

        System.out.print("[");
        Node indexNode = head;
        for (int i = 0; i < size ; i++) {
            System.out.print(indexNode.getData() + ",");
            indexNode = indexNode.getNext();
        }
        System.out.print("\b");
        System.out.print("]");
        System.out.println();
    }

}

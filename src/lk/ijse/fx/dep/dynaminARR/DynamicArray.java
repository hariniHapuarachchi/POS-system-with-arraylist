package lk.ijse.fx.dep.dynaminARR;

import java.util.Arrays;

public class DynamicArray<T> {

    private T[] data;

    private int size;

    public int getSize() {
        return size;
    }

    public void add(T data){
        if (this.data == null){
            this.data = (T[]) new Object[]{data};
        }else{
            Object[] tempArray = new Object[this.data.length + 1];
            for (int i = 0; i < this.data.length; i++) {
                tempArray[i]  = this.data[i];
            }
            tempArray[tempArray.length - 1] = data;
            this.data = (T[]) tempArray;
        }
        size++;
    }

    public void remove(int index){

        if (size == 0){
            System.err.println("Empty Array");
            return;
        }

        if (index >= size){
            System.err.println("Invalid Index");
            return;
        }

        Object[] tempArray = new Object[data.length - 1];

        int j = 0;
        for (int i = 0; i < data.length ; i++) {
            if (i != index){
                tempArray[j] = data[i];
                j++;
            }
        }

        data = (T[]) tempArray;
        size--;

    }

    public T get(int index){

        if (size == 0){
            System.err.println("Empty Array");
            return null;
        }



        if (index >= size){
            System.err.println("Invalid Index");
            return null;
        }

        return data[index];

    }

    public void printAll(){
        System.out.println(Arrays.toString(data));
    }

}

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PerformanceComparison {

    public static void compareInsertion(int size){
        List<Integer> arrayList = new ArrayList<>();
        LinkedList<Integer> linkedList = new LinkedList<>();

        long start,end;

        start = System.nanoTime();
        for(int i=0;i<size;i++){
            arrayList.add(i);
        }
        end = System.nanoTime();
        System.out.println("ArrayList insertion time: "+(end-start)/1000000.0+" ms");


        start = System.nanoTime();
        for(int i=0;i<size;i++){
            linkedList.add(i);
        }
        end = System.nanoTime();
        System.out.println("LinkedList insertion time: "+(end-start)/1000000.0+" ms");
    }

    public static void compareDeletion(int size){
        List<Integer> arrayList = new ArrayList<>();
        LinkedList<Integer> linkedList = new LinkedList<>();

        long start,end;

        for(int i=0;i<size;i++){
            arrayList.add(i);
        }
        start = System.nanoTime();
        for(int i=0;i<size;i++){
            arrayList.remove(0);
        }
        end = System.nanoTime();
        System.out.println("ArrayList deletion time: "+(end-start)/1000000.0+" ms");


        for(int i=0;i<size;i++){
            linkedList.add(i);
        }
        start = System.nanoTime();
        for(int i=0;i<size;i++){
            linkedList.remove(0);
        }
        end = System.nanoTime();
        System.out.println("Linkedlist deletion time: "+(end-start)/1000000.0+" ms");
    }

    public static void main(String[] args) {
        int[] sizes = {10000, 50000, 100000};

        for (int size : sizes) {
            System.out.println(" Size " + size);

            compareInsertion(size);
            compareDeletion(size);
        }
    }
}

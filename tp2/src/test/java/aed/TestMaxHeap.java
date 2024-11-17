package aed;

import java.util.Comparator;

public class TestMaxHeap {
    public static void main(String[] args) {
        Comparator<Integer> comparator = Integer::compare;

        MaxHeap<Integer> heap = new MaxHeap<>(comparator, 10);

        // Prueba: Insertar elementos
        heap.encolar(15);
        heap.encolar(10);
        heap.encolar(30);
        heap.encolar(5);
        heap.encolar(20);

        System.out.println("Heap después de encolar: " + heap);
        System.out.println("Máximo del heap: " + heap.getMax());
        System.out.println("Desencolando el máximo: " + heap.desencolar());
        System.out.println("Heap después de desencolar: " + heap);
        System.out.println("Heap después de eliminar en posición 2: " + heap);
        heap.encolar(25);
        System.out.println("Heap después de encolar 25: " + heap);
        }
}

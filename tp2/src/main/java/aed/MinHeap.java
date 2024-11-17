package aed;
import java.util.Arrays;
import java.util.Comparator;

public final class MinHeap<T> {
    private T[] heap;
    private int lastIndex;
    private Comparator<? super T> comparator;
    private static final int DEFAULT_CAPACITY = 100;
    private boolean integrityOK = false;

    @SuppressWarnings("unchecked")
    public MinHeap(Comparator<? super T> comparator, int initialCapacity) {
        this.comparator = comparator;
        heap = (T[]) new Object[initialCapacity + 1];
        lastIndex = 0;
        integrityOK = true;
    }

    public MinHeap(Comparator<? super T> comparator) {
        this(comparator, DEFAULT_CAPACITY);
    }

    // public void encolar(T newEntry) {
    //     checkIntegrity();
    //     int newIndex = lastIndex + 1;
    //     int parentIndex = newIndex / 2;

    //     while ((parentIndex > 0) &&
    //            comparator.compare(newEntry, heap[parentIndex]) < 0) {  // Cambio para MinHeap
    //         heap[newIndex] = heap[parentIndex];
    //         newIndex = parentIndex;
    //         parentIndex = newIndex / 2;
    //     }

    //     heap[newIndex] = newEntry;
    //     lastIndex++;
    //     ensureCapacity();
    // }
    public void encolar(T newEntry) {
        checkIntegrity();
        int newIndex = lastIndex + 1;
        int parentIndex = newIndex / 2;
    
        while ((parentIndex > 0) &&
               comparator.compare(newEntry, heap[parentIndex]) < 0) { // MinHeap o MaxHeap
            heap[newIndex] = heap[parentIndex];
            if (heap[newIndex] instanceof Traslado) { 
                ((Traslado) heap[newIndex]).setPosHeapAntiguo(newIndex); // Uso explícito de casting
            }
            newIndex = parentIndex;
            parentIndex = newIndex / 2;
        }
    
        heap[newIndex] = newEntry;
        if (newEntry instanceof Traslado) { 
            ((Traslado) newEntry).setPosHeapAntiguo(newIndex); // Uso explícito de casting
        }
        lastIndex++;
        ensureCapacity();
    }
    

    public T desencolar() {
        checkIntegrity();
        T root = null;

        if (!isEmpty()) {
            root = heap[1];
            heap[1] = heap[lastIndex];
            lastIndex--;
            reheap(1);
        }

        return root;
    }
    private void reheap(int rootIndex) {
        boolean done = false;
        T orphan = heap[rootIndex];
        int leftChildIndex = 2 * rootIndex;
    
        while (!done && (leftChildIndex <= lastIndex)) {
            int smallerChildIndex = leftChildIndex;
            int rightChildIndex = leftChildIndex + 1;
    
            if ((rightChildIndex <= lastIndex) &&
                comparator.compare(heap[rightChildIndex], heap[smallerChildIndex]) < 0) { // MinHeap o MaxHeap
                smallerChildIndex = rightChildIndex;
            }
    
            if (comparator.compare(orphan, heap[smallerChildIndex]) > 0) { // MinHeap o MaxHeap
                heap[rootIndex] = heap[smallerChildIndex];
                if (heap[rootIndex] instanceof Traslado) {
                    ((Traslado) heap[rootIndex]).setPosHeapAntiguo(rootIndex); // Uso explícito de casting
                }
                rootIndex = smallerChildIndex;
                leftChildIndex = 2 * rootIndex;
            } else {
                done = true;
            }
        }
    
        heap[rootIndex] = orphan;
        if (orphan instanceof Traslado) {
            ((Traslado) orphan).setPosHeapAntiguo(rootIndex); // Uso explícito de casting
        }
    }
    
    // private void reheap(int rootIndex) {
    //     boolean done = false;
    //     T orphan = heap[rootIndex];
    //     int leftChildIndex = 2 * rootIndex;

    //     while (!done && (leftChildIndex <= lastIndex)) {
    //         int smallerChildIndex = leftChildIndex;  // Cambiado de largerChildIndex a smallerChildIndex
    //         int rightChildIndex = leftChildIndex + 1;

    //         if ((rightChildIndex <= lastIndex) &&
    //             comparator.compare(heap[rightChildIndex], heap[smallerChildIndex]) < 0) {  // Cambio para MinHeap
    //             smallerChildIndex = rightChildIndex;
    //         }

    //         if (comparator.compare(orphan, heap[smallerChildIndex]) > 0) {  // Cambio para MinHeap
    //             heap[rootIndex] = heap[smallerChildIndex];
    //             rootIndex = smallerChildIndex;
    //             leftChildIndex = 2 * rootIndex;
    //         } else {
    //             done = true;
    //         }
    //     }

    //     heap[rootIndex] = orphan;
    // }

    private void ensureCapacity() {
        if (lastIndex >= heap.length - 1) {
            heap = Arrays.copyOf(heap, 2 * heap.length);
        }
    }

    private void checkIntegrity() {
        if (!integrityOK) {
            throw new SecurityException("Heap is corrupt.");
        }
    }

    public boolean isEmpty() {
        return lastIndex < 1;
    }

    public T getMin() {
        if (!isEmpty()) {
            return heap[1];
        }
        return null;
    }

    public int getCardinal() {
        return lastIndex;  // Retorna el número de elementos en el heap
    }
    public void eliminarPorPosicion(int posicion) {
        if (posicion <= lastIndex && posicion > 0) {
            heap[posicion] = heap[lastIndex];
            lastIndex--;
            reheap(posicion);
        }
    }
}

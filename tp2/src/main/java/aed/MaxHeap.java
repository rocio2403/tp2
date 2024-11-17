
package aed;
import java.util.Arrays;
import java.util.Comparator;

public final class MaxHeap<T> {
    public T[] heap;
    private int cardinal;
    private Comparator<? super T> comparator;
    private static final int cap_default = 100;
    private boolean integrityOK = false;

    @SuppressWarnings("unchecked")
    public MaxHeap(Comparator<? super T> comparator, int initialCapacity) {
        this.comparator = comparator;
        heap = (T[]) new Object[initialCapacity + 1];
        cardinal = 0;
        integrityOK = true;
    } // lo inicializo vacio

    public MaxHeap(Comparator<? super T> comparator) {
        this(comparator, cap_default);
    } //lo inicilaizo con un comparador y una capacidad inicial
    public void encolar(T newEntry) {
        checkIntegrity();
        int newIndex = cardinal + 1;
        int parentIndex = newIndex / 2;
    
        while ((parentIndex > 0) &&
               comparator.compare(newEntry, heap[parentIndex]) > 0) { // MaxHeap: prioridad a valores mayores
            heap[newIndex] = heap[parentIndex];
            if (heap[newIndex] instanceof Traslado) { 
                ((Traslado) heap[newIndex]).setPosRedituable(newIndex); // Actualiza posición para el MaxHeap
            }
            if (heap[newIndex] instanceof Ciudad) {
                ((Ciudad) heap[newIndex]).setPosHeapSuperavit(newIndex); // Actualiza posición para el MaxHeap
            }
            newIndex = parentIndex;
            parentIndex = newIndex / 2;
        }
    
        heap[newIndex] = newEntry;
        if (newEntry instanceof Traslado) { 
            ((Traslado) newEntry).setPosRedituable(newIndex);// Actualiza posición para el MaxHeap
        }
        if (newEntry instanceof Ciudad) {
            ((Ciudad) newEntry).setPosHeapSuperavit(newIndex); // Actualiza posición para el MaxHeap
        }
        cardinal++;
        masCap();
    }
    
    private void reheap(int rootIndex) {
        boolean done = false;
        T orphan = heap[rootIndex];
        int leftChildIndex = 2 * rootIndex;
    
        while (!done && (leftChildIndex <= cardinal)) {
            int largerChildIndex = leftChildIndex;
            int rightChildIndex = leftChildIndex + 1;
    
            if ((rightChildIndex <= cardinal) &&
                comparator.compare(heap[rightChildIndex], heap[largerChildIndex]) > 0) { // MaxHeap: mayor prioridad a valores más altos
                largerChildIndex = rightChildIndex;
            }
    
            if (comparator.compare(orphan, heap[largerChildIndex]) < 0) { // MaxHeap: si el hijo es mayor, intercambiar
                heap[rootIndex] = heap[largerChildIndex];
                if (heap[rootIndex] instanceof Traslado) {
                    ((Traslado) heap[rootIndex]).setPosRedituable(rootIndex); // Uso explícito de casting
                }
                rootIndex = largerChildIndex;
                leftChildIndex = 2 * rootIndex;
            } else {
                done = true;
            }
        }
    
        heap[rootIndex] = orphan;
        if (orphan instanceof Traslado) {
            ((Traslado) orphan).setPosRedituable(rootIndex); // Actualiza posición para el MaxHeap
        }
    }
    

    public T desencolar() {
        checkIntegrity();
        T root = null;

        if (!isEmpty()) {
            root = heap[1];
            heap[1] = heap[cardinal];
            cardinal--;
            reheap(1);
        }

        return root;
    } 

    private void masCap() {
        if (cardinal >= heap.length - 1) {
            heap = Arrays.copyOf(heap, 2 * heap.length);
        }
    }

    private void checkIntegrity() {
        if (!integrityOK) {
            throw new SecurityException("Problemas con heap :( )");
        }
    }

    public boolean isEmpty() {
        return cardinal < 1;
    }

    public T getMax() {
        if (!isEmpty()) {
            return heap[1];
        }
        return null;
    }
    public int getCardinal() {
        return cardinal;  // Retorna el número de elementos en el heap
    }
    public void eliminarPorPosicion(int posicion) {
        if (posicion <= cardinal && posicion > 0) {
            heap[posicion] = heap[cardinal];
            cardinal--;
            reheap(posicion);
        }
    }
    public void rechequear(int pos) {
        checkIntegrity();
        reheap(pos); // Ajusta hacia arriba o hacia abajo según el nuevo valor
    }
    
    
    
    
}

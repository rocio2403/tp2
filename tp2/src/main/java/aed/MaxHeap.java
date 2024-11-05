package aed;

import java.util.Comparator;

public class MaxHeap<T> {
    private T[] _array;
    private int _n;
    private Comparator<? super T> comparator;

    public MaxHeap(T[] array, int cardinal, Comparator<? super T> comparator) {
        this._array = array;
        this._n = cardinal;
        this.comparator = comparator;
        this.heapify();
    }

    // Constructor por defecto (sin Comparator)
    public MaxHeap(T[] array, int cardinal) {
        this(array, cardinal, null);
    }

    private void heapify() {
        for (int i = (_n / 2) - 1; i >= 0; i--) {
            downHeap(i);
        }
    }

    private int compare(T o1, T o2) {
        return (comparator == null) ? ((Comparable<T>) o1).compareTo(o2) : comparator.compare(o1, o2);
    }

    public void insertar(T elemento) {
        if (_n >= _array.length) throw new IllegalStateException("Heap lleno");
        _array[_n] = elemento;
        upHeap(_n++);
    }

    public T extraerMax() {
        if (_n == 0) throw new IllegalStateException("Heap vacío");
        T max = _array[0];
        _array[0] = _array[--_n];
        downHeap(0);
        return max;
    }

    private void upHeap(int i) { /* Implementación de upHeap */ }
    private void downHeap(int i) { /* Implementación de downHeap */ }
}

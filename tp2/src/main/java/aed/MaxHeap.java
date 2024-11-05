package aed;

public class MaxHeap<T extends Comparable<T>> {
    private T[] _array;
    private int _n;


    public MaxHeap(T[] array, int cardinal) { // Constructor por heapify // buildHeap // Algoritmo de Floyd
        this._array = array; // O(n)
        this._n = cardinal; // O(1)
        this.heapify(); // O(n)
    } // O(n)

    public T[] cola() {
        return this._array; // O(1)
    } // O(1)

    public int cardinal() {
        return this._n; // O(1)
    } // O(1)

    public T proximo() {
        return this._array[0]; // O(1)
    } // O(1)

    public boolean pertenece(T elem) { // búsqueda lineal
        int i = 0; // O(1)
        while (i < this._n && this._array[i] != elem) { // O(1)
            i++; // O(1)
        } // O(n)
        return i < this._n; // O(1)
    } // O(n)

    public String toString() {
        String res = "{"; // O(1)
        for (int i = 0; i < this._n; i++) {
            res += this._array[i].toString(); // O(1)
            if (i != this._n - 1) { // O(1)
                res += ", "; // O(1)
            }
        } // O(n)
        res += "}"; // O(1)
        return res; // O(1)
    } // O(n)

    private void swap(int i, int j) {
        T temp = this._array[i]; // O(1)
        this._array[i] = this._array[j]; // O(1)
        this._array[j] = temp; // O(1)
    } // O(1)

    private static int padre(int i) {
        return (i - 1)/2; // O(1)
    } // O(1)

    private static int hijoIzq(int i) {
        return 2*i + 1; // O(1)
    } // O(1)

    private static int hijoDer(int i) {
        return 2*i + 2; // O(1)
    } // O(1)

    private T prioridad(int i) {
        return this._array[i]; // O(1)
    } // O(1)

    private void subir(int i) {
        for (int p = padre(i); i != 0 && this.prioridad(i).compareTo(this.prioridad(p)) > 0; p = padre(i)) { // O(1)
            this.swap(i, p); // O(1)
            i = p; // O(1)
        } // O(log(n))
    } // O(log(n))

    private Boolean esHoja(int i) {
        return hijoIzq(i) >= this._n; // O(1)
    } // O(1)

    private void bajar(int i) { // A.k.a. "percolar"
        for (int hi = hijoIzq(i), hd = hijoDer(i); 
            !(this.esHoja(i)) && (
            (hi < this._n && this.prioridad(i).compareTo(prioridad(hi)) < 0) || 
            (hd < this._n && this.prioridad(i).compareTo(prioridad(hd)) < 0));
            hi = hijoIzq(i), hd = hijoDer(i)) { // O(1)
            if ((hi < this._n && hd < this._n && prioridad(hi).compareTo(prioridad(hd)) > 0) ||
                (hi < this._n && hd >= this._n)) { // O(1)
                this.swap(i, hi); // O(1)
                i = hi; // O(1)
            } else {
                this.swap(i, hd); // O(1)
                i = hd; // O(1)
            }
        } // O(log(n))
    } // O(log(n))

    public void encolar(T elem) { // insertar
        int cap = this._array.length; // O(1)
        int i = this._n; // O(1)
        if (i < cap) { // O(1)
            this._array[i] = elem; // O(1)
            this._n++; // O(1)
            this.subir(i); // O(log(n))
        }
    } // O(log(n))

    public T desencolar() { // eliminar
        T res = this._array[0]; // O(1)
        int i = this._n - 1; // O(1)
        this.swap(i, 0); // O(1)
        this._n--; // O(1)
        this.bajar(0); // O(log(n))
        return res;
    } // O(log(n))

    private void heapify() { // buildHeap // Algoritmo de Floyd
        //revisar  https://youtu.be/cuL8gXCSA58?si=oXDtn6goyfo3I0Cq
        for (int i = this._n - 1; i >= 0; i--) {
            this.bajar(i);
        }
    } // Por lo analizado en teórica, O(n)

    private class MaxHeapIterador implements IteradorBase<T> {
    	private T[] _array = this._array;
        private int _n = this._n;
        private int _i;

        public MaxHeapIterador() {
            this._i = 0; // O(1)
        } // O(1)

        public boolean haySiguiente() {
            return this._i < this._n; // O(1)
        } // O(1)

        public T siguiente() {
            T res = this._array[this._i]; // O(1)
            this._i++; // O(1)
            return res;
        } // O(1)
    }

    public IteradorBase<T> iterador() {
        return new MaxHeapIterador(); // O(1)
    } // O(1)
}

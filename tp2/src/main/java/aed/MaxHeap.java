package aed;

import java.util.Arrays;
import java.util.Comparator;

public class MaxHeap<T extends Comparable<T>> {
    private T[] _array;
    private int _n; 
    private Comparator<T> comparator;

    public MaxHeap(T[] array, int cardinal,Comparator<T> comparator) { 
        this._array = array; // O(n)
        this._n = cardinal; // O(1)
        this.comparator = comparator;
        this.heapify(); // O(n)
    } // O(n)

    public T[] cola() {  
        return this._array; // O(1)
    } // O(1)

    public int cardinal() {
        return this._n; // O(1)
    } // O(1)

    public T proximo() { //Devuelve la raiz, sin eliminarla 
        return this._array[0]; // O(1)
    } // O(1)

    public boolean pertenece(T elem) { // búsqueda lineal
        int i = 0; // O(1)
        while (i < this._n && this._array[i] != elem) { // O(1)
            i++; // O(1)
        } // O(n)
        return i < this._n; // O(1)
    } // O(n)

    public String toString() { //Crea y devuelve una representación en cadena del heap, con elementos separados por comas y encerrados entre llaves.
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

    private void swap(int i, int j) { //ntercambia los elementos en las posiciones i y j
        T temp = this._array[i]; // O(1)
        this._array[i] = this._array[j]; // O(1)
        this._array[j] = temp; // O(1)
    } // O(1)

    public static int padre(int i) {
        return (i - 1)/2; // O(1)
    } // O(1)

    private static int hijoIzq(int i) {
        return 2*i + 1; // O(1)
    } // O(1)

    private static int hijoDer(int i) {
        return 2*i + 2; // O(1)
    } // O(1)

    public T prioridad(int i) { // Devuelve el elemento en la posición i del array, representando la prioridad de ese nodo
        return this._array[i]; // O(1)
    } // O(1)

    public void subir(int i) { //Corrige la posición de un elemento moviéndolo hacia arriba en el heap si es mayor que su padre, para mantener la propiedad de max-heap.
        for (int p = padre(i); i != 0 && this.prioridad(i).compareTo(this.prioridad(p)) > 0; p = padre(i)) { // O(1)
            this.swap(i, p); // O(1)
            i = p; // O(1)
        } // O(log(n))
    } // O(log(n))

    private Boolean esHoja(int i) {
        return hijoIzq(i) >= this._n; // O(1)
    } // O(1)

    public void bajar(int i) { // Corrige la posición de un elemento moviéndolo hacia abajo en el heap si es menor que alguno de sus hijos, para mantener la propiedad de max-heap.
        // compara el nodo con sus hijos y, si es menor que alguno de ellos, intercambia con el hijo de mayor prioridad. Este proceso se repite hasta que el nodo esté en la posición correcta o sea una hoja
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

    public T desencolar() { // elimina el primero
        T res = this._array[0]; // O(1)
        int i = this._n - 1; // O(1)
        this.swap(i, 0); // O(1)
        this._n = this._n--; // O(1)
        this.bajar(0); // O(log(n))
        return res;
    } // O(log(n))

    public void eliminar(int i) { //borra el iésimo elemento :)          
        if (i < 0 || i >= this._n) {
            throw new IndexOutOfBoundsException("Índice fuera de rango"); // O(1)
        }
        this.swap(i, this._n - 1); // O(1)
        this._n--; // O(1)
        if (i > 0 && this.prioridad(i).compareTo(this.prioridad(padre(i))) > 0) { // O(1)
            this.subir(i); // O(log n)
        } else {
            this.bajar(i); // O(log n)
        }
    }
    

    
    private void heapify() { //algoritmo de floyd
        for (int i = this._n - 1; i >= 0; i--) {
            this.bajar(i);
        }
    } // Por lo analizado en teórica, O(n)

    public MaxHeap(MaxHeap<T> otroHeap) {//Constructor de copia
    this._n = otroHeap._n;
    this._array = Arrays.copyOf(otroHeap._array, otroHeap._array.length); 
    }



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

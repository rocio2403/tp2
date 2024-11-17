package aed;
import java.util.Comparator;


public class MaxHeap<T extends Comparable<T>> {
    private T[] array;
    private int longitud;
    private Comparator<T> comparator;


    public MaxHeap(T[] array, int cardinal,Comparator<T> comparator ) { // Constructor por heapify // buildHeap // Algoritmo de Floyd
        this.array = array; // O(n)
        this.longitud = cardinal; // O(1)
        this.heapify(); // O(n)
        this.comparator = comparator;
    } // O(n)

    public int compare(T a, T b) {
        return (comparator != null) ? comparator.compare(a, b) : a.compareTo(b);
    }

    public T[] cola() {
        return this.array; // O(1)
    } // O(1)

    public int cardinal() {
        return this.longitud; // O(1)
    } // O(1)

    public T proximo() {
        return this.array[0]; // O(1)
    } // O(1)

    public boolean pertenece(T elem) { // búsqueda lineal
        int i = 0; // O(1)
        while (i < this.longitud && this.array[i] != elem) { // O(1)
            i++; // O(1)
        } // O(n)
        return i < this.longitud; // O(1)
    } // O(n)

    public String toString() {
        String res = "{"; // O(1)
        for (int i = 0; i < this.longitud; i++) {
            res += this.array[i].toString(); // O(1)
            if (i != this.longitud - 1) { // O(1)
                res += ", "; // O(1)
            }
        } // O(n)
        res += "}"; // O(1)
        return res; // O(1)
    } // O(n)

    public void swap(int i, int j) {
        T temp = this.array[i]; // O(1)
        this.array[i] = this.array[j]; // O(1)
        this.array[j] = temp; // O(1)

       
    
    } // O(1)

   
    
    public  static int padre(int i) {
        return (i - 1)/2; // O(1)
    } // O(1)

    private static int hijoIzq(int i) {
        return 2*i + 1; // O(1)
    } // O(1)

    private static int hijoDer(int i) {
        return 2*i + 2; // O(1)
    } // O(1)

    public T prioridad(int i) {
        return this.array[i]; // O(1)
    } // O(1)

    public void subir(int i) {
        for (int p = padre(i); i != 0 && compare(this.prioridad(i), this.prioridad(p)) > 0; p = padre(i)) { // O(1)
            this.swap(i, p); // O(1)
            i = p; // O(1)
        } // O(log(n))
    } // O(log(n))

    private Boolean esHoja(int i) {
        return hijoIzq(i) >= this.longitud; // O(1)
    } // O(1)
    public T eliminar(int i) {
        if (i < 0 || i >= this.longitud) {
            throw new IndexOutOfBoundsException("Índice fuera de los límites.");
        }
        
        T eliminado = this.array[i]; // Guardar el elemento a eliminar
        this.swap(i, this.longitud - 1); // Intercambiar con el último elemento
        this.longitud--; // Reducir el tamaño del heap
        
        // Verificar si es necesario bajar o subir el elemento
        if (i > 0 && compare(this.prioridad(i), this.prioridad(padre(i))) > 0) {
            subir(i); // Subir si es mayor que su padre
        } else {
            bajar(i); // Bajar si no cumple la propiedad de max-heap
        }
        
        return eliminado; // Retornar el elemento eliminado
    }
    
    public void bajar(int i) { // A.k.a. "percolar"
        for (int hi = hijoIzq(i), hd = hijoDer(i); 
            !(this.esHoja(i)) && (
            (hi < this.longitud && compare(this.prioridad(i), prioridad(hi)) < 0) || 
            (hd < this.longitud && compare(this.prioridad(i), prioridad(hd)) < 0));

            hi = hijoIzq(i), hd = hijoDer(i)) { // O(1)

            if ((hi < this.longitud && hd < this.longitud&& compare(prioridad(hi), prioridad(hd)) > 0) ||
                (hi < this.longitud && hd >= this.longitud)) { // O(1)
                this.swap(i, hi); // O(1)
                i = hi; // O(1)
            } else {
                this.swap(i, hd); // O(1)
                i = hd; // O(1)
            }
        } // O(log(n))
    } // O(log(n))

    // public void encolar(T elem) { // insertar
    //     int cap = this.array.length; // O(1)
    //     int i = this.longitud; // O(1)
    //     if (i < cap) { // O(1)
    //         this.array[i] = elem; // O(1)
    //         this.longitud++; // O(1)
    //         this.subir(i); // O(log(n))
    //     }
    // } // O(log(n))
    public void encolar(T elem) { // insertar
        int cap = this.array.length; // O(1)
        int i = this.longitud; // O(1)
        if (i >= cap) { // Si está lleno
            expandirCapacidad(); // O(n)
        }
        this.array[i] = elem; // O(1)
        this.longitud++; // O(1)
        this.subir(i); // O(log(n))
    } // O(log(n)) promedio, O(n) en peor caso por la expansión
    
    private void expandirCapacidad() {
        int nuevaCapacidad = this.array.length * 2; // Duplicar capacidad
        T[] nuevoArray = (T[]) new Comparable[nuevaCapacidad]; // Crear nuevo array
        System.arraycopy(this.array, 0, nuevoArray, 0, this.array.length); // Copiar elementos al nuevo array
        this.array = nuevoArray; // Actualizar referencia
    }
    

    public T desencolar() { // eliminar
        T res = this.array[0]; // O(1)
        int i = this.longitud - 1; // O(1)
        this.swap(i, 0); // O(1)
        this.longitud--; // O(1)
        this.bajar(0); // O(log(n))
        return res;
    } // O(log(n))

    private void heapify() { // buildHeap // Algoritmo de Floyd (yewtu.be/watch?v=C8IqJshhVbg)
        for (int i = this.longitud - 1; i >= 0; i--) {
            this.bajar(i);
        }
    } // Por lo analizado en teórica, O(n)

    private class MaxHeapIterador implements IteradorBase<T> {
    	private T[] array = this.array;
        private int longitud = this.longitud;
        private int _i;

        public MaxHeapIterador() {
            this._i = 0; // O(1)
        } // O(1)

        public boolean haySiguiente() {
            return this._i < this.longitud; // O(1)
        } // O(1)

        public T siguiente() {
            T res = this.array[this._i]; // O(1)
            this._i++; // O(1)
            return res;
        } // O(1)
    }

    public IteradorBase<T> iterador() {
        return new MaxHeapIterador(); // O(1)
    } // O(1)
}

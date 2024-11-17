package aed;

import java.util.Comparator;

public class MinHeap<T extends Comparable<T>> {
    private T[] _array;
    private int _n;
    private Comparator<T> comparator;

    // Constructor que recibe un comparador
    public MinHeap(T[] array, int cardinal, Comparator<T> comparator) {
        this._array = array;
        this._n = cardinal;
        this.comparator = comparator;
        this.heapify();
    }

    public T[] cola() {
        return this._array;
    }

    public int cardinal() {
        return this._n;
    }

    public T proximo() {
        return this._array[0];
    }

    public boolean pertenece(T elem) {
        int i = 0;
        while (i < this._n && this._array[i] != elem) {
            i++;
        }
        return i < this._n;
    }

    public String toString() {
        String res = "{";
        for (int i = 0; i < this._n; i++) {
            res += this._array[i].toString();
            if (i != this._n - 1) {
                res += ", ";
            }
        }
        res += "}";
        return res;
    }

    private void swap(int i, int j, T elem) {
        T temp = this._array[i];
        this._array[i] = this._array[j];
        this._array[j] = temp;
        if(elem instanceof Traslado){
            ((Traslado) elem).setPosHeapAntiguo(i);
        }
    }
    public void eliminar(int i) {
        if (i < 0 || i >= this._n) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        
        T eliminado = this._array[i]; // Guardar el elemento a eliminar
        this.swap(i, this._n - 1, eliminado); // Intercambiar con el último elemento
        this._n--; // Reducir el tamaño del heap
        
        // Reequilibrar el heap
        if (i > 0 && this.prioridad(i).compareTo(this.prioridad(padre(i))) < 0) {
            this.subir(i); // Subir si es menor que su padre
        } else {
            this.bajar(i); // Bajar si es mayor que sus hijos
        }
    } 
    

    public static int padre(int i) {
        return (i - 1) / 2;
    }

    private static int hijoIzq(int i) {
        return 2 * i + 1;
    }

    private static int hijoDer(int i) {
        return 2 * i + 2;
    }

    public T prioridad(int i) {
        return this._array[i];
    }

    public void subir(int i) {
        for (int p = padre(i); i != 0 && this.prioridad(i).compareTo(this.prioridad(p)) < 0; p = padre(i)) {
            this.swap(i, p,_array[i]);
            i = p;
        }
    }

    private Boolean esHoja(int i) {
        return hijoIzq(i) >= this._n;
    }

    public  void bajar(int i) {
        for (int hi = hijoIzq(i), hd = hijoDer(i);
             !(this.esHoja(i)) && (
             (hi < this._n && this.prioridad(i).compareTo(prioridad(hi)) > 0) || 
             (hd < this._n && this.prioridad(i).compareTo(prioridad(hd)) > 0));
             hi = hijoIzq(i), hd = hijoDer(i)) {
            
            if ((hi < this._n && hd < this._n && prioridad(hi).compareTo(prioridad(hd)) < 0) ||
                (hi < this._n && hd >= this._n)) {
                this.swap(i, hi,_array[i]);
                i = hi;
            } else {
                this.swap(i, hd,_array[i]);
                i = hd;
            }
        }
    }

    // public void encolar(T elem) {
    //     int cap = this._array.length;
    //     int i = this._n;
    //     if (i < cap) {
    //         this._array[i] = elem;
    //         this._n++;
    //         this.subir(i);
    //     }
    // }
    public void encolar(T elem) {
        int cap = this._array.length; // O(1)
        int i = this._n; // O(1)
        if (i >= cap) { // Si está lleno
            expandirCapacidad(); // O(n)
        }
        this._array[i] = elem; // O(1)
        this._n++; // O(1)
        this.subir(i); // O(log(n))
    } // O(log(n)) promedio, O(n) en peor caso por expansión
    
    private void expandirCapacidad() {
        int nuevaCapacidad = this._array.length * 2; // Duplicar capacidad
        T[] nuevoArray = (T[]) new Comparable[nuevaCapacidad]; // Crear nuevo array
        System.arraycopy(this._array, 0, nuevoArray, 0, this._array.length); // Copiar elementos
        this._array = nuevoArray; // Actualizar referencia
    }
    

    public T desencolar() {
        T res = this._array[0];
        int i = this._n - 1;
        this.swap(i, 0,_array[i]);
        this._n--;
        this.bajar(0);
        return res;
    }

    
    
    public void heapify() {
        for (int i = this._n - 1; i >= 0; i--) {
            this.bajar(i);
        }
    }

    private class MinHeapIterador implements IteradorBase<T> {
        private int _i;

        public MinHeapIterador() {
            this._i = 0;
        }

        public boolean haySiguiente() {
            return this._i < _n;
        }

        public T siguiente() {
            T res = _array[this._i];
            this._i++;
            return res;
        }
    }

    public IteradorBase<T> iterador() {
        return new MinHeapIterador();
    }
}

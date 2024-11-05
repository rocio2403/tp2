package aed;

public class MinHeap<T extends Comparable<T>> {
    private T[] _array;
    private int _n;

    // InvRep
    /* forall i: Z :: (0 <= i < _n) =>
     *                (((hijoIzq(i) < _n) =>L _array[i] < _array[hijoIzq(i)] ^ 
     *                  (hijoDer(i) < _n) =>L _array[i] < _array[hijoDer(i)]))
     *   
     *  aux hijoIzq(i: Z) = 2*i + 1
     *  aux hijoDer(i: Z) = 2*i + 2
     */

    public MinHeap(T[] array, int cardinal) { // Constructor por heapify
        this._array = array;
        this._n = cardinal;
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

    private void swap(int i, int j) {
        T temp = this._array[i];
        this._array[i] = this._array[j];
        this._array[j] = temp;
    }

    private static int padre(int i) {
        return (i - 1) / 2;
    }

    private static int hijoIzq(int i) {
        return 2 * i + 1;
    }

    private static int hijoDer(int i) {
        return 2 * i + 2;
    }

    private T prioridad(int i) {
        return this._array[i];
    }

    private void subir(int i) {
        for (int p = padre(i); i != 0 && this.prioridad(i).compareTo(this.prioridad(p)) < 0; p = padre(i)) {
            this.swap(i, p);
            i = p;
        }
    }

    private Boolean esHoja(int i) {
        return hijoIzq(i) >= this._n;
    }

    private void bajar(int i) {
        for (int hi = hijoIzq(i), hd = hijoDer(i);
             !(this.esHoja(i)) && (
             (hi < this._n && this.prioridad(i).compareTo(prioridad(hi)) > 0) || 
             (hd < this._n && this.prioridad(i).compareTo(prioridad(hd)) > 0));
             hi = hijoIzq(i), hd = hijoDer(i)) {
            
            if ((hi < this._n && hd < this._n && prioridad(hi).compareTo(prioridad(hd)) < 0) ||
                (hi < this._n && hd >= this._n)) {
                this.swap(i, hi);
                i = hi;
            } else {
                this.swap(i, hd);
                i = hd;
            }
        }
    }

    public void encolar(T elem) {
        int cap = this._array.length;
        int i = this._n;
        if (i < cap) {
            this._array[i] = elem;
            this._n++;
            this.subir(i);
        }
    }

    public T desencolar() {
        T res = this._array[0];
        int i = this._n - 1;
        this.swap(i, 0);
        this._n--;
        this.bajar(0);
        return res;
    }

    private void heapify() {
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

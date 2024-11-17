package aed;
//NO CONFIAR Y REVISAR
public class ABB_Dict<T extends Comparable<T>> {
    private Nodo raiz;
    private int cardinal = 0;

    // Usamos una clase para representar la tupla (par de posiciones)
    public class Tupla {
        int posMaxHeap;
        int posMinHeap;

        public Tupla(int posMaxHeap, int posMinHeap) {
            this.posMaxHeap = posMaxHeap;
            this.posMinHeap = posMinHeap;
        }
    }

    private class Nodo {
        private T clave;
        private Tupla valor;  // Cambiamos a Tupla para almacenar posiciones de los heaps
        private Nodo padre;
        private Nodo izquierda;
        private Nodo derecho;

        public Nodo(T clave, Tupla valor) {
            this.clave = clave;
            this.valor = valor;
            this.padre = null;
            this.izquierda = null;
            this.derecho = null;
        }
    }

    public void insertar(T clave, Tupla valor) {
        raiz = insertarRec(raiz, clave, valor);
    }

    private Nodo insertarRec(Nodo nodo, T clave, Tupla valor) {
        if (nodo == null) {
            nodo = new Nodo(clave, valor);
            cardinal++;
        } else {
            int comparacion = clave.compareTo(nodo.clave);
            if (comparacion < 0) {
                nodo.izquierda = insertarRec(nodo.izquierda, clave, valor);
                nodo.izquierda.padre = nodo;
            } else if (comparacion > 0) {
                nodo.derecho = insertarRec(nodo.derecho, clave, valor);
                nodo.derecho.padre = nodo;
            }
            // Si la clave ya existe, actualizamos el valor
        }
        return nodo;
    }

    public Tupla buscar(T clave) {
        return buscarRec(raiz, clave);
    }

    private Tupla buscarRec(Nodo nodo, T clave) {
        if (nodo == null) {
            return null; // No encontrado
        }
        int comparacion = clave.compareTo(nodo.clave);
        if (comparacion == 0) {
            return nodo.valor; // Devolvemos la tupla de posiciones
        } else if (comparacion < 0) {
            return buscarRec(nodo.izquierda, clave);
        } else {
            return buscarRec(nodo.derecho, clave);
        }
    }

    public void eliminar(T clave) {
        raiz = eliminarRec(raiz, clave);
        cardinal--;
    }

    private Nodo eliminarRec(Nodo nodo, T clave) {
        if (nodo == null) {
            return nodo;
        }

        int comparacion = clave.compareTo(nodo.clave);
        if (comparacion < 0) {
            nodo.izquierda = eliminarRec(nodo.izquierda, clave);
        } else if (comparacion > 0) {
            nodo.derecho = eliminarRec(nodo.derecho, clave);
        } else {
            // Si encontramos el nodo a eliminar
            if (nodo.izquierda == null) {
                return nodo.derecho;
            } else if (nodo.derecho == null) {
                return nodo.izquierda;
            }

            // Encontramos el sucesor (mínimo del subárbol derecho)
            nodo.clave = nodo.derecho.clave;
            nodo.valor = nodo.derecho.valor;
            nodo.derecho = eliminarRec(nodo.derecho, nodo.clave);
        }

        return nodo;
    }

    private Tupla encontrarMinimoValor(Nodo nodo) {
        Nodo actual = nodo;
        while (actual.izquierda != null) {
            actual = actual.izquierda;
        }
        return actual.valor;
    }

    // Método para obtener el tamaño del árbol (cantidad de nodos)
    public int getCardinal() {
        return cardinal;
    }
}

package aed;

public class Tupla <T, M> {
    //creo clase de tuplas de enteros para usar en 3
    public T primero;
    public M segundo;

    // Constructor para inicializar la tupla
    public Tupla(T primero, M segundo) {
        this.primero = primero;
        this.segundo = segundo;
    }

    public T getPrimero() {
        return primero;
    }

    public M getSegundo() {
        return segundo;
    }
    
    public void setPrimero(T primero) {
        this.primero = primero;
    }

    public void setSegundo(M segundo) {
        this.segundo = segundo;
    }
} 
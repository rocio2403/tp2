package aed;

public class Tupla {
    //creo clase de tuplas de enteros para usar en 3
    public int primero;
    public int segundo;

    // Constructor para inicializar la tupla
    public Tupla(int primero, int segundo) {
        this.primero = primero;
        this.segundo = segundo;
    }

    public int getPrimero() {
        return primero;
    }

    public int getSegundo() {
        return segundo;
    }
    
    public void setPrimero(int primero) {
        this.primero = primero;
    }

    public void setSegundo(int segundo) {
        this.segundo = segundo;
    }
} 
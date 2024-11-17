package aed;

public class Traslado implements Comparable<Traslado> {
    
    private int id;
    private int origen;
    private int destino;
    private int gananciaNeta;
    private int timestamp;
    private int posHeapAntiguo; 
    private int posHeapRedituable;

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }
    public int getId(){
        return id;
    }

    public int getOrigen(){
        return origen;
    }

    public int getDestino(){
        return destino;
    }
    public int getGananciaNeta() {
        return gananciaNeta;
    }

    public int getTimestamp() {
        return timestamp;
    }


    public int getPosRedituable(){
        return posHeapRedituable;
    }

    public int getPosAntiguo(){
        return posHeapAntiguo;
    }
    //compare to para hacer maxheap de redituabilidad, comapra por ganacia y desempata por id, este es para el heap de reditaubilidad, armo otro para minheap
    @Override
    public int compareTo(Traslado other) {
        if (this.gananciaNeta != other.gananciaNeta) {
            return Integer.compare(other.gananciaNeta, this.gananciaNeta);
        } else {
            return Integer.compare(this.id, other.id);
        }
    }
}

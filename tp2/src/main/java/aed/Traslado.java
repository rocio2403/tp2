package aed;

public class Traslado implements Comparable<Traslado> {
    
    int id;
    int origen;
    int destino;
    int gananciaNeta;
    int timestamp;

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }
    public int getGananciaNeta() {
        return gananciaNeta;
    }

    public int getTimestamp() {
        return timestamp;
    }
 //compareto para hacer maxheap de redituabilidad, comapra por ganacia y desempata por id
    @Override
    public int compareTo(Traslado other) {
        if (this.gananciaNeta != other.gananciaNeta) {
            return Integer.compare(other.gananciaNeta, this.gananciaNeta);
        } else {
            return Integer.compare(this.id, other.id);
        }
    }
}

package aed;

import java.util.Comparator;

public class TimestampComparator implements Comparator<Traslado> {
    @Override
    public int compare(Traslado t1, Traslado t2) {
        return Integer.compare(t1.getTimestamp(), t2.getTimestamp());
    }
}
//como en traslado pusimos que se comapraran segun ganancia y desempataran segun id, armamos otra clase para no modificar la clase traslado y utilizar la comparacion de acuerdo al timenstamp para el minHeap

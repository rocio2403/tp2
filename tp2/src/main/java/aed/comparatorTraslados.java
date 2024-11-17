package aed;

import java.util.Comparator;

public class comparatorTraslados {
    
    public static final Comparator<Traslado> comparadorPorGanancia = new Comparator<Traslado>() {
        @Override
        public int compare(Traslado p1, Traslado p2) {
           
            return Integer.compare(p1.getGananciaNeta(), p2.getGananciaNeta());
        }
    };

    public static final Comparator<Traslado> comparadorPorTimestamp = new Comparator<Traslado>() {
        @Override
        public int compare(Traslado p1, Traslado p2) {
            
            return Integer.compare(p1.getTimestamp(), p2.getTimestamp());
        }
    };
}

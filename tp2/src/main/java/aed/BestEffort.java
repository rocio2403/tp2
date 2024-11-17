package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class BestEffort {
    private ArrayList<Ciudad> ciudades;
    private MaxHeap<Traslado> heapRedituable; 
    private MinHeap<Traslado> heapAntiguo;    
    private ArrayList<Integer> mayorGanancia; 
    private  ArrayList<Integer> mayorPerdida;  
    private MaxHeap<Ciudad> heapSuperavit;
    private  int mayorSuperavit;
    private int cantGanancia; 
    private int cantTraslados;

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        this.ciudades = new ArrayList<>(cantCiudades);
        for (int i = 0; i < cantCiudades; i++) {
            ciudades.add(new Ciudad(i,0,0,0)); 
        } 

        Comparator<Traslado> gananciaComparar = Comparator.comparing(Traslado :: getGananciaNeta);
        this.heapRedituable = new MaxHeap<>(traslados, traslados.length, gananciaComparar); //O(|T|) algoritmo de Floyd
        
        Comparator<Traslado> timeStampComparar = Comparator.comparing(Traslado :: getTimestamp);
        this.heapAntiguo = new MinHeap<>(traslados, traslados.length,timeStampComparar); //O(|T|) algoritmo de Floyd
        
        Ciudad[] arrayCiudades = ciudades.toArray(new Ciudad[0]);
        Comparator<Ciudad> superavitComparar = Comparator.comparing(Ciudad::getSuperavit);
        this.heapSuperavit = new MaxHeap<>(arrayCiudades, cantCiudades,superavitComparar); //O(|C|)
       
        this.mayorSuperavit = 0;

        this.mayorGanancia = new ArrayList<Integer>(); //Las actualizo con el auxiliar de abajo
        this.mayorPerdida = new ArrayList<Integer>();
        
        this.cantGanancia = 0;
        this.cantTraslados = 0;
       
    }// Complejidad final = O(|C|+ |T|)



    public void registrarTraslados(Traslado[] traslados){
        for (Traslado traslado : traslados){  //O(|traslados|)
            this.heapRedituable.encolar(traslado); //O(log(T))
            this.heapAntiguo.encolar(traslado); //O(log(T))
        } 
    } //complejidad: O(|traslados|log(t))

   

    public int[] despacharMasRedituables(int n){
        int i = 0;
        int[] res = new int[n]; //renombrar //O(n)
        int[] indices = new int[n];

        while (i < n && heapRedituable.cardinal() > 0){
            Traslado t = heapRedituable.desencolar();  //O(log(T))
           
            res[i] = t.getId();    
            actualizarInfoCiudad(t);    //O(Log(C))   Aca actualizo ambos heaps y la informacion de la lista de superavit  
            maxSuperavit(); //O(1)      
           // indices[i] =t.getPosAntiguo();
           
            
            i++;
        } //Complejidad bucle = O(n(log(T) + log(C)))
      //  sincronizarHeapAntiguo(indices);
        actualizarListaGananciasYPerdidas(); //O(|C|)
        return res ;
       //Complejidad = O(|C|) + O(n(Log(T) + log(C))) =  O(n(logT + logC))
    }

    public int[] despacharMasAntiguos(int n){
        int i = 0;
        int[] res = new int[n]; //renombrar
       
        while (i< n && heapAntiguo.cardinal() > 0){
            Traslado t  = this.heapAntiguo.desencolar(); // O(log(T))
              // O(1)
            
            
            res[i] = t.getId();    
            actualizarInfoCiudad(t);    //O(Log(C)) 
            maxSuperavit(); //O(1)

            i++;
        }   //Complejidad bucle = O(n(log(T) + log(C)))
        
        actualizarListaGananciasYPerdidas(); //O(|C|)
        return res ;
    } //Complejidad = O(|C|) + O(n(Log(T) + log(C))) =  O(n(logT + logC))

    
    
    public int ciudadConMayorSuperavit(){
        return mayorSuperavit;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
         return mayorGanancia; 
        }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        
        return mayorPerdida;
    }

    public int gananciaPromedioPorTraslado(){
        return cantGanancia/cantTraslados;
    }

    /* ====================================================================================
     *                  FUNCIONES AUXILIARES
     * ====================================================================================
    */
    public void actualizarListaGananciasYPerdidas() { 
        int mayorGananciaActual = -1; 
        int mayorPerdidaActual = -1;
        mayorGanancia.clear();
        mayorPerdida.clear();
        if (!mayorGanancia.isEmpty()) {
            int idCiudadGanancia = mayorGanancia.get(0);
            mayorGananciaActual = ciudades.get(idCiudadGanancia).getGanancias();
        }
        if (!mayorPerdida.isEmpty()) {
            int idCiudadPerdida = mayorPerdida.get(0);
            mayorPerdidaActual = ciudades.get(idCiudadPerdida).getPerdidas();
        }
    
        for (Ciudad ciudad : ciudades) { // O(C)
            int ganancia = ciudad.getGanancias();
            if (ganancia > mayorGananciaActual) {
                mayorGananciaActual = ganancia;
                mayorGanancia.clear(); // Borrar solo si encontramos un mayor nuevo
                mayorGanancia.add(ciudad.getId());
            } else if (ganancia == mayorGananciaActual && !mayorGanancia.contains(ciudad.getId())) {
                // Añadir solo si no está ya presente
                mayorGanancia.add(ciudad.getId());
            }
    
            int perdida = ciudad.getPerdidas();
            if (perdida > mayorPerdidaActual) {
                mayorPerdidaActual = perdida;
                mayorPerdida.clear(); // Borrar solo si encontramos una pérdida mayor nueva
                mayorPerdida.add(ciudad.getId());
            } else if (perdida == mayorPerdidaActual && !mayorPerdida.contains(ciudad.getId())) {
                // Añadir solo si no está ya presente
                mayorPerdida.add(ciudad.getId());
            }
        }
    }
    
    public void actualizarInfoCiudad(Traslado t){
                                     
        int indiceCiudadGana = t.getOrigen();  //O(1)
        int ganancia = t.getGananciaNeta(); //O(1)
        int indiceCiudadPierde = t.getDestino();//O(1)
        int perdida = t.getGananciaNeta();//O(1)
        
        this.ciudades.get(indiceCiudadGana).agregarGanancias(ganancia); //O(1)
        this.ciudades.get(indiceCiudadPierde).agregarPerdidas(perdida); //O(1)
        
        cantGanancia += ganancia; //O(1)
        cantTraslados++;//O(1)
        
       actualizarSuperavit(indiceCiudadGana);
       actualizarSuperavit(indiceCiudadPierde);
        
    } //Complejidad = O(log(C))

    public void actualizarSuperavit(int indiceCiudad) {
        int altura = this.heapSuperavit.cardinal();
        Ciudad[] arregloHeap = this.heapSuperavit.cola();
        if (indiceCiudad >= 0 && indiceCiudad < altura) {
            arregloHeap[indiceCiudad].actualizarSuperavit();
    
            if (heapSuperavit.compare(heapSuperavit.prioridad(indiceCiudad), heapSuperavit.prioridad(MaxHeap.padre(indiceCiudad))) > 0) {
                heapSuperavit.subir(indiceCiudad); // O(log(C))
            } else {
                heapSuperavit.bajar(indiceCiudad); // O(log(C))
            }
        }
    } // Complejidad: O(log(C))
    
    
    private void maxSuperavit(){
        Ciudad candidato = heapSuperavit.proximo(); //O(1)
        if(this.ciudades.get(this.mayorSuperavit).getSuperavit() < candidato.getSuperavit()){
            this.mayorSuperavit = candidato.getId();
        }
        else if(this.ciudades.get(this.mayorSuperavit).getSuperavit() == candidato.getSuperavit()){
            if(candidato.getId()<this.mayorSuperavit ){
                this.mayorSuperavit = candidato.getId();
            }
        }
    }//O(1)

    private void sincronizarHeapAntiguo(int[] indices){
        //en esta funcion le paso los elementos a borrar
        for(int index : indices){
            this.heapAntiguo.eliminar(index);

        }
    }

    private void sincronizarTrasladosEnMaxHeap(ArrayList<Integer> cambios){
        for (int i = 0; i < cambios.size(); i++) {
           int posicionActualizada = cambios.get(i);
           Traslado t = this.heapRedituable.prioridad(posicionActualizada);
           t.setPosRedituable(posicionActualizada);   
        } 
    }

    private void sincronizarTrasladosEnMinHeap(ArrayList<Integer> cambios){
        for (int i = 0; i < cambios.size(); i++) {
           int posicionActualizada = cambios.get(i);
           Traslado t = this.heapAntiguo.prioridad(posicionActualizada);
           t.setPosRedituable(posicionActualizada);   
        } 
    }
    
    
    // private void actualizarListaGananciasYPerdidas() { 
    //     int mayorGananciaActual = Integer.MIN_VALUE; 
    //     int mayorPerdidaActual = Integer.MIN_VALUE;
    
    //     mayorGanancia.clear();
    //     mayorPerdida.clear();
    
    //     for (Ciudad ciudad : ciudades) { // O(C)
    //         int ganancia = ciudad.getGanancias();
    //         if (ganancia > mayorGananciaActual) {
    //             mayorGananciaActual = ganancia;
    //             mayorGanancia.clear(); // Limpiar y agregar nueva mejor ganancia
    //             mayorGanancia.add(ciudad.getId());
    //         } else if (ganancia == mayorGananciaActual) {
    //             mayorGanancia.add(ciudad.getId());
    //         }
    
    //         int perdida = ciudad.getPerdidas();
    //         if (perdida > mayorPerdidaActual) {
    //             mayorPerdidaActual = perdida;
    //             mayorPerdida.clear(); // Limpiar y agregar nueva mejor pérdida
    //             mayorPerdida.add(ciudad.getId());
    //         } else if (perdida == mayorPerdidaActual) {
    //             mayorPerdida.add(ciudad.getId());
    //         }
    //     }
    // }
    
    // private void actualizarListaGananciasYPerdidas() { 
    //     int mayorGananciaActual = -1; 
    //     int mayorPerdidaActual = -1;
    
    //     // Verifica si las listas actuales no están vacías y obtén el valor máximo.
    //     if (!mayorGanancia.isEmpty()) {
    //         int idCiudadGanancia = mayorGanancia.get(0);
    //         mayorGananciaActual = ciudades.get(idCiudadGanancia).getGanancias();
    //     }
    //     if (!mayorPerdida.isEmpty()) {
    //         int idCiudadPerdida = mayorPerdida.get(0);
    //         mayorPerdidaActual = ciudades.get(idCiudadPerdida).getPerdidas();
    //     }
    
    //     // Itera por todas las ciudades para encontrar la mayor ganancia y pérdida.
    //     for (Ciudad ciudad : ciudades) { // O(C)
    //         int ganancia = ciudad.getGanancias();
    //         if (ganancia > mayorGananciaActual) {
    //             mayorGananciaActual = ganancia;
    //             mayorGanancia.clear(); // Nueva ganancia máxima, reinicia la lista.
    //             mayorGanancia.add(ciudad.getId());
    //         } else if (ganancia == mayorGananciaActual) {
    //             mayorGanancia.add(ciudad.getId()); // Misma ganancia máxima, añade el ID.
    //         }
    
    //         int perdida = ciudad.getPerdidas();
    //         if (perdida > mayorPerdidaActual) {
    //             mayorPerdidaActual = perdida;
    //             mayorPerdida.clear(); // Nueva pérdida máxima, reinicia la lista.
    //             mayorPerdida.add(ciudad.getId());
    //         } else if (perdida == mayorPerdidaActual) {
    //             mayorPerdida.add(ciudad.getId()); // Misma pérdida máxima, añade el ID.
    //         }
    //     }
    // }
    // Método para eliminar un traslado de heapAntiguo

    
    
        
}

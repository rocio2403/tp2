package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class BestEffort {
    private ArrayList<Ciudad> ciudades;
    public  MaxHeap<Traslado> heapRedituable; 
    public MinHeap<Traslado> heapAntiguo;    
    private ArrayList<Integer> mayorGanancia; 
    private  ArrayList<Integer> mayorPerdida;  
    private MaxHeap<Ciudad> heapSuperavit;
    private  int mayorSuperavit;
    private int cantGanancia; 
    private int cantTraslados;

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        this.ciudades = new ArrayList<>(cantCiudades);
        for (int i = 0; i < cantCiudades; i++) {
            ciudades.add(new Ciudad(i, 0, 0, 0)); 
        } 
    
        // Definir comparadores
        Comparator<Traslado> comparadorPorGN = comparatorTraslados.comparadorPorGanancia;
        this.heapRedituable = new MaxHeap<>(comparadorPorGN, traslados.length); // Iniciar con la cantidad de traslados
    
        Comparator<Traslado> timeStampComparar = comparatorTraslados.comparadorPorTimestamp;
        this.heapAntiguo = new MinHeap<>(timeStampComparar, traslados.length); // Iniciar con la cantidad de traslados
        
        // Añadir los traslados a los heaps
        for (Traslado t : traslados) {
            heapRedituable.encolar(t);
            heapAntiguo.encolar(t);
        }
    
        // Comparador por Superavit para el MaxHeap de ciudades
        Comparator<Ciudad> comparadorPorSuperavit = Comparator.comparing(Ciudad::getSuperavit).reversed();
        this.heapSuperavit = new MaxHeap<>(comparadorPorSuperavit, cantCiudades); // Iniciar con la cantidad de ciudades
    
        // Añadir ciudades al heap
        for (Ciudad ciudad : ciudades) {
            heapSuperavit.encolar(ciudad);
        }
        
        this.mayorSuperavit = -1;

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

        while (i < n && heapRedituable.getCardinal() > 0){
            Traslado t = heapRedituable.desencolar();  //O(log(T))
           
            res[i] = t.getId();    
            actualizarInfoCiudad(t);    //O(Log(C))   Aca actualizo ambos heaps y la informacion de la lista de superavit  
             
           indices[i] =t.getPosAntiguo();
           
            
            i++;
        } //Complejidad bucle = O(n(log(T) + log(C)))
       
       sincronizarHeapAntiguo(indices);
        actualizarListaGananciasYPerdidas(); //O(|C|)
       // maxSuperavit();
        return res ;
       //Complejidad = O(|C|) + O(n(Log(T) + log(C))) =  O(n(logT + logC))
    }

    public int[] despacharMasAntiguos(int n){
        int i = 0;
        int[] res = new int[n]; //renombrar
        int[] indices = new int[n];
        while (i< n && heapAntiguo.getCardinal() > 0){
            Traslado t  = this.heapAntiguo.desencolar(); // O(log(T))
              // O(1)
            indices[i]=t.getPosRedituable();
            
            res[i] = t.getId();    
            actualizarInfoCiudad(t);    //O(Log(C)) 
           

            i++;
        }   //Complejidad bucle = O(n(log(T) + log(C)))
        sincronizarHeapRedituable(indices);
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
        this.ciudades.get(indiceCiudadPierde).agregarPerdidas(perdida); //O(1) //agregar ganancias y perdidas ya actualiza el superavit
        //solo hay que heapificar
        
        cantGanancia += ganancia; //O(1)
        cantTraslados++;//O(1)
        
       actualizarSuperavit(indiceCiudadGana);
       actualizarSuperavit(indiceCiudadPierde);
        
    } //Complejidad = O(log(C))

      private void sincronizarHeapAntiguo(int[] indices){
        //en esta funcion le paso los elementos a borrar
        for(int index : indices){
            this.heapAntiguo.eliminarPorPosicion(index);

        }
    }

    private void sincronizarHeapRedituable(int[] indices){
        //en esta funcion le paso los elementos a borrar
        for(int index : indices){
            this.heapRedituable.eliminarPorPosicion(index);

        }
    }

    public void actualizarSuperavit(int indiceCiudad) {
    }
    
    
//En caso de empate, devuelve la que tiene menor identificador
    private void maxSuperavit(){
        Ciudad candidato = heapSuperavit.getMax(); //O(1)
        int actual= this.ciudades.get(this.mayorSuperavit).getSuperavit();
        if(actual < candidato.getSuperavit()){
            this.mayorSuperavit = candidato.getId();
        }
        else if(actual == candidato.getSuperavit()){
            if(candidato.getId()<this.mayorSuperavit ){
                this.mayorSuperavit = candidato.getId();
            }
        }   
    //O(1)
    }
}    
    
        


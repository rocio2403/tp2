package aed;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

public class BestEffort {
    private ArrayList<Ciudad> ciudades;
    private MaxHeap<Traslado> heapRedituable; 
    private MinHeap<Traslado> heapAntiguo;    
    private ArrayList<Integer> mayorGanancia; 
    private  ArrayList<Integer> mayorPerdida;  
 // NO HACE FALTA, MIRO SUPERAVIT DE CIUDADES   private ArrayList<Integer>superavitPorCiudad;
    private MaxHeap<Ciudad> heapSuperavit;
    
    private MaxHeap<Ciudad> gananciasHeap;
    private MaxHeap<Ciudad> perdidaHeap;
    private  int mayorSuperavit;//devolver en el 5
    private int cantGanancia; //devolver en el 8 ganancias/traslados
    private int cantTraslados;
   

    public BestEffort(int cantCiudades, Traslado[] traslados) {
        this.ciudades = new ArrayList<>(cantCiudades);
        for (int i = 0; i < cantCiudades; i++) {
            ciudades.add(new Ciudad(i,0,0,0)); 
        } //O(C)

        Comparator<Traslado> gananciaComparar = Comparator.comparing(Traslado::getGananciaNeta);
        this.heapRedituable = new MaxHeap<>(traslados, traslados.length,gananciaComparar); //O(|T|) algoritmo de Floyd
        
        Comparator<Traslado> timeStampComparar = Comparator.comparing(Traslado::getTimestamp);
        this.heapAntiguo = new MinHeap<>(traslados, traslados.length,timeStampComparar); //O(|T|) algoritmo de Floyd
        
        Comparator<Ciudad> gananciasComparar = Comparator.comparingInt(Ciudad::getGanancias);
        
        Ciudad[] arrayCiudades = ciudades.toArray(new Ciudad[0]);
        this.gananciasHeap = new MaxHeap<>(arrayCiudades, arrayCiudades.length,gananciasComparar); //O(|C|)
        
        Comparator<Ciudad> perdidasComparar = Comparator.comparingInt(Ciudad::getPerdidas);
        this.perdidaHeap = new MaxHeap<>(arrayCiudades, cantCiudades,perdidasComparar); //O(|C|)

        Comparator<Ciudad> superavitComparar = Comparator.comparingInt(Ciudad::getSuperavit);
        this.perdidaHeap = new MaxHeap<>(arrayCiudades, cantCiudades,superavitComparar); //O(|C|)

        //this.superavitPorCiudad = new ArrayList<>(cantCiudades);
        //for(int j = 0; j < cantCiudades; j++){ //O(|C|)
          //  superavitPorCiudad.add(0);
        //} // O(C)
        this.mayorSuperavit = 0;

        this.mayorGanancia = new ArrayList<Integer>();
        this.mayorPerdida = new ArrayList<Integer>();
        
        this.cantGanancia = 0;
        this.cantTraslados = 0;
       
    }// complejidad = O(|C|+ |T|)



    public void registrarTraslados(Traslado[] traslados){
        for (Traslado traslado : traslados){  //O(|traslados|)
            this.heapRedituable.encolar(traslado); //O(log(T))
            this.heapAntiguo.encolar(traslado); //O(log(T))
        } 
    } //complejidad: O(|traslados|log(t))

   

    public int[] despacharMasRedituables(int n){
        int i = 0;
        int[] res = new int[n]; //renombrar
    
        while (i< n){
            Traslado t  = this.heapRedituable.desencolar();  //O(log(T))
            res[i] = t.getId();    
            actualizarInfoCiudad(t);    //O(Log(C))   Aca actualizo ambos heaps y la informacion de la lista de superavit  
                   
        }   //Complejidad bucle = O(n(log(T) + log(C)))
        
       // actualizarSuperavit(); //O(|C|)
        actualizarEstadisticas(); //O(|C|)
        maxSuperavit();
         //aca actualizo las varibles mayorGanancia,mayorPerdida, para no arruinar la complejidad deberia ser O(|C|)
        return res ;
    }


    public int[] despacharMasAntiguos(int n){
        int i = 0;
        int[] res = new int[n]; //renombrar
    
        while (i< n){
            Traslado t  = this.heapAntiguo.desencolar();  //O(log(T))
            res[i] = t.getId();    
            actualizarInfoCiudad(t);    //O(Log(C)) 
        }   //Complejidad bucle = O(n(log(T) + log(C))) 
        
        actualizarEstadisticas(); //
        maxSuperavit(); //O(1)
        return res ;
    }
    
    
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

        //ESTE CODIGO GENERA COMPLEJIDAD O(CLOGC)
       /* if(mayorGanancia.size() == 0){
            MaxHeap<Ciudad> copiaHeapGanancia = new MaxHeap<>(gananciasHeap);
            Ciudad cMaxGan = copiaHeapGanancia.desencolar(); //desencola y elimina el primero O(logC)
            this.mayorGanancia.add(cMaxGan.getId());
            while(cMaxGan.getGanancias() == copiaHeapGanancia.proximo().getGanancias()){ //O(ClogC) //PREGUNTAR
                Ciudad temp = copiaHeapGanancia.desencolar();
                this.mayorGanancia.add(temp.getId());
            }


        }*/
       
    
    private void actualizarEstadisticas() {
        int mayorGananciaActual = -1;
        int mayorPerdidaActual = -1;

        mayorGanancia.clear();
        mayorPerdida.clear();

        for (Ciudad ciudad : ciudades) {//OPTIMIZAR
            int ganancia = ciudad.getGanancias();
            if (ganancia > mayorGananciaActual) {
                mayorGananciaActual = ganancia;
                mayorGanancia.clear();  // LimpiO la lista si encontramos una nueva mayor ganancia
                mayorGanancia.add(ciudad.getId());
            } else if (ganancia == mayorGananciaActual) {
                mayorGanancia.add(ciudad.getId());  // Si es empate, agregarla a la lista
            }

            int perdida = ciudad.getPerdidas();
            if (perdida < mayorPerdidaActual) {
                mayorPerdidaActual = perdida;
                mayorPerdida.clear();  // Limpiar la lista si encontramos una nueva mayor pérdida
                mayorPerdida.add(ciudad.getId());
            } else if (perdida == mayorPerdidaActual) {
                mayorPerdida.add(ciudad.getId());  // Si es empate, agregarla a la lista
            }
        }
    }
    

 
    public void actualizarPerdidasCiudad(int indiceCiudad,int nuevaPerdida){
        int altura = this.perdidaHeap.cardinal();
        Ciudad[] arregloHeap = this.perdidaHeap.cola();
        if (indiceCiudad >= 0 && indiceCiudad < altura) {
            
            arregloHeap[indiceCiudad].agregarPerdidas(nuevaPerdida);
    
            if (perdidaHeap.prioridad(indiceCiudad).compareTo(perdidaHeap.prioridad(perdidaHeap.padre(indiceCiudad))) > 0) {
                perdidaHeap.subir(indiceCiudad); //O(log(C))
            } else {
                perdidaHeap.bajar(indiceCiudad); //O(log(C))
            }
        }
    } // Complejidad : O(Log(C))

    public void actualizarGananciaCiudad(int indiceCiudad, int nuevaGanancia) {
        int altura = this.gananciasHeap.cardinal();
        Ciudad[] arregloHeap = this.gananciasHeap.cola();
        if (indiceCiudad >= 0 && indiceCiudad < altura) {
            
            arregloHeap[indiceCiudad].agregarGanancias(nuevaGanancia);
    
            if (gananciasHeap.prioridad(indiceCiudad).compareTo(gananciasHeap.prioridad(gananciasHeap.padre(indiceCiudad))) > 0) {
                gananciasHeap.subir(indiceCiudad); //O(log(C))
            } else {
                gananciasHeap.bajar(indiceCiudad); //O(log(C))
            }
        }
    } //    Complejidad : O(Log(C))

    private void actualizarInfoCiudad(Traslado t){
                                     
        int indiceCiudadGana = t.getOrigen();  //O(1)
        int ganancia = t.getGananciaNeta(); //O(1)
        int indiceCiudadPierde = t.getDestino();//O(1)
        int perdida = t.getGananciaNeta();//O(1)
        
        this.ciudades.get(indiceCiudadGana).agregarGanancias(ganancia); //O(1)
        this.ciudades.get(indiceCiudadPierde).agregarGanancias(perdida); //O(1)

        this.ciudades.get(indiceCiudadGana).agregarGanancias(ganancia); //O(1)
        this.ciudades.get(indiceCiudadPierde).agregarPerdidas(perdida); //O(1)
        
        cantGanancia += ganancia; //O(1)
        cantTraslados++;//O(1)

        //int superavit_gana = this.ciudades.get(indiceCiudadGana).getSuperavit(); //O(1)
        //int superavit_pierde = this.ciudades.get(indiceCiudadPierde).getSuperavit(); //O(1)
                
        actualizarSuperavit(indiceCiudadGana);
        actualizarSuperavit(indiceCiudadPierde);
        
       // this.superavitPorCiudad.set(indiceCiudadPierde, superavit_pierde); //O(1)
        //this.superavitPorCiudad.set(indiceCiudadGana, superavit_gana); //O(1)

        actualizarGananciaCiudad(indiceCiudadGana, ganancia); //O(log(C))
        actualizarPerdidasCiudad(indiceCiudadPierde, perdida);//O(log(C))

    } //O(log(C))

    public void actualizarSuperavit(int indiceCiudad) {
        int altura = this.heapSuperavit.cardinal();
        Ciudad[] arregloHeap = this.heapSuperavit.cola();
        if (indiceCiudad >= 0 && indiceCiudad < altura) {
            
            arregloHeap[indiceCiudad].actualizarSuperavit();
    
            if (heapSuperavit.prioridad(indiceCiudad).compareTo(heapSuperavit.prioridad(heapSuperavit.padre(indiceCiudad))) > 0) {
                heapSuperavit.subir(indiceCiudad); //O(log(C))
            } else {
                heapSuperavit.bajar(indiceCiudad); //O(log(C))
            }
        }

    } //Complejidad: O(log(C))
    
    private void maxSuperavit(){
        Ciudad candidato = heapSuperavit.proximo();
        if(this.ciudades.get(this.mayorSuperavit).getSuperavit() < candidato.getSuperavit()){
            this.mayorSuperavit = candidato.getId();
        }
        else if(this.ciudades.get(this.mayorSuperavit).getSuperavit() == candidato.getSuperavit()){
            if(candidato.getId()<this.mayorSuperavit ){
                this.mayorSuperavit = candidato.getId();
            }
        }
    }//O(1)
    
    
}

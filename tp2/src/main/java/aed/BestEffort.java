package aed;

import java.util.ArrayList;

public class BestEffort {
    //Completar atributos privados
    private ArrayList<Ciudad> ciudades;
    private MaxHeap<Traslado> heapRedituable; 
    private MinHeap<Traslado> heapAntiguo;    
    private ArrayList<Integer> mayorGanancia;
    private  ArrayList<Integer> mayorPerdida;


    public BestEffort(int cantCiudades, Traslado[] traslados) {
        this.ciudades = new ArrayList<>(cantCiudades);
        this.heapRedituable = new MaxHeap<>(traslados, traslados.length);
        this.heapAntiguo = new MinHeap<>(traslados, traslados.length);
        this.mayorGanancia = new ArrayList<Integer>();
        this.mayorPerdida = new ArrayList<Integer>();
        //listo ya inicalice todo, ahora deberia contruir cosas, ya construi los heaps
        //deberia acutalizarme las demas variables, empecemos con las ciudades
        //debo actualizar  cada vez que aparece en origen ganancia neta como ganancia y si aparece en destino, como perdida.
        actualizarInfoCiudades(this.ciudades,traslados);        
       
    }
    public ArrayList<Ciudad> actualizarInfoCiudades(ArrayList<Ciudad> ciudades,Traslado[] traslados ){
        //aca recorro y actualizo las variables
        return ciudades ;
    }


    public void registrarTraslados(Traslado[] traslados){
        // Implementar
    }

    public int[] despacharMasRedituables(int n){
        // Implementar
        return null;
    }

    public int[] despacharMasAntiguos(int n){
        // Implementar
        return null;
    }

    public int ciudadConMayorSuperavit(){
        // Implementar
        return 0;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        // Implementar
        return null;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        // Implementar
        return null;
    }

    public int gananciaPromedioPorTraslado(){
        // Implementar
        return 0;
    }
    
}

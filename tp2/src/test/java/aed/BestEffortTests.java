package aed;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BestEffortTests {

    int cantCiudades;
    Traslado[] listaTraslados;
    
    ArrayList<Integer> actual;


    @BeforeEach
    void init(){
        //Reiniciamos los valores de las ciudades y traslados antes de cada test
        cantCiudades = 7;
        listaTraslados = new Traslado[] {
                                            new Traslado(1, 0, 1, 100, 10),
                                            new Traslado(2, 0, 1, 400, 20),
                                            new Traslado(3, 3, 4, 500, 50),
                                            new Traslado(4, 4, 3, 500, 11),
                                            new Traslado(5, 1, 0, 1000, 40),
                                            new Traslado(6, 1, 0, 1000, 41),
                                            new Traslado(7, 6, 3, 2000, 42)
                                        };
    }

    void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        assertEquals(s1.size(), s2.size());
        for (int e1 : s1) {
            boolean encontrado = false;
            for (int e2 : s2) {
                if (e1 == e2) encontrado = true;
            }
            assertTrue(encontrado, "No se encontró el elemento " +  e1 + " en el arreglo " + s2.toString());
        }
    }

    @Test
    void despachar_con_mas_ganancia_de_a_uno(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(1);
        
        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 3)), sis.ciudadesConMayorPerdida());
    }
    
    @Test
    void despachar_con_mas_ganancia_de_a_varios(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

    }
    
    @Test
    void despachar_mas_viejo_de_a_uno(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        
        sis.despacharMasAntiguos(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 3)), sis.ciudadesConMayorPerdida());
    }
    
    @Test
    void despachar_mas_viejo_de_a_varios(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);
        
        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());
        
    }
    
    @Test
    void despachar_mixtos(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(3);
        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());
        
    }
    
    @Test
    void agregar_traslados(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        Traslado[] nuevos = new Traslado[] {
            new Traslado(8, 0, 1, 10001, 5),
            new Traslado(9, 0, 1, 40000, 2),
            new Traslado(10, 0, 1, 50000, 3),
            new Traslado(11, 0, 1, 50000, 4),
            new Traslado(12, 1, 0, 150000, 1)
        };

        sis.registrarTraslados(nuevos);

        sis.despacharMasAntiguos(4);
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());

    }
    
    @Test
    void promedio_por_traslado(){
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasAntiguos(3);
        assertEquals(333, sis.gananciaPromedioPorTraslado());

        sis.despacharMasRedituables(3);
        assertEquals(833, sis.gananciaPromedioPorTraslado());

        Traslado[] nuevos = new Traslado[] {
            new Traslado(8, 1, 2, 1452, 5),
            new Traslado(9, 1, 2, 334, 2),
            new Traslado(10, 1, 2, 24, 3),
            new Traslado(11, 1, 2, 333, 4),
            new Traslado(12, 2, 1, 9000, 1)
        };

        sis.registrarTraslados(nuevos);
        sis.despacharMasRedituables(6);

        assertEquals(1386, sis.gananciaPromedioPorTraslado());
        

    }

    @Test
    void mayor_superavit(){
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 3, 4, 1, 7),
            new Traslado(7, 6, 5, 40, 6),
            new Traslado(6, 5, 6, 3, 5),
            new Traslado(2, 2, 1, 41, 4),
            new Traslado(3, 3, 4, 100, 3),
            new Traslado(4, 1, 2, 30, 2),
            new Traslado(5, 2, 1, 90, 1)
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasAntiguos(1);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(2);
        assertEquals(3, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(3);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(1);
        assertEquals(2, sis.ciudadConMayorSuperavit());

    }

        @Test
    void test_un_traslado(){
        Traslado[] nuevos = new Traslado[] {
            new Traslado(1, 6, 5, 40, 6),
            new Traslado(0, 3, 4, 100, 7),
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasAntiguos(1);
        
        assertEquals(3, sis.ciudadConMayorSuperavit());

         sis.despacharMasAntiguos(2);
         assertEquals(3, sis.ciudadConMayorSuperavit());

        // sis.despacharMasAntiguos(3);
        //  assertEquals(2, sis.ciudadConMayorSuperavit());

        // sis.despacharMasAntiguos(1);
        // assertEquals(2, sis.ciudadConMayorSuperavit());

    }
             
            //     // Ejemplo de traslados
            //     Traslado[] traslados = {
            //         new Traslado(1, 0, 1, 300, 10),
            //         new Traslado(2, 1, 2, 200, 20),
            //         new Traslado(3, 2, 3, 400, 30),
            //         new Traslado(4, 3, 4, 100, 40),
            //     };
        
            //     // Ejemplo de ciudades
            //     Ciudad[] ciudades = {
            //         new Ciudad(0, 1000, 800,800),
            //         new Ciudad(1, 1200, 1000,1000),
            //         new Ciudad(2, 800, 700,700),
            //         new Ciudad(3, 1500, 1400,1400),
            //     };
        
            //     // Crear MaxHeap para traslados redituables
            //     Comparator<Traslado> comparadorPorGanancia = Comparator.comparing(Traslado::getGananciaNeta).reversed();
            //     MaxHeap<Traslado> heapRedituable = new MaxHeap<>(comparadorPorGanancia);
        
            //     for (Traslado traslado : traslados) {
            //         heapRedituable.add(traslado);
            //     }
        
            //     System.out.println("Heap Redituable (por ganancia neta):");
            //     while (!heapRedituable.isEmpty()) {
            //         System.out.println(heapRedituable.removeMax());
            //     }
        
            //     // Crear MaxHeap para ciudades con mayor superávit
            //     Comparator<Ciudad> comparadorPorSuperavit = Comparator.comparing(Ciudad::getSuperavit).reversed();
            //     MaxHeap<Ciudad> heapSuperavit = new MaxHeap<>(comparadorPorSuperavit);
        
            //     for (Ciudad ciudad : ciudades) {
            //         heapSuperavit.add(ciudad);
            //     }
        
            //     System.out.println("\nHeap Superavit (por superávit):");
            //     while (!heapSuperavit.isEmpty()) {
            //         System.out.println(heapSuperavit.removeMax());
            //     }
            // }
            // Traslado[] traslados = {
            //     new Traslado(1, 0, 1, 100, 5),
            //     new Traslado(2, 1, 2, 200, 3),
            //     new Traslado(3, 2, 3, 50, 8),
            //     new Traslado(4, 3, 4, 300, 2)
            // };
    
            // // Heap por ganancia neta
            // Comparator<Traslado> comparadorPorGN = comparatorTraslados.comparadorPorGanancia;
            // MaxHeap<Traslado> heapPorGanancia = new MaxHeap<>(comparadorPorGN);
            // for (Traslado t : traslados) {
            //     heapPorGanancia.add(t);
            // }
    
            // System.out.println("Heap por Ganancia Neta:");
            // while (!heapPorGanancia.isEmpty()) {
            //     System.out.println(heapPorGanancia.removeMax());
            // }
    
            // // Heap por timestamp (usando un comparador)
            // Comparator<Traslado> comparadorPorTimestamp = comparatorTraslados.comparadorPorTimestamp;
            // MaxHeap<Traslado> heapPorTimestamp = new MaxHeap<>(comparadorPorTimestamp);
    
            // for (Traslado t : traslados) {
            //     heapPorTimestamp.add(t);
            // }
    
            // System.out.println("\nHeap por Timestamp:");
            // while (!heapPorTimestamp.isEmpty()) {
            //     System.out.println(heapPorTimestamp.removeMax());
            
       

} //ANDAN MAL LOS COMPARADORES

//     public static void main(String[] args) {
//     // Crear objetos Traslado
//     Traslado t1 = new Traslado(1, 0, 1, 100, 5);
//     Traslado t2 = new Traslado(2, 1, 2, 200, 3);
//     Traslado t3 = new Traslado(3, 2, 3, 50, 8);
//     Traslado t4 = new Traslado(4, 3, 4, 300, 2);

//     // Crear comparadores
//     Comparator<Traslado> gananciaComparar = Comparator.comparing(Traslado::getGananciaNeta);
//     Comparator<Traslado> timeStampComparar = Comparator.comparing(Traslado::getTimestamp);

//     // Imprimir resultados de las comparaciones
//     System.out.println("Comparador de ganancia (t2 vs t1): " + gananciaComparar.compare(t2, t1)); // Debería ser positivo
//     System.out.println("Comparador de ganancia (t3 vs t4): " + gananciaComparar.compare(t3, t4)); // Debería ser negativo
//     System.out.println("Comparador de timestamp (t2 vs t4): " + timeStampComparar.compare(t2, t4)); // Debería ser positivo
//     System.out.println("Comparador de timestamp (t3 vs t1): " + timeStampComparar.compare(t3, t1)); // Debería ser positivo

//     // Verificar comparadores inversos
//     Comparator<Traslado> inversoGanancia = gananciaComparar.reversed();
//     Comparator<Traslado> inversoTimestamp = timeStampComparar.reversed();

//     System.out.println("Comparador de ganancia inverso (t1 vs t2): " + inversoGanancia.compare(t1, t2)); // Debería ser negativo
//     System.out.println("Comparador de timestamp inverso (t4 vs t2): " + inversoTimestamp.compare(t4, t2)); // Debería ser negativo
// }
   

  
    


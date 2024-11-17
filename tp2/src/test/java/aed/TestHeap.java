package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class TestHeap {


    public static void main(String[] args) {
        Integer[] initialArray = {10, 20, 15, 30, 40};
        int cardinal = 5;

        // Crear el Heap con el arreglo inicial
        Heap<Integer> Heap = new Heap<>(initialArray, cardinal);

        // Test 1: Verificar el heap inicial
        System.out.println("Estado inicial del Heap: " + Heap);
        assert Heap.proximo() == 40 : "El mayor elemento debe ser 40";

        // Test 2: Encolar un elemento nuevo y verificar su posición
        Heap.encolar(50);
        System.out.println("Estado del Heap tras encolar 50: " + Heap);
        assert Heap.proximo() == 50 : "El mayor elemento debe ser 50 después de encolar";

        // Test 3: Desencolar elementos y verificar el orden
        System.out.println("Desencolando todos los elementos:");
        Integer[] sortedArray = new Integer[Heap.cardinal()];
        int index = 0;
        
        while (Heap.cardinal() > 0) {
            sortedArray[index++] = Heap.desencolar();
        }

        System.out.println("Array ordenado al desencolar (de mayor a menor): " + Arrays.toString(sortedArray));
        
        // Comprobar que el array está ordenado de mayor a menor
        for (int i = 0; i < sortedArray.length - 1; i++) {
            assert sortedArray[i] >= sortedArray[i + 1] : "El Heap no está ordenado correctamente";
        }

        System.out.println("Todos los tests pasaron correctamente.");
    }


    boolean es_max_heap(Heap<Integer> max_heap){
        Integer n = max_heap.cardinal();
        Integer[] heap_array = max_heap.cola();
        
        Integer i = 0;
        while (i < n / 2) {
            int hijo_izq = 2 * i + 1;
            int hijo_der = 2 * i + 2;

            boolean dentro_de_rango = hijo_izq < n && hijo_der < n;
            boolean hijos_mayores_que_padre = heap_array[i] < heap_array[hijo_izq] &&  heap_array[i] < heap_array[hijo_der];

            if (dentro_de_rango && hijos_mayores_que_padre) {
                return false;
            }

            i++;
        }

        return true;
    } 

    @Test
    void iniciar_heap(){
        Integer[] datos_test_1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Heap<Integer> heap = new Heap<>(datos_test_1, datos_test_1.length);
        assertEquals(10, heap.cardinal());
        assertEquals(true, es_max_heap(heap));
    }

    @Test
    void proximo(){
        Integer[] datos_test_1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Heap<Integer> heap = new Heap<>(datos_test_1, datos_test_1.length);
        assertEquals(9, heap.proximo());
    }

    @Test
    void pertenece(){
        Integer[] datos_test_1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Heap<Integer> heap = new Heap<>(datos_test_1, datos_test_1.length);
        assertEquals(false, heap.pertenece(10));
        assertEquals(true, heap.pertenece(1));
    }



    @Test
    void quitar_primer_elemento(){
        Integer[] datos_test_1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Heap<Integer> heap = new Heap<>(datos_test_1, datos_test_1.length);
        
        assertEquals(10, heap.cardinal());        
        assertEquals(9, heap.eliminar(0));
        assertEquals(9, heap.cardinal());
        assertEquals(true, es_max_heap(heap));
    }

    @Test
    void quitar_elemento_central(){
        Integer[] datos_test_1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Heap<Integer> heap = new Heap<>(datos_test_1, datos_test_1.length);

        assertEquals(10, heap.cardinal());        
        assertEquals(true, heap.pertenece(4));
        assertEquals(4, heap.eliminar(4));
        assertEquals(false, heap.pertenece(4));
        
        assertEquals(9, heap.cardinal());
        assertEquals(true, es_max_heap(heap));
        assertEquals("{9, 8, 6, 7, 1, 5, 2, 0, 3}", heap.toString());
    }


    @Test
    void quitar_elemento_hoja(){
        Integer[] datos_test_1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        Heap<Integer> heap = new Heap<>(datos_test_1, datos_test_1.length);

        assertEquals(10, heap.cardinal());        
        assertEquals(true, heap.pertenece(1));
        assertEquals(1, heap.eliminar(9));
        assertEquals(false, heap.pertenece(1));
        
        assertEquals(9, heap.cardinal());
        assertEquals(true, es_max_heap(heap));
    }

    @Test
    void desencolar(){
        Integer[] datos_test_1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 99 };
        Heap<Integer> heap = new Heap<>(datos_test_1, datos_test_1.length);

        assertEquals(11, heap.cardinal());        
        assertEquals(true, heap.pertenece(99));
        assertEquals(99, heap.desencolar());
        assertEquals(false, heap.pertenece(99));
        
        assertEquals(10, heap.cardinal());
        assertEquals(true, es_max_heap(heap));
    }
}

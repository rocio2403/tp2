package aed;

import java.util.Arrays;
import java.util.Comparator;

public class TestMinHeap {

    public static void main(String[] args) {
        Integer[] initialArray = {15, 10, 20, 17, 25, 8};
        int cardinal = 6;

        // Crear el MinHeap con el arreglo inicial y un comparador natural para Integer
        MinHeap<Integer> minHeap = new MinHeap<>(initialArray, cardinal, Comparator.naturalOrder());

        // Test 1: Verificar el heap inicial
        System.out.println("Estado inicial del MinHeap: " + minHeap);
        assert minHeap.proximo() == 8 : "El menor elemento debe ser 8";

        // Test 2: Encolar un elemento nuevo y verificar su posición
        minHeap.encolar(5);
        System.out.println("Estado del MinHeap tras encolar 5: " + minHeap);
        assert minHeap.proximo() == 5 : "El menor elemento debe ser 5 después de encolar";

        // Test 3: Desencolar elementos y verificar el orden
        System.out.println("Desencolando todos los elementos:");
        Integer[] sortedArray = new Integer[minHeap.cardinal()];
        int index = 0;
        
        while (minHeap.cardinal() > 0) {
            sortedArray[index++] = minHeap.desencolar();
        }

        System.out.println("Array ordenado al desencolar: " + Arrays.toString(sortedArray));
        
        // Comprobar que el array está ordenado de menor a mayor
        for (int i = 0; i < sortedArray.length - 1; i++) {
            assert sortedArray[i] <= sortedArray[i + 1] : "El MinHeap no está ordenado correctamente";
        }

        System.out.println("Todos los tests pasaron correctamente.");
    }
}

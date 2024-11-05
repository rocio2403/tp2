package aed;

import java.util.Arrays;

public class TestMaxHeap {

    public static void main(String[] args) {
        Integer[] initialArray = {10, 20, 15, 30, 40};
        int cardinal = 5;

        // Crear el MaxHeap con el arreglo inicial
        MaxHeap<Integer> maxHeap = new MaxHeap<>(initialArray, cardinal);

        // Test 1: Verificar el heap inicial
        System.out.println("Estado inicial del MaxHeap: " + maxHeap);
        assert maxHeap.proximo() == 40 : "El mayor elemento debe ser 40";

        // Test 2: Encolar un elemento nuevo y verificar su posición
        maxHeap.encolar(50);
        System.out.println("Estado del MaxHeap tras encolar 50: " + maxHeap);
        assert maxHeap.proximo() == 50 : "El mayor elemento debe ser 50 después de encolar";

        // Test 3: Desencolar elementos y verificar el orden
        System.out.println("Desencolando todos los elementos:");
        Integer[] sortedArray = new Integer[maxHeap.cardinal()];
        int index = 0;
        
        while (maxHeap.cardinal() > 0) {
            sortedArray[index++] = maxHeap.desencolar();
        }

        System.out.println("Array ordenado al desencolar (de mayor a menor): " + Arrays.toString(sortedArray));
        
        // Comprobar que el array está ordenado de mayor a menor
        for (int i = 0; i < sortedArray.length - 1; i++) {
            assert sortedArray[i] >= sortedArray[i + 1] : "El MaxHeap no está ordenado correctamente";
        }

        System.out.println("Todos los tests pasaron correctamente.");
    }
}

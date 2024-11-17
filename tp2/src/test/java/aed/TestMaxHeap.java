package aed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.Test;

public class TestMaxHeap{

    @Test
    public void sequentialHeapTest() {

        Integer[] heapValues = {20,40,30,10,90,70};

        MaxHeap<Integer> sequenHeap = new MaxHeap<>();
        sequenHeap.createHeap_Sequential(heapValues);

        Integer[] result = new Integer[6];

        for (int i = 1; i < sequenHeap.getSize() + 1; i++) {
            result[i-1] = sequenHeap.getHeapValue_atIndex(i);
        }

        Integer[] expected = {90,40,70,10,20,30};

        assertArrayEquals(expected, result  );

        
    }

    @Test
    public void smartHeapTest() {

        Integer[] heapValues = {20,40,30,10,90,70};

        MaxHeap<Integer> smartHeap = new MaxHeap<>();
        smartHeap.createHeap_SmartWay(heapValues);

        Integer[] result = new Integer[6];

        for(int i = 1; i < smartHeap.getSize() + 1; i++) {
            result[i-1] = smartHeap.getHeapValue_atIndex(i);
        }

        Integer[] expected = {90,40,70,10,20,30};

        assertArrayEquals(expected, result);

    }

    @Test
    public void getMethodTest() {
        
        Integer[] heapValues = {20,40,30,10,90,70};

        // heap created using smart method
        MaxHeap<Integer> smartHeap = new MaxHeap<>();
        smartHeap.createHeap_SmartWay(heapValues);

        // heap created using sequential method
        MaxHeap<Integer> sequenHeap = new MaxHeap<>();
        sequenHeap.createHeap_Sequential(heapValues);

        assertTrue(smartHeap.getMethod());
        assertFalse(sequenHeap.getMethod());

    }

    @Test
    public void getSwapsTest() {

        Integer[] heapValues = {20,40,30,10,90,70};

        // heap created using smart method
        MaxHeap<Integer> smartHeap = new MaxHeap<>();
        smartHeap.createHeap_SmartWay(heapValues);

        // heap created using sequential method
        MaxHeap<Integer> sequenHeap = new MaxHeap<>();
        sequenHeap.createHeap_Sequential(heapValues);

        int expectedSmartSwaps = 4;
        int expectedSequentialSwaps = 4;

        assertEquals(expectedSmartSwaps, smartHeap.getSwaps());
        assertEquals(expectedSequentialSwaps, sequenHeap.getSwaps());

    }

    @Test
    public void readInputFileTest() throws FileNotFoundException {

        String fileName = "C:/Users/galax/git/CS 2400 Assignments/Project_4-MaxHeap/data_sorted.txt";
        Integer[] result = DriverMH.read100Integers(fileName);

        Integer[] expected = new Integer[100];
        for (int i = 0; i < 100;i++) {
            int tempInt = i + 1;
            expected[i] = Integer.valueOf(tempInt);
        }

        assertEquals(expected[0], result[0]);
        

    }

    @Test
    public void printAfterCreationTest() throws IOException {
        String fileName = "testUnitTest.txt";
        Integer[] heapValues = {20,40,30,10,90,70};

        DriverMH.MakeAFile(fileName);

        MaxHeap<Integer> smartHeap = new MaxHeap<>();
        smartHeap.createHeap_SmartWay(heapValues);

        MaxHeap<Integer> sequenHeap = new MaxHeap<>();
        sequenHeap.createHeap_Sequential(heapValues);

        DriverMH.print100Num_Creation(smartHeap, fileName);
        DriverMH.print100Num_Creation(sequenHeap, fileName);

        File myFile = new File(fileName);
        Scanner outputFile = new Scanner(myFile);

        
        String readResult1 = outputFile.nextLine();
        String readResult2 = outputFile.nextLine();

        String expected1 = "Heap built using optimal method: 90,40,70,10,20,30,";
        String expected2 = "Heap built using sequential insertions: 90,40,70,10,20,30,";
        outputFile.close();
        myFile.delete();

        assertTrue(readResult1.equals(expected1));
        assertTrue(readResult2.equals(expected2));


    }

    @Test
    public void printSwapsTest() throws IOException {
        String fileName = "testUnitTest.txt";
        Integer[] heapValues = {20,40,30,10,90,70};

        DriverMH.MakeAFile(fileName);

        MaxHeap<Integer> smartHeap = new MaxHeap<>();
        smartHeap.createHeap_SmartWay(heapValues);

        DriverMH.printSwaps(smartHeap, fileName);

        File myFile = new File(fileName);
        Scanner outputFile = new Scanner(myFile);

        String readResult = outputFile.nextLine();

        String expected = "Number of swaps in the heap creation: 4";
        outputFile.close();
        myFile.delete();
    
        assertTrue(readResult.equals(expected));


    }

    @Test
    public void perform10RemovalsTest() throws FileNotFoundException {

        Integer[] heapValues = {20,40,30,1,10,90,70,50,60,70,10};

        MaxHeap<Integer> heap = new MaxHeap<>();
        heap.createHeap_SmartWay(heapValues);

        DriverMH.perform10Removals(heap);

        Integer[] result = new Integer[heap.getSize()];

        for(int i = 1; i < heap.getSize() + 1; i++) {
            result[i-1] = heap.getHeapValue_atIndex(i);
        }

        Integer[] expected = {1};

        assertArrayEquals(expected, result);


    }

    @Test
    public void printAfterRemovalTest() throws IOException {
        
        String fileName = "testUnitTest.txt";
        Integer[] heapValues = {20,40,30,1,10,90,70,50,60,70,10};

        DriverMH.MakeAFile(fileName);

        MaxHeap<Integer> smartHeap = new MaxHeap<>();
        smartHeap.createHeap_SmartWay(heapValues);

        DriverMH.perform10Removals(smartHeap);
        DriverMH.print100Num_Removal(smartHeap, fileName);

        File myFile = new File(fileName);
        Scanner outputFile = new Scanner(myFile);

        
        String readResult1 = outputFile.nextLine();

        String expected1 = "Heap after 10 removals: 1,";

        outputFile.close();
        myFile.delete();

        assertTrue(readResult1.equals(expected1));

    }

    @Test
    public void printOutputBorderTest() throws IOException {

        String fileName = "testUnitTest.txt";
        Integer[] heapValues = {20,40,30,1,10,90,70,50,60,70,10};

        DriverMH.MakeAFile(fileName);

        DriverMH.printOutputBorder(fileName);

        File myFile = new File(fileName);
        Scanner outputFile = new Scanner(myFile);
        
        String readResult1 = outputFile.nextLine();

        String expected1 = "=====================================================================";

        outputFile.close();
        myFile.delete();

        assertTrue(readResult1.equals(expected1));

    }

    @Test
    public void makeFileTest() throws FileNotFoundException {
        String fileName = "testUnitTest.txt";
        DriverMH.MakeAFile(fileName);

        File myFile = new File(fileName);

        boolean fileExists = myFile.exists();
        myFile.delete();

        assertTrue(fileExists);
        
    }


}
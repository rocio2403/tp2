package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CiudadTest {
    private Ciudad ciudad;

    @BeforeEach
    public void setUp() {
        ciudad = new Ciudad(1, 100, 200, 100); // id=1, pérdidas=100.0, ganancias=200.0, superávit=100.0
    } //arreglar por el compareTo

    @Test
    public void testGetters() {
        assertEquals(1, ciudad.getId());
        assertEquals(100.0, ciudad.getPerdidas());
        assertEquals(200.0, ciudad.getGanancias());
        assertEquals(100.0, ciudad.getSuperavit());
    }

    @Test
    public void testAgregarGanancias() {
        ciudad.agregarGanancias(50);
        assertEquals(250.0, ciudad.getGanancias()); // Las ganancias deberían aumentar a 250.0
        assertEquals(150.0, ciudad.getSuperavit());  // El superávit debería actualizarse a 150.0
    }

    @Test
    public void testAgregarPerdidas() {
        ciudad.agregarPerdidas(50);
        assertEquals(150.0, ciudad.getPerdidas());  // Las pérdidas deberían aumentar a 150.0
        assertEquals(50.0, ciudad.getSuperavit());   // El superávit debería actualizarse a 50.0
    }
    @Test
    public void testMultipleActualizaciones() {
        // Inicialmente, las ganancias son 200.0 y las pérdidas son 100.0
        assertEquals(200.0, ciudad.getGanancias());
        assertEquals(100.0, ciudad.getPerdidas());
        assertEquals(100.0, ciudad.getSuperavit());
    
        // Agregar ganancias
        ciudad.agregarGanancias(50);  // 200.0 + 50.0 = 250.0
        ciudad.agregarGanancias(100); // 250.0 + 100.0 = 350.0
    
        // Agregar pérdidas
        ciudad.agregarPerdidas(30);   // 100.0 + 30.0 = 130.0
        ciudad.agregarPerdidas(70);   // 130.0 + 70.0 = 200.0
    
        // Comprobar valores finales
        assertEquals(350.0, ciudad.getGanancias());  // Las ganancias deberían ser 350.0
        assertEquals(200.0, ciudad.getPerdidas());    // Las pérdidas deberían ser 200.0
        assertEquals(150.0, ciudad.getSuperavit());   // El superávit debería ser 150.0 (350.0 - 200.0)
    }
    
    @Test
    public void testToString() {
        String expected = "Ciudad{id=1, perdidas=100, ganancias=200, superavit=100}";
        assertEquals(expected, ciudad.toString());
    }
}
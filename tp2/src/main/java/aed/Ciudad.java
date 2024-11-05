package aed;

public class Ciudad {
    private  int id;           
    private double perdidas;  
    private double ganancias; 
    private double superavit;   


    public Ciudad(int id, double perdidas, double ganancias, double superavit){
        this.id = id;
        this.perdidas = perdidas;
        this.ganancias = ganancias;
        this.superavit = superavit;
    } //listo tengo el constructor, voy por als operaciones de la clase
    // de una ciudad yo quiero :
    //tener los getters, para acceder a los atributos
    //quiero poder actualizar esas variables
    //reescribo el toString

    //preguntar si las variabels deberian ser privadaas o publicas
    //deberia hacer test para testear la clase
    public int getId() {
        return id;
    }

    public double getPerdidas() {
        return perdidas;
    }

    public double getGanancias() {
        return ganancias;
    }

    public double getSuperavit() {
        return superavit;
    }

    public void agregarGanancias(double monto) {
        this.ganancias += monto;
        actualizarSuperavit();
    }

    public void agregarPerdidas(double monto) {
        this.perdidas += monto;
        actualizarSuperavit();
    }


    private void actualizarSuperavit() {
        this.superavit = this.ganancias - this.perdidas;
    }


    @Override
    public String toString() {
            return "Ciudad{id=" + id + 
            ", perdidas=" + (int) perdidas + 
            ", ganancias=" + (int) ganancias + 
            ", superavit=" + (int) superavit + "}";
    }

}

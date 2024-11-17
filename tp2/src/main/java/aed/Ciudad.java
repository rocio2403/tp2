package aed;

public class Ciudad implements Comparable<Ciudad> {
    private  int id;           
    private int perdidas;  
    private int ganancias; 
    private int superavit;   


    public Ciudad(int id, int perdidas, int ganancias, int superavit){
        this.id = id;
        this.perdidas = perdidas;
        this.ganancias = ganancias;
        this.superavit = superavit;
    } 
    public int getId() {
        return id;
    }

    public int getPerdidas() {
        return perdidas;
    }

    public int getGanancias() {
        return ganancias;
    }

    public int getSuperavit() {
        return superavit;
    }

    public void agregarGanancias(int monto) {
        ganancias += monto;
        actualizarSuperavit();
    }

    public void agregarPerdidas(int monto) {
        perdidas += monto;
        actualizarSuperavit();
    }


    public void actualizarSuperavit() {
        this.superavit = this.ganancias - this.perdidas;
    }


    @Override
    public String toString() {
            return "Ciudad{id=" + id + 
            ", perdidas=" + (int) perdidas + 
            ", ganancias=" + (int) ganancias + 
            ", superavit=" + (int) superavit + "}";
    }


     @Override
    public int compareTo(Ciudad otra) {
        return Integer.compare(otra.superavit, this.superavit);
    }

}

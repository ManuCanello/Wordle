public class Pantalla{

    public static void borrarPantalla(){
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }
}
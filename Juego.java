import java.util.Scanner;

public class Juego {

    private final Palabra palabra_adivinar = new Palabra();
    private final String array_palabras[] = new String[5];
    private int intento = 0;

    public static void main(String[] args) {
        new Juego();
    }

    public Juego(){
        preparJuego();
        start();
    }

    private void start(){
        jugar();
    }

    private void jugar(){
        String palabra_ingresada;
        boolean ganador = false, continuar = true;
        mostrarPalabras();
        do{ 
            palabra_ingresada = pedirPalabra();
            reemplazarPalbraArray(palabra_ingresada);
            System.out.println(ganador(palabra_ingresada));

            if(this.intento == 4)
                continuar = false;

            if(ganador(palabra_ingresada)){
                ganador = true;
                continuar = false;
            }else
                sumarIntento();
            
            mostrarPalabras();
        } while(continuar);

        if(ganador){
            System.out.println("Haz ganado");
        }else{
            System.out.println(palabraString(palabra_adivinar.getPalabra()));
        }
    }

    private String cambiarColorLetra(String letra, int color, int pos ){
        String colores[] = {"\u001B[33m","\u001B[32m","\u001B[0m"};
        return colores[color] + letra.charAt(pos);
    }

    private void setArrayPalabras(){
        for(int i = 0; i < 5; i++){
            this.array_palabras[i] = "_ _ _ _ _";
        }
    }

    private void preparJuego(){
        setArrayPalabras();
    }

    private void mostrarPalabras(){
        Pantalla.borrarPantalla();
        for(int i = 0; i < 5; i++){
            System.out.println(this.array_palabras[i]);
        }
    }

    private void sumarIntento(){
        this.intento++;
    }

    private boolean ganador(String palabra){
        for(int i = 0; i < palabra_adivinar.getPalabra().length; i++){
            if(!palabra_adivinar.getPalabra()[i].equals(String.valueOf(palabra.charAt(i))))
                return false;
        }
        return true;
    }

    private void reemplazarPalbraArray(String palabra){
        String[] palabra_espacios = new String[5];
        String[] aux = new String[5];
        inicializarArray(palabra_espacios, palabra);
        copiarArray(aux, palabra_adivinar.getPalabra());

        for(int i = 0; i < palabra.length(); i++){
            if(String.valueOf(palabra.charAt(i)).equals(palabra_adivinar.getPalabra()[i])){
                palabra_espacios[i] = cambiarColorLetra(palabra, 1, i) + " " + "\u001B[0m";
                censurarLetra(i);
            }
        }

        for(int i = 0; i < palabra.length(); i++){
            if(existe(String.valueOf(palabra.charAt(i)), palabraString(palabra_adivinar.getPalabra())))
                palabra_espacios[i] = cambiarColorLetra(palabra, 0, i) + " " + "\u001B[0m";
            else if(String.valueOf(palabra.charAt(i)).equals("!")){
                palabra_espacios[i] = cambiarColorLetra(palabra, 2, i) + " " + "\u001B[0m";
            }
        }  
        
        this.array_palabras[intento] = palabraString(palabra_espacios);
        copiarArray(palabra_adivinar.getPalabra(), aux);
    }

    private String pedirPalabra(){
        Scanner sc = new Scanner(System.in);
        String palabra = sc.nextLine();
        
        if(palabra.trim().length() == 5 && comprobarLetras(palabra.trim()))
            return palabra.toLowerCase();
        else
            return pedirPalabra();
    }

    private boolean comprobarLetras(String palabra){
        for(int i = 0; i < palabra.length(); i++){
            if(!Character.isLetter(palabra.charAt(i)))
                return false;
        }
        return true;
    }

    private void censurarLetra(int i){
        palabra_adivinar.getPalabra()[i] = "!";
    }

    private String palabraString(String[] array){
        String palabra = "";
        for (String p : array) {
            palabra += p;
        }
        return palabra;
    }

    private void copiarArray(String a1[], String a2[]){
        System.arraycopy(a2, 0, a1, 0, a1.length);
    }

    private boolean existe(String letra, String palabra){
        letra = letra.trim();
        palabra = palabra.trim();
        for(int i = 0; i < palabra.length(); i++){
            if(letra.equals(String.valueOf(palabra.charAt(i)))){
                censurarLetra(i);
                return true;
            }
        }
        return false;
    }

    private void inicializarArray(String[] array, String palabra){
        for(int i = 0; i < array.length; i++){
            array[i] = String.valueOf(palabra.charAt(i)) + " ";
        }
    }
}


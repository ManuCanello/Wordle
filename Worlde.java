import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


public class Worlde{

    private String palabra_adivinar;
    private String array_palabras[] = new String[5];
    private int intento = 0;

    public static void main(String[] args) {
        new Worlde();
    }

    public Worlde(){
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
            System.out.println(palabra_adivinar);
        }
        
    }


    //cambia color de las letras para ver si estan bien o no(verde correcto, amarillo letra correcta pero en mal lugar)
    private String cambiarColorLetra(String letra, int color, int pos ){
        //0 amarillo, 1 verde, 2 blanco
        String colores[] = {"\u001B[33m","\u001B[32m","\u001B[0m"};
        return colores[color] + letra.charAt(pos);
    }

    private void setPalabraAdivinar(){
        Random r = new Random();
        int stop = r.nextInt(cantidadPalabras()-1);
        int nl=0;//nl(numero de line) stop(en que linea frena)
        String p;
        try(BufferedReader br = new BufferedReader(new FileReader("palabras.txt"))){
            while(br.readLine() != null){
                if(nl==stop){
                    p = br.readLine();
                    p = p.toLowerCase();
                    p = p.trim();
                    palabra_adivinar = p;
                    break;
                }

                nl++;
            }
        }catch(IOException e){
            System.out.println(e);
        }

    }
    
    //Cantidad de palabras que posee el archivo
    private int cantidadPalabras(){
        int cantPalabras=0;
        
        try(BufferedReader br = new BufferedReader(new FileReader("palabras.txt"))){
            while(br.readLine() != null){
                cantPalabras++;
            }
        }catch(IOException e){
            System.out.println(e);
        }

        return cantPalabras;
        
    }


    private void setArrayPalabras(){
        for(int i=0;i<5;i++){
            this.array_palabras[i]="_ _ _ _ _";
        }
    }

    private void preparJuego(){
        setPalabraAdivinar();
        setArrayPalabras();
    }

    private void mostrarPalabras(){
        Pantalla.borrarPantalla();
        for(int i=0;i<5;i++){
            System.out.println(this.array_palabras[i]);
        }

        System.out.println(palabra_adivinar);
    }

    private void sumarIntento(){
        this.intento++;
    }

    private boolean ganador(String palabra){
        return palabra.equals(this.palabra_adivinar);
    }

    private void reemplazarPalbraArray(String palabra){
        String palabra_espacios = "",aux = palabra_adivinar;
        for(int i=0;i<palabra.length();i++){
            if(palabra.charAt(i) == palabra_adivinar.charAt(i)){
                palabra_espacios += cambiarColorLetra(palabra,1,i)+ " " + "\u001B[0m";
            }else{
                if(existe(palabra.charAt(i),palabra_adivinar)){
                    palabra_espacios += cambiarColorLetra(palabra,0,i)+ " "+"\u001B[0m";
                    
                }else
                    palabra_espacios += cambiarColorLetra(palabra,2,i)+ " "+"\u001B[0m";
            }
        }

        this.array_palabras[intento] = palabra_espacios;
        palabra_adivinar = aux;
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
        for(int i=0;i<palabra.length();i++){
            if(!Character.isLetter(palabra.charAt(i)))
                return false;
        }

        return true;
    }

    private boolean existe(char letra, String palabra){
        for(int i=0;i<palabra.length();i++){
            if(letra == palabra.charAt(i)){
                return true;
            }
        }

        return false;
    }

  

}
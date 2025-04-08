import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Palabra {

    private final String[] palabra_adivinar = new String[5];

    public Palabra() {
        setPalabraAdivinar();
    }

    public String[] getPalabra() {
        return palabra_adivinar;
    }

    private void setPalabraAdivinar(){
        Random r = new Random();
        int stop = r.nextInt(cantidadPalabras() - 1);
        int nl = 0;
        String p;
        try(BufferedReader br = new BufferedReader(new FileReader("palabras.txt"))){
            while(br.readLine() != null){
                if(nl == stop){
                    p = br.readLine();
                    p = p.toLowerCase().trim();
                    for(int i = 0; i < palabra_adivinar.length; i++){
                        palabra_adivinar[i] = String.valueOf(p.charAt(i));
                    }
                    break;
                }
                nl++;
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }

    private int cantidadPalabras(){
        int cantPalabras = 0;
        try(BufferedReader br = new BufferedReader(new FileReader("palabras.txt"))){
            while(br.readLine() != null){
                cantPalabras++;
            }
        }catch(IOException e){
            System.out.println(e);
        }
        return cantPalabras;
    }
}


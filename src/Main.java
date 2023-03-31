// Tablica jednowymiarowa
//==============================================
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
class Wektor{
    public int[] vector; // referencja do wektora
    public int maxSize; // maksymalna długość wektora
    public int currentSize; // aktualna długość wektora
    //============================================= konstruktor
    public Wektor (int maxSize_) {
        maxSize = maxSize_;
        currentSize = 0;
        vector = new int[maxSize];
        System.out.println("Vector" + Arrays.toString(vector));
    }
    //==================================== ustawienie wektora
    public void setVector(int[] vector) {
        if(maxSize < vector.length){
            this.vector = vector;
            // Nadpisuje wcześniejsze ustawienie maxSize.
            maxSize = vector.length;
        }
        else {
            System.arraycopy(vector, 0, this.vector, 0, vector.length);
        }
        // Nadpisuje wcześniejsze ustawienie currentSize dla obu powyższych przypadków.
        currentSize = vector.length;
    }
    //==================================== wczytywanie wektora
    public void readVector(int nrOfIntegers, Scanner sc) {
        for (int i = 0; i < nrOfIntegers; ) {
            if (sc.hasNextInt()) {
                vector[i] = sc.nextInt();
                ++i;
            } else {
                // Zbiera to, co zostało wpisane i nie było typu int.
                sc.nextLine();
            }
        }
    }
    //===================================== losowanie wektora
    /** Losowanie nrOfIntegers liczb z przedziału [min,max] do wektora vector.*/
    public void randVector(int nrOfIntegers, int min, int max) {
        System.out.println ("Losowanie "+nrOfIntegers+" liczb z przedzialu [" +min+","+max+"]");
        for( int i=0; i<nrOfIntegers; i++)
            // max+1, żeby przedział był domknięty z góry.
            vector[i] = new Random().nextInt(max+1-min)+min;
    }
    //===================================== wyświetlanie wektora
    public void display(){
        System.out.println("Liczba elementow = " + currentSize);
        System.out.println("Zawartosc wektora ");
        for (int i = 0; i<currentSize; i++) {
            System.out.print(vector[i]+", ");
            if((i+1)%10==0) System.out.println();
        }
        System.out.println();
    }
    public void display(int begin, int end){
        int size = end - begin + 1;
        if(size>currentSize) {
            System.out.println("Liczba elementów nie może być większa niż " + currentSize);
            return;
        }
        System.out.println("Liczba elementow = " + size);
        System.out.print("Indeks początku=" + begin + " Indeks końca=" + end +". ");
        System.out.println("Zawartość podwektora ");
        // i<=end mniejsze lub równe, ponieważ end jest indeksem.
        for (int i = begin; i<=end; i++) {
            System.out.print(vector[i]+", ");
            if((i-begin+1)%10==0) System.out.println();
        }
        System.out.println();
    }
    public void insertLast(int insertedElement){
        if(currentSize < maxSize) {
            vector[currentSize++] = insertedElement;
        }
        else {
            int[] newVector = new int[2*maxSize];
            for(int i=0; i<currentSize; i++)
                newVector[i] = vector[i];
            vector = newVector;
            maxSize*=2;
            vector[currentSize++] = insertedElement;
        }
    }
    public void deleteGiven(int deletedElement){
        int repetitions = 0;
        for(int i=0; i<currentSize; i++){
            if(vector[i] == deletedElement)
                    repetitions++;
        }
        int[] newVector = new int[currentSize-repetitions];
        int j=0;
        for(int i=0; i<currentSize; i++){
            if(vector[i] != deletedElement){
                newVector[j] = vector[i];
                j++;
            }
        }
        vector = newVector;
        currentSize-=repetitions;
    }
    public void checkOrder(){
        boolean notLowering = false, notRising = false, constant = false;

        for(int i=0; i<currentSize-1; i++) {
            if (vector[i] > vector[i + 1]){
                constant = false;
                notRising = true;
            }
            if (vector[i] <= vector[i + 1]){
                constant = false;
                notLowering = true;
            }
            if (vector[i] == vector[i + 1])
                constant = true;
            else
                constant = false;
        }
        if(notLowering == true)
            System.out.println("nie malejacy");
        else if(notRising == true)
            System.out.println("nie rosnacy");
        else if(constant == true)
            System.out.println("staly");

    }
    public void getMinMax(){
        Integer max = vector[0];
        Integer min = vector[0];
        for(int i=0; i < currentSize; i++){
            if(vector[i]>max)
                max = vector[i];
            if(vector[i]<min)
                min = vector[i];
        }
        System.out.println("Max to:" + max.toString());
        System.out.println("Min to:" + min.toString());
    }
    public int hornersMethod(int givenNumber){
        int value = vector[0];
        for(int i=1; i < currentSize; i++){
            value = value*givenNumber + vector[i];
        }
        return value;
    }
}//End of class wektor
////////////////////////////////////////////////////////////
/**
 *     Klasa wektorApp, zawierająca metodę main(), pozwalającą na wybór i ilustrację
 *     działania podanych operacji na tablicy.
 */
class WektorApp {
    /** Zapobiega błędom gdy nie int.*/
    public static int getInt(Scanner sc) {
        if (sc.hasNextInt()) {
            return sc.nextInt();
        }
        // Zbiera to, co wpisane i nie typu int.
        sc.nextLine();
        return -1;
    }
    /** Ustawia i wyświetla elementy wektora (ręcznie, losowo, predefiniowany) */
    public static void setVectorElements(Scanner sc, Wektor wektor, int[] vec) {
        System.out.print("Podaj aktualną dlugosc wektora, "+" <= "+wektor.maxSize +": ");
        int currentSizeTmp = getInt(sc);
        while(currentSizeTmp<=0 || currentSizeTmp>wektor.maxSize) {
            System.out.println("Niepoprawna długość wektora");
            System.out.print("Podaj nowa dlugosc wektora: ");
            currentSizeTmp = getInt(sc);
        }
        wektor.currentSize = currentSizeTmp;
        System.out.println("Wybierz: 1-czytanie, 2-losowanie, 3-predefiniowany, inne - koniec");
        int choice = getInt(sc);
        switch(choice){
            case 1:
                System.out.println("Czytanie "+currentSizeTmp+" liczb integer");
                wektor.readVector(currentSizeTmp, sc );
                break;
            case 2:
                System.out.println("Losowanie "+currentSizeTmp+" liczb integer");
                System.out.print("Podaj minimum: ");
                int min = sc.nextInt();
                System.out.print("Podaj maksimum: ");
                int max = sc.nextInt();
                if(max<min) max=min;
                wektor.randVector(currentSizeTmp,min,max);
                break;
            case 3:
                wektor.setVector(vec);
                break;
            default:
                return;
        }
        wektor.display();
    }
    /** setVectorElements(Scanner sc, Wektor wektor, int[] vec = new int[]{2,-6,2,-1}) */
    public static void setVectorElements(Scanner sc, Wektor wektor) {
        setVectorElements(sc, wektor, new int[]{2,-6,2,-1});
    }
    public static void main(String[] args) {
        Wektor testVector = new Wektor(5);
        //Tworzy jeden obiekt sc.
        Scanner sc = new Scanner(System.in);
        int[] predefinedArray = {1,-2,1};
        WektorApp.setVectorElements(sc, testVector, predefinedArray);
        //testVector.insertLast(100);
        //testVector.display();
        //testVector.deleteGiven(2);
        //testVector.display();
        //testVector.checkOrder();
        //testVector.getMinMax();
        System.out.println(testVector.hornersMethod(1));

    }
}//End of class wektorApp
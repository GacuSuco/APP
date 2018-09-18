package Recursie;

public class FaculteitsFunctie {
    public FaculteitsFunctie(int val) {
        System.out.println("Faculteitsfunctie");
        System.out.println("recursief");
        System.out.println(val + "! = " + calcRecursief(val));
        System.out.println("Niet-recursief");
        System.out.println(val + "! = " + calcNonRecursief(val));
    }

    public static void main(String[] args) {
        new FaculteitsFunctie(5);
    }

    public int calcRecursief(int n) {
        return n <= 1 ? n : (n * this.calcRecursief(n - 1));
    }

    public int calcNonRecursief(int n) {
        int sum = 1;

        while (n > 1) {
            sum *= n;
            n--;
        }

        return sum;
    }
}


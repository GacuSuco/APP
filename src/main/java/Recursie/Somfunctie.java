package Recursie;

public class Somfunctie {
    public Somfunctie(int val) {
        System.out.println("Somfunctie");
        System.out.println("recursief");
        System.out.println("som(" + val + ") = " + calcRecursief(val));
        System.out.println("Niet-recursief");
        System.out.println("som(" + val + ") = " + calcNonRecursief(val));
    }

    public static void main(String[] args) {
        new Somfunctie(1);
    }

    public int calcRecursief(int n) {
        return n <= 1 ? n : (n + this.calcRecursief(n - 1));
    }

    public int calcNonRecursief(int n) {
        int sum = 1;

        while (n > 1) {
            sum += n;
            n--;
        }

        return sum;
    }
}

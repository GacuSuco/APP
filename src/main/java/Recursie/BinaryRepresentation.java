package Recursie;

public class BinaryRepresentation {
    public BinaryRepresentation(int v) {
        System.out.println(this.CalcBinsOne(v));
    }

    public static void main(String[] args) {
        new BinaryRepresentation(5);
    }

    private int CalcBinsOne(int n) {
        if (n <= 1)
            return 1;
        if (n % 2 == 0) {
            return this.CalcBinsOne(n / 2);
        } else {
            return 1 + this.CalcBinsOne((n / 2));
        }
    }
}

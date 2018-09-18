package Recursie;

public class ReverseString {
    public ReverseString(String v) {
        System.out.println(Reverse(v));
    }

    public static void main(String[] args) {
        new ReverseString("Hallo");
    }

    private String Reverse(String s) {
        int length = s.length();
        if (length <= 1) {
            return s;
        } else {
            return s.charAt(length - 1) + this.Reverse(s.substring(0, length - 1));
        }
    }
}
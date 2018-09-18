package ListsStacksQueues;

public class main {
    public static void main(String[] args) {
        SingleLinkedList<String> List = new SingleLinkedList<String>();
        List.addFirst("p");
        List.addFirst("a");
        List.addFirst("e");
        List.addFirst("h");

        System.out.println(List.toString());
        List.insert(0,"#");
        System.out.println(List.toString());

        List.delete(5);
        System.out.println(List.toString());
    }
}

// Q19 - Interface Implementation
interface Playable {
    void play();
}

class Guitar implements Playable {
    public void play() {
        System.out.println("Playing Guitar...");
    }
}

class Piano implements Playable {
    public void play() {
        System.out.println("Playing Piano...");
    }
}

public class Q19_InterfaceExample {
    public static void main(String[] args) {
        Playable g = new Guitar();
        Playable p = new Piano();

        g.play();
        p.play();
    }
}

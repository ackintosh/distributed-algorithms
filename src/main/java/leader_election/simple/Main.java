package leader_election.simple;


public class Main {
    public static void main(String[] args) {
        Queue queue1to2 = new Queue();
        Queue queue2to3 = new Queue();
        Queue queue3to1 = new Queue();
        new Node("node1", queue1to2, queue3to1).start();
        new Node("node2", queue2to3, queue1to2).start();
        new Node("node3", queue3to1, queue2to3).start();

        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {

        }

        echo("send START message to node2.");
        queue1to2.putMessage(new StartMessage());
    }

    public static void echo(String message) {
        System.out.println("[Main] " + message);
    }
}

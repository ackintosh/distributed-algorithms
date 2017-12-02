package leader_election.simple;

class Node extends Thread {
    private final Queue sendQueue;
    private final Queue receiveQueue;
    private Status status = Status.WAITING;

    Node(String name, Queue sendQueue, Queue receiveQueue) {
        super(name);
        this.sendQueue = sendQueue;
        this.receiveQueue = receiveQueue;
    }

    public void run() {
        echo("waiting for message.");
        while (true) {
            Message message = receiveQueue.getMessage();
            System.out.println(message);
            if (message instanceof StartMessage) {
                handleStartMessage();
            } else if (message instanceof Token) {
                handleToken((Token)message);
            }
        }
    }

    private void echo(String message) {
        System.out.println("[" + Thread.currentThread().getName()
                + "](" + Thread.currentThread().getId() + ") " + message);
    }

    private void handleStartMessage() {
        echo("waiting -> candidate");
        status = Status.CANDIDATE;
        echo("send token");
        sendQueue.putMessage(new Token(Thread.currentThread().getId()));
    }

    private void handleToken(Token token) {
        echo("received token. (id: " + token.getId() + ")");
        if (token.getId() == Thread.currentThread().getId() && status.equals(Status.CANDIDATE)) {
            echo("candidate -> leader");
            status = Status.LEADER;
        } else {
            if (status.equals(Status.WAITING) || token.getId() > Thread.currentThread().getId()) {
                echo("waiting -> not leader");
                status = Status.NOT_LEADER;
            }
            if (token.getId() != Thread.currentThread().getId()) {
                sendQueue.putMessage(token);
            }
        }
    }
}

enum Status {
    WAITING,
    CANDIDATE,
    NOT_LEADER,
    LEADER,
}

package leader_election.simple;

import java.util.LinkedList;

class Queue {
    private final LinkedList<Message> linkedList = new LinkedList<Message>();

    public synchronized Message getMessage() {
        while (linkedList.peek() == null) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        return linkedList.remove();
    }

    public synchronized void putMessage(Message message) {
        linkedList.offer(message);
        notifyAll();
    }
}

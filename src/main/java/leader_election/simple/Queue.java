package leader_election.simple;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Queue {
    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();

    public Message getMessage() {
        Message m = null;
        try {
            m = queue.take();
        } catch (InterruptedException e) {

        }

        return m;
    }

    public void putMessage(Message message) {
        try {
            queue.put(message);
        } catch (InterruptedException e) {

        }
    }
}

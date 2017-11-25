package leader_election.simple;

class Token implements Message {
    private final long id;

    public Token(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}

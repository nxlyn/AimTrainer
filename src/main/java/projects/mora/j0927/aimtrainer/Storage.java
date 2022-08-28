package projects.mora.j0927.aimtrainer;

public class Storage {
    /**** INSTANCE VARIABLES ****/
    private String hits, misses, accuracy, score, combo;
    /**** CONSTRUCTOR ****/
    public Storage(String data) {
        String[] storage;
        storage = data.split(",");

            //Split based on String "Hits, Misses, Accuracy, Score, Combo"
            String hits = storage[0];
            String misses = storage[1];
            String accuracy = storage[2];
            String score = storage[3];
            String combo = storage[4];
            //ArrayList<Storage> storages = new ArrayList<Storage>();
            this.setHits(hits);
            this.setMisses(misses);
            this.setAccuracy(accuracy);
            this.setScore(score);
            this.setCombo(combo);

    }
    /**** MUTATORS ****/
    public void setHits(String hits) {
        this.hits = hits;
    }
    public void setMisses(String misses) {
        this.misses = misses;
    }
    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }
    public void setScore(String score) {
        this.score = score;
    }
    public void setCombo(String combo) {
        this.combo = combo;
    }
    public String getHits() {
        return this.hits;
    }
    public String getMisses() {
        return this.misses;
    }
    public String getAccuracy() {
        return this.accuracy;
    }
    public String getScore() {
        return this.score;
    }
    public String getCombo() {
        return this.combo;
    }
    /**** HELPER METHODS ****/
    public String toString() {
        return "[Hits: "+this.hits+", Misses: "+this.misses+", Accuracy: "+this.accuracy+", Score: "+this.score+", Combo: "+this.combo+"]";
    }
}

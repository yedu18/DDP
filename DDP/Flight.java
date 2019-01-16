package DDP;

public class Flight
{
    public int flight_no;
    public int start_node;
    public int end_node;
    public int start_time;
    public int speed;
    public int sep;
    public int run_len;
    public int priority;

    public Flight(int flight_no, int start_node, int end_node, int start_time, int speed, int sep, int run_len, int priority) {
        this.flight_no = flight_no;
        this.start_node = start_node;
        this.end_node = end_node;
        this.start_time = start_time;
        this.speed = speed;
        this.sep = sep;
        this.run_len = run_len;
        this.priority = priority;
    }

    public Flight() {
    }

    public int getFlight_no() {
        return flight_no;
    }

    public void setFlight_no(int flight_no) {
        this.flight_no = flight_no;
    }

    public int getStart_node() {
        return start_node;
    }

    public void setStart_node(int start_node) {
        this.start_node = start_node;
    }

    public int getEnd_node() {
        return end_node;
    }

    public void setEnd_node(int end_node) {
        this.end_node = end_node;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSep() {
        return sep;
    }

    public void setSep(int sep) {
        this.sep = sep;
    }

    public int getRun_len() {
        return run_len;
    }

    public void setRun_len(int run_len) {
        this.run_len = run_len;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}

package operator;

public class ActionSpec {   //实现对json的转化
    private String name;
    private String retrieve_counterName;
    private String save_counterName;

    public String getName() {
        return name;
    }
    public String getRetrieve_counterName() {
        return retrieve_counterName;
    }
    public String getSave_counterName() {
        return save_counterName;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setRetrieve_counterName(String retrieve_counterName) {
        this.retrieve_counterName = retrieve_counterName;
    }
    public void setSave_counterName(String save_counterName) {
        this.save_counterName = save_counterName;
    }
    public String toString() {
        return "ActionSpec{" +
                "name=" + name +
                ", retrieve_counterName=" + retrieve_counterName +
                ", save_counterName=" + save_counterName +
                '}';
    }
}

package operator;

public class CounterSpec{   //实现对json的转化
    private String counterName;
    private String counterIndex;
    private String type;
    private String keyFields;
    private String valueFields;
    private String maxSize;
    private String fields;

    public String getCounterName() {
        return counterName;
    }
    public String getCounterIndex() {
        return counterIndex;
    }
    public String getType() { return type; }
    public String getKeyFields() {
        return keyFields;
    }
    public String getValueFields() {
        return valueFields;
    }
    public String getMaxSize() { return maxSize; }
    public String getFields() { return fields; }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }
    public void setCounterIndex(String counterIndex) {
        this.counterIndex = counterIndex;
    }
    public void setType(String type) { this.type = type; }
    public void setKeyFields(String keyFields) {
        this.keyFields = keyFields;
    }
    public void setValueFields(String valueFields) {
        this.valueFields = valueFields;
    }
    public void setMaxSize(String maxSize) { this.maxSize = maxSize; }
    public void setFields(String fields) { this.fields = fields; }

    public String toString() {
        return "CounterSpec{" +
                "counterName='" + counterName + '\'' +
                ", counterIndex='" + counterIndex + '\'' +
                ", type='" + type + '\'' +
                ", keyFields='" + keyFields + '\'' +
                ", valueFields='" + valueFields + '\'' +
                ", maxSize='" + maxSize + '\'' +
                '}';
    }
}

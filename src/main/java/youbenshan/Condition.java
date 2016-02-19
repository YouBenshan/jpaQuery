package youbenshan;

 public class Condition{
	public String getNamePath() {
		return namePath;
	}
	public void setNamePath(String namePath) {
		this.namePath = namePath;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	private String namePath;
	private Operator operator;
	private String value;
}

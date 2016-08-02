package crowdcenter.aa.output;

public interface Output {

	Integer numberFields();
	void setField(Integer index, String value);
	void flush() throws Exception;
}

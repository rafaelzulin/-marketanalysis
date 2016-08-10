package crowdcenter.csv.output;

public interface Output {


	void setField(Integer index, String value);
	void flush() throws Exception;
}

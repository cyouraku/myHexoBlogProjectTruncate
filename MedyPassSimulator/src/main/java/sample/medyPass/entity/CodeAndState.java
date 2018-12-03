package sample.medyPass.entity;
import net.arnx.jsonic.JSONHint;

public class CodeAndState {

	@JSONHint(name="code")
	private String code;
	@JSONHint(name="state")
	private String state;

	public CodeAndState(){}

	public CodeAndState(String code,String state){
		this.code = code;
		this.state = state;
	}

	public String getCode(){
		return this.code;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getState(){
		return this.state;
	}

	public void setState(String state){
		this.state = state;
	}

}

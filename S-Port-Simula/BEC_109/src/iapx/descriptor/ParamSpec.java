package iapx.descriptor;

import java.io.IOException;

import iapx.AttributeInputStream;
import iapx.AttributeOutputStream;
import iapx.util.Option;
import iapx.util.Type;

//Record ParamSpec; info "TYPE";
//begin range(0:MaxType) type;
//      range(0:MaxByte) count;
//end;
public class ParamSpec {

	public Type type;
	public int count;

	public ParamSpec(Type type, int count) {
		this.type = type;
		this.count = count;
	}

	@Override
	public String toString() {
		return "ParamSpec: type=" + type + ", count=" +count;
	}

	public void write(AttributeOutputStream oupt) throws IOException {
		if(Option.ATTR_OUTPUT_TRACE) IO.println("ParamSpec.write: " + this);
		Type.write(type, oupt);
		oupt.writeShort(count);
	}

	public static ParamSpec read(AttributeInputStream inpt) throws IOException {
		Type type = Type.readType(inpt);
		if(Option.ATTR_INPUT_TRACE) IO.println("ParamSpec.read: type="+type);
		int count = inpt.readShort();
		if(Option.ATTR_INPUT_TRACE) IO.println("ParamSpec.read: count="+count);
		ParamSpec spec = new ParamSpec(type, count);
		if(Option.ATTR_INPUT_DUMP) IO.println("ParamSpec.read: "+spec);
//		Util.IERR("");
		return spec;
	}


}

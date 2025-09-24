package iAPX.enums;

import java.io.IOException;

import iAPX.minut.AttributeInputStream;
import iAPX.minut.AttributeOutputStream;

/*
 * Usage:
 * 
 * 	int i = NormalDefined.ordinal();
 * 
 * 	TestEnum normalDefined = TestEnum.of(3);

 */
public enum Test {
	Undefined, FixupDefined, NormalDefined, DefinedAndUsed;
	private static Test[] values = Test.values(); // Get all enum constants as an array
	public static Test of(int ordinale) {
		return values[ordinale];
	}
	
	public void write(Test testEnum, AttributeOutputStream oupt) throws IOException {
		oupt.writeShort(testEnum.ordinal());
	}
	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeShort(this.ordinal());
	}

	public static Test read(AttributeInputStream inpt) throws IOException {
		return Test.of(inpt.readShort());
	}

}

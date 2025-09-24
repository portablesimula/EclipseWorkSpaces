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
public enum Test2 {
	Undefined(1), FixupDefined(8), NormalDefined(5), DefinedAndUsed(2);
	
	public final int value;
	private Test2(int value) { this.value = value; }
	public int value() { return value; }
	
	private static Test2[] values = Test2.values(); // Get all enum constants as an array
	public static Test2 of(int ordinale) {
		return values[ordinale];
	}
	
	
	public void write(Test2 testEnum, AttributeOutputStream oupt) throws IOException {
		oupt.writeShort(testEnum.ordinal());
	}
	public void write(AttributeOutputStream oupt) throws IOException {
		oupt.writeShort(this.ordinal());
	}

	public static Test2 read(AttributeInputStream inpt) throws IOException {
		return Test2.of(inpt.readShort());
	}

}

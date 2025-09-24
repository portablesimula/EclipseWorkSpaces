package bec.compileTimeStack;

import bec.util.Global;
import bec.util.Type;
import bec.util.Util;
import bec.value.Value;
import bec.value.IntegerValue;
import bec.value.dataAddress.DataAddress;
import bec.value.dataAddress.GeneralAddress;
import bec.value.dataAddress.RemoteAddress;
import bec.value.dataAddress.SegmentAddress;
import bec.virtualMachine.SVM_ADDREG;
import bec.virtualMachine.SVM_LOADC;
import bec.virtualMachine.SVM_POP2REG;
import bec.virtualMachine.SVM_PUSHA;
import bec.virtualMachine.SVM_PUSHC;
import bec.virtualMachine.SVM_PUSHR;

public class AddressItem extends CTStackItem {
//	public Mode mode; // Inherited
//	public Type type; // Inherited
//	public int  size; // Inherited
	public DataAddress objadr;
	public int offset;

	public final static int NotStacked=0,FromConst=1,Calculated=2;
	public int objState;     // NotStacked ! FromConst ! Calculated
	public int atrState;     // NotStacked ! FromConst ! Calculated
	
//	private int xReg; // Index register. See INDEX
//	public int getIndexReg() {
//		return xReg;
//	}
//	public void useIndexReg(int xReg) {
//		this.xReg = xReg;
//	}
//	
//	private int adrReg; // Address register. See REFER
//	public int getAddressReg() {
//		return adrReg;
//	}
//	public void useAddressReg(int adrReg) {
//		this.adrReg = adrReg;
//	}
	
		
	private AddressItem() {}
	
	public static AddressItem ofREF(Type type, int offset, DataAddress objadr) {
		AddressItem itm = new AddressItem();
		itm.mode = Mode.REF;
		itm.type = type;
		itm.size = type.size();
		itm.objadr = objadr;
		if(objadr instanceof GeneralAddress) Util.IERR("");
		itm.offset = offset;
		return itm;
	}
	
	public static AddressItem ofVAL(Type type, int offset, DataAddress objadr) {
		AddressItem itm = new AddressItem();
		itm.mode = Mode.REF;
		itm.type = type;
		itm.size = type.size();
		itm.objadr = objadr;
		if(objadr instanceof GeneralAddress) Util.IERR("");
		itm.offset = offset;
		return itm;
	}

	@Override
	public CTStackItem copy() {
		AddressItem itm = new AddressItem();
		itm.mode = mode;
		itm.type = type;
		itm.size = size;
		itm.objadr = objadr;
		itm.offset = offset;
		itm.xReg = xReg;
		return itm;
	}

	public static void assertObjStacked() {
		AddressItem tos = (AddressItem) CTStack.TOS();
		if(tos.objState == AddressItem.NotStacked) {
			tos.objState = AddressItem.FromConst;
			DataAddress adr = tos.objadr;
//	           case 0:adrMax (adr.kind)
//	           when reladr,locadr: 
//	%+E             Qf3(qPUSHA,0,qEBX,cOBJ,adr);
//	           when segadr,fixadr,extadr:
//	%+E             Qf2b(qPUSHC,0,qEBX,cOBJ,0,adr);
//	%+C        otherwise IERR("CODER.AssertObjStacked-2")
//	           endcase;
			
//			if(adr instanceof SegmentAddress sadr) {
////				Qf2b(qPUSHC,0,qEBX,cOBJ,0,adr);
//				Global.PSEG.emit(new SVM_PUSHC(Type.T_OADDR, sadr), "assertObjStacked: ");
//			} else if(adr instanceof RemoteAddress radr) {
////				Qf3(qPUSHA,0,qEBX,cOBJ,adr);
//				Global.PSEG.emit(new SVM_PUSHA(radr), "assertObjStacked: ");
//				Util.IERR(""+radr);
//			} else Util.IERR(""+adr);
			
			Global.PSEG.emit(new SVM_PUSHA(adr), "assertObjStacked: ");

		}
	}

	public static void assertAtrStacked() {
		assertObjStacked();
		AddressItem tos = (AddressItem) CTStack.TOS();
		if(tos.atrState == AddressItem.NotStacked) {
			tos.atrState = AddressItem.FromConst;
//	        Qf2(qPUSHC,0,FreePartReg,cVAL,TOS qua Address.Offset);
			Global.PSEG.emit(new SVM_PUSHC(Type.T_INT,IntegerValue.of(Type.T_INT, tos.offset)), "assertAtrStacked: ");
		} else if(tos.atrState == AddressItem.Calculated) {
			if(tos.offset != 0) {
//	%+E             Qf2(qLOADC,0,qEAX,cVAL,TOS qua Address.Offset);
//	%+E             Qf1(qPOPR,qEBX,cVAL);
//	%+E             Qf2(qDYADR,qADD,qEAX,cVAL,qEBX); Qf1(qPUSHR,qEAX,cVAL);
				Value ofst = IntegerValue.of(Type.T_INT, tos.offset);
				Global.PSEG.emit(new SVM_LOADC(Reg.qEAX, ofst), "assertAtrStacked: ");
				Global.PSEG.emit(new SVM_POP2REG(Reg.qEBX), "assertAtrStacked: ");
				Global.PSEG.emit(new SVM_ADDREG(Reg.qEAX, Reg.qEBX), "assertAtrStacked: ");
				Global.PSEG.emit(new SVM_PUSHR(Reg.qEAX), "assertAtrStacked: ");
				tos.offset = 0;
			}
		}
	}
	
	public String edState(int state) {
		switch(state) {
			case NotStacked: return "NotStacked";
			case FromConst:  return "FromConst";
			case Calculated: return "Calculated";
			default: Util.IERR(""); return null;
		}
	}
	
	@Override
	public String toString() {
		String s = "ADDR: " + objadr + ", offset="+offset + ", objState="+edState(objState) + ", atrState="+edState(atrState);
		return edMode() + "ADDR: " + s;
	}

//	@Override
//	public String toString() {
//		String s = "" + type + " AT " + objadr + "[" + offset;
//		if(xReg > 0) s += "+" + RTRegister.edReg(xReg);
//		s =  s  + "]";
////		if(objadr.kind == DataAddress.REMOTE_ADDR) s = s + " withRemoteBase";
//		return edMode() + "ADDR: " + s;
//	}

}

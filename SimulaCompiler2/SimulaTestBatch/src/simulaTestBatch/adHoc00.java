// JavaLine 1 <== SourceLine 1
package simulaTestBatch;
// Simula-2.0 Compiled at Tue Jun 17 08:50:54 CEST 2025
import simula.runtime.*;
@SuppressWarnings("unchecked")
public final class adHoc00 extends RTS_BASICIO {
    // SubBlock: Kind=11, BlockLevel=0, firstLine=1, lastLine=16, hasLocalClasses=false, System=false
    // Declare locals as attributes
    // JavaLine 9 <== SourceLine 2
    public int i=0;
    // JavaLine 11 <== SourceLine 3
    public RTS_TXT t=null;
    public RTS_TXT u=null;
    // JavaLine 14 <== SourceLine 4
    public char c=0;
    // JavaLine 16 <== SourceLine 5
    public boolean b=false;
    // Normal Constructor
    public adHoc00(RTS_RTObject staticLink) {
        super(staticLink);
        BBLK();
        // Declaration Code
    }
    // 11 Statements
    @Override
    public RTS_RTObject _STM() {
        // JavaLine 27 <== SourceLine 7
        c=RTS_TXT.loadChar(t,4);
        ;
        // JavaLine 30 <== SourceLine 8
        RTS_TXT.storeChar(t,c,i);
        ;
        // JavaLine 33 <== SourceLine 9
        b=RTS_TXT.startsWith(t,new RTS_TXT("abc"));
        ;
        // JavaLine 36 <== SourceLine 10
        b=RTS_TXT.endsWith(t,new RTS_TXT("abc"));
        ;
        // JavaLine 39 <== SourceLine 11
        i=RTS_TXT.indexOf(t,'Z');
        ;
        // JavaLine 42 <== SourceLine 12
        RTS_TXT.replace(t,'\\','/');
        ;
        // JavaLine 45 <== SourceLine 13
        u=RTS_TXT.toLowerCase(t);
        ;
        // JavaLine 48 <== SourceLine 14
        u=RTS_TXT.toUpperCase(t);
        ;
        EBLK();
        return(this);
    } // End of 11 Statements
    
    public static void main(String[] args) {
        //System.setProperty("file.encoding","UTF-8");
        RTS_UTIL.BPRG("adHoc00", args);
        RTS_UTIL.RUN_STM(new adHoc00(_CTX));
    } // End of main
    public static RTS_PROGINFO _INFO=new RTS_PROGINFO("adHoc00.sim","11 adHoc00",1,1,9,2,11,3,14,4,16,5,27,7,30,8,33,9,36,10,39,11,42,12,45,13,48,14,59,16);
} // End of SubBlock

// JavaLine 1 <== SourceLine 2
package simulaTestBatch;
// Simula-2.0 Compiled at Wed Oct 15 10:29:52 CEST 2025
import simula.runtime.*;
@SuppressWarnings("unchecked")
public final class adHoc03 extends RTS_BASICIO {
    // SubBlock: Kind=11, BlockLevel=0, firstLine=2, lastLine=0, hasLocalClasses=false, System=false
    // Declare locals as attributes
    // JavaLine 9 <== SourceLine 8
    public RTS_Simset _inspect_8_1=null;
    // Normal Constructor
    public adHoc03(RTS_RTObject staticLink) {
        super(staticLink);
        BBLK();
        // Declaration Code
    }
    // 11 Statements
    @Override
    public RTS_RTObject _STM() {
        // JavaLine 20 <== SourceLine 2
        {
            // JavaLine 22 <== SourceLine 8
            {
                // BEGIN INSPECTION 
                _inspect_8_1=new RTS_Simset(_USR)._STM();
                if(_inspect_8_1!=null) { // INSPECT _inspect_8_1  type=ref(SIMSET)
                    {
                        {
                            RTS_BASICIO.sysout().outtext(new RTS_TXT("ABRA"));
                            ;
                        }
                    }
                }
            } // END INSPECTION
        }
        EBLK();
        return(this);
    } // End of 11 Statements
    
    public static void main(String[] args) {
        //System.setProperty("file.encoding","UTF-8");
        RTS_UTIL.BPRG("adHoc03", args);
        RTS_UTIL.RUN_STM(new adHoc03(_CTX));
    } // End of main
    public static RTS_PROGINFO _INFO=new RTS_PROGINFO("adHoc03.sim","11 adHoc03",1,2,9,8,20,2,22,8,44,0);
} // End of SubBlock

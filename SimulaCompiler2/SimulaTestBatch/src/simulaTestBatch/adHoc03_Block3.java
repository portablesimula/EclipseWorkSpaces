package simulaTestBatch;
// Simula-2.0 Compiled at Wed Oct 15 10:05:22 CEST 2025
import simula.runtime.*;
@SuppressWarnings("unchecked")
public final class adHoc03_Block3 extends RTS_BASICIO {
    // SubBlock: Kind=5, BlockLevel=1, firstLine=2, lastLine=13, hasLocalClasses=false, System=false
    // Declare locals as attributes
    // JavaLine 8 <== SourceLine 3
    public int i=0;
    // JavaLine 10 <== SourceLine 8
    public RTS_Simset _inspect_8_1=null;
    // Normal Constructor
    public adHoc03_Block3(RTS_RTObject staticLink) {
        super(staticLink);
        BBLK();
        // Declaration Code
    }
    // 5 Statements
    @Override
    public RTS_RTObject _STM() {
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
        EBLK();
        return(this);
    } // End of 5 Statements
    public static RTS_PROGINFO _INFO=new RTS_PROGINFO("adHoc03.sim","5 Block3",8,3,10,8,35,13);
} // End of SubBlock

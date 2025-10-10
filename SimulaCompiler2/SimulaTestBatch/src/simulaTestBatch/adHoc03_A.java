// JavaLine 1 <== SourceLine 3
package simulaTestBatch;
// Simula-2.0 Compiled at Thu Oct 09 12:12:54 CEST 2025
import simula.runtime.*;
@SuppressWarnings("unchecked")
public class adHoc03_A extends RTS_Process {
    // ClassDeclaration: Kind=9, BlockLevel=2, PrefixLevel=3, firstLine=3, lastLine=5, hasLocalClasses=false, System=false, detachUsed=false
public boolean isDetachUsed() { return(true); }
    // Declare parameters as attributes
    // Declare locals as attributes
    // Normal Constructor
    public adHoc03_A(RTS_RTObject staticLink) {
        super(staticLink);
        // Parameter assignment to locals
        // Declaration Code
    }
    // Class Statements
    @Override
    public adHoc03_A _STM() {
        // BEGIN Linkage INNER PART
        // BEGIN Link INNER PART
        // JavaLine 22 <== SourceLine 1
        detach(); // Process'detach
        // BEGIN Process INNER PART
        // JavaLine 25 <== SourceLine 5
        // BEGIN A INNER PART
        // ENDOF A INNER PART
        // ENDOF Process INNER PART
        // JavaLine 29 <== SourceLine 1
        terminate(); // Process'terminate
        // ENDOF Link INNER PART
        // ENDOF Linkage INNER PART
        EBLK();
        return(this);
    } // End of Class Statements
    public static RTS_PROGINFO _INFO=new RTS_PROGINFO("adHoc03.sim","9 A",1,3,22,1,25,5,29,1,35,5);
} // End of Class

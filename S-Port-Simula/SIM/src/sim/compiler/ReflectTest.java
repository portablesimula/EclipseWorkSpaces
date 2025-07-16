package sim.compiler;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import sim.editor.ConsolePanel;

public class ReflectTest {
	private static Method write;
	private static Object console;
	
	private static final boolean DEBUG = false;

	// ****************************************************************
	// *** SimulaEditor: Main Entry for TESTING ONLY
	// ****************************************************************
     public static void main(String[] args) {
    	 JFrame frame = new JFrame("Testing");
         frame.setSize(800, 1000);
         frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         frame.setLocationRelativeTo(null); // center the frame on screen

         Global.consolePanel = new ConsolePanel();
     	 frame.add(Global.consolePanel);
     	 Global.consolePanel.write("BEGIN TESTING ConsolePanel\n");
    	 frame.setVisible(true);
    	 
    	 Class<?> class_ConsolePanel = getClass("sim.editor.ConsolePanel");
    	 Class<?> class_Global = getClass("sim.compiler.Global");
    	 Field field_console = getField(class_Global, "console");
    	 write = getMethod(class_ConsolePanel, "write", String.class);
    	 console = getValue(class_Global, field_console);
    	 
//    	 printOnDisplay("DETTE VAR BRA !");
//    	 if (console != null) invoke(console, write, "DETTE VAR BRA !\n");
    	 
    	 
//    	 Method getReader = getMethod(class_ConsolePanel, "getReader");
//    	 Method getWriter = getMethod(class_ConsolePanel, "getWriter");
    	 try {
//	    	 Writer writer = (Writer) invoke(console, getWriter);
//	    	 Reader reader = (Reader) invoke(console, getReader);
    		 Writer writer = getConsoleWriter();
    		 Reader reader = getConsoleReader();
	    	 LineNumberReader lineReader = new LineNumberReader(reader);
	    	 LOOP:for(int i=0;i<50;i++) {
	    		 writer.write("Input line:"); writer.flush();
	    		 String line = lineReader.readLine();
	    		 if(line.length() == 0) {
		    		 writer.write("ALL READING DONE");
	    			 break LOOP;
	    		 }
	    		 System.out.println("READING DONE: " + line);
	    	 }
	    	 writer.close();
	    	 reader.close();
    	 } catch (IOException e) {}

    	 while(true)
			try { Thread.sleep(10000); } catch (InterruptedException e) {} 
     }
     
     
     private static Class<?> getClass(String... names) {
    	 for(String name:names)
	    	 try { return Class.forName(name); } catch (ClassNotFoundException e) {}
		 return null;
     }
     
     public static Writer getConsoleWriter() {
    	 try {
//	    	 Class<?> consolePanel = Class.forName("sim.editor.ConsolePanel");
//	    	 Class<?> global = Class.forName("sim.compiler.Global");
	    	 Class<?> consolePanel = getClass("simula.compiler.utilities.ConsolePanel", "sim.editor.ConsolePanel");
	    	 Class<?> global       = getClass("simula.compiler.utilities.Global",       "sim.compiler.Global");
	    	 Field field_console = global.getDeclaredField("console");
	    	 Method getWriter = consolePanel.getDeclaredMethod("getWriter");
    		 Object console = field_console.get(global);
    		 return (Writer) getWriter.invoke(console);
 		} catch(Exception e) { return null; }
     }
     
     public static Reader getConsoleReader() {
    	 try {
//	    	 Class<?> consolePanel = Class.forName("sim.editor.ConsolePanel");
//	    	 Class<?> global = Class.forName("sim.compiler.Global");
	    	 Class<?> consolePanel = getClass("simula.compiler.utilities.ConsolePanel", "sim.editor.ConsolePanel");
	    	 Class<?> global       = getClass("simula.compiler.utilities.Global",       "sim.compiler.Global");
	    	 Field field_console = global.getDeclaredField("console");
	    	 Method getReader = consolePanel.getDeclaredMethod("getReader");
    		 Object console = field_console.get(global);
	    	 return (Reader) getReader.invoke(console);
  		} catch(Exception e) { return null; }
     }

     
     
     public static Class<?> getClass(String name) {
    	 try {
    		 Class<?> clazz = Class.forName(name);
    		 if (DEBUG) System.out.println("ReflectTest.getClass: " + name + " ==> " + clazz);
    		 return clazz;
    	 } catch (ClassNotFoundException e) {
    		 e.printStackTrace();
    		 return null;
    	 }
     }

     public static Field getField(Class<?> clazz, String name) {
    	 try {
    		 Field field = clazz.getDeclaredField(name);
    		 if (DEBUG) System.out.println("ReflectTest.getField: " + name + " ==> " + field);
    		 return field;
    	 } catch (NoSuchFieldException e) {
    		 e.printStackTrace();
    		 return null;
    	 }
     }
     
     public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
    	 try {
    		 Method method = clazz.getDeclaredMethod(name, parameterTypes);
    		 if (DEBUG) System.out.println("ReflectTest.getMethod: " + name + " ==> " + method);
    		 return method;
    	 } catch (NoSuchMethodException e) {
    		 e.printStackTrace();
    		 return null;
    	 }
     }

     public static Object getValue(Class<?> clazz, Field field) {
    	 try {
    		 Object value = field.get(clazz);
    		 if (DEBUG) System.out.println("ReflectTest.getValue: " + clazz + " ==> " + value.getClass());
    		 return value;
    	 } catch (IllegalArgumentException | IllegalAccessException e) {
    		 e.printStackTrace();
    		 return null;
    	 }
     }
     
     public static Object invoke(Object obj, Method method, Object... args) {
		 try {
			 Object res = method.invoke(obj, args);
    		 if (DEBUG) {
    			 String s = (res == null)? "null" : res.getClass().getSimpleName();
    			 System.out.println("ReflectTest.invoke: " + obj.getClass().getSimpleName() + '.' + method + " ==> " + s);
    		 }
			 return res;
		 } catch (IllegalAccessException | InvocationTargetException e) {
			 e.printStackTrace();
			 return null;
		 }
    	 
     }
     
     
     
}

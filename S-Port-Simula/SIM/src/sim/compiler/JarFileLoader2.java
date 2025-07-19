package sim.compiler;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarFileLoader2 {
	private myURLClassLoader classLoader;
	
	private static final boolean DEBUG = true;
	
	class myURLClassLoader extends URLClassLoader{

		public myURLClassLoader(URL[] urls, ClassLoader parent) {
			super(urls, parent);
			// TODO Auto-generated constructor stub
		}
		
		public Class<?> findMyLoadedClass(String name) {
			return this.findLoadedClass(name);
		}
	}
	
	public JarFileLoader2(String jarFileName) {
		File jarFile = new File(jarFileName);
		URL jarUrl = null;
		try { jarUrl = jarFile.toURI().toURL(); } catch (MalformedURLException e) { e.printStackTrace(); }
		if(DEBUG) {
			System.out.println("JarFileLoader: jarUrl="+jarUrl);
			Util.listJarFile(jarFile);
		}

	    URL[] urls = new URL[]{jarUrl};
//	    classLoader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());
	    classLoader = new myURLClassLoader(urls, ClassLoader.getSystemClassLoader());
	    try { loadAll(classLoader, jarFile); } catch(IOException e) { e.printStackTrace(); }
	}
	
	private void loadAll(myURLClassLoader classLoader, File jarFile) throws IOException {
	    JarFile jar = new JarFile(jarFile);
	    Enumeration<JarEntry> entries = jar.entries();
	    while (entries.hasMoreElements()) {
	        JarEntry entry = entries.nextElement();
	        if (entry.getName().endsWith(".class")) {
	            String className = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);
	            
				System.out.println("JarFileLoader.loadAll: className="+className);
				try {
					classLoader.loadClass(className);
					Class<?> clazz = classLoader.findMyLoadedClass(className);
					System.out.println("JarFileLoader.loadAll: clazz="+clazz);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
	        }
	    }
	    jar.close();		
	}
	
	public Package[] getDefinedPackages() {
		return classLoader.getDefinedPackages();
	}

	public Class<?> findMyLoadedClass(String name) {
		return classLoader.findMyLoadedClass(name);
	}
	
	public void invokeMain(String className, String[] args) {
        if(Option.verbose) {
    		String line="";
    		for(int i=0;i<args.length;i++) line=line+" "+args[i];
        	System.out.println("JarFileLoader.invokeMain: with args="+line);
        }
		Class<?> loadedClass = null;
    	try { loadedClass = classLoader.loadClass(className); } catch (ClassNotFoundException e) { e.printStackTrace(); }
    	if(DEBUG)
    		System.out.println("JarFileLoader: loadedClass="+loadedClass);
		
		Method mainMethod = null;
		try { mainMethod = loadedClass.getDeclaredMethod("main", String[].class); } catch (NoSuchMethodException e) { e.printStackTrace(); }
		if(DEBUG)
			System.out.println("JarFileLoader: mainMethod="+mainMethod);

		// Invoke the main method with arguments
	    try { mainMethod.invoke(null, (Object) args); } catch (IllegalAccessException | InvocationTargetException e) { e.printStackTrace(); }
//		try { mainMethod.invoke(loadedClass, (Object) args); } catch (IllegalAccessException | InvocationTargetException e) { e.printStackTrace(); }
	}
	
	
	
}

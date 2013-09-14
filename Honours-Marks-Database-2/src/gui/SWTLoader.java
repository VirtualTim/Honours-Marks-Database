package gui;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;


public class SWTLoader {
	public static void loadSwtJar() {
		String swtFileName = null;
	    try {
	        String osName = System.getProperty("os.name").toLowerCase();
	        String osArch = System.getProperty("os.arch").toLowerCase();
	        //URLClassLoader classLoader = (URLClassLoader) getClass().getClassLoader();
	        Class<?> currentClass = new Object() { }.getClass().getEnclosingClass();
	        URLClassLoader classLoader = (URLClassLoader) currentClass.getClassLoader();
	        Method addUrlMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
	        addUrlMethod.setAccessible(true);

	        String swtFileNameOsPart = 
	            osName.contains("win") ? "win32" :
	            osName.contains("mac") ? "macosx" :
	            osName.contains("linux") || osName.contains("nix") ? "linux_gtk" :
	            ""; // throw new RuntimeException("Unknown OS name: "+osName)

	        String swtFileNameArchPart = osArch.contains("64") ? "x64" : "x86";
	        swtFileName = "org.eclipse.swt_"+swtFileNameOsPart+"_"+swtFileNameArchPart+"/swt.jar";
	        URL swtFileUrl = new URL("rsrc:"+swtFileName); // I am using Jar-in-Jar class loader which understands this URL; adjust accordingly if you don't
	        addUrlMethod.invoke(classLoader, swtFileUrl);
	    }
	    catch(Exception e) {
	        System.out.println("Unable to add the swt jar to the class path: "+swtFileName);
	        e.printStackTrace();
	    }
	}
}

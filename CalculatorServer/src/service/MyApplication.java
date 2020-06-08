package service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
public class MyApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	 
    public MyApplication() {
        singletons.add(new Calculator());
    }
 
//    @Override
//    public Set<Object> getSingletons() {
//        return singletons;
//    }
    @SuppressWarnings("unchecked")
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(Arrays.asList(Calculator.class));
    }
}
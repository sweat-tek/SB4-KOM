package dk.sdu.mmmi.cbse.common.util;

import java.util.*;

public class SPIAsteroidsLocator {

    private static final Map<Class, ServiceLoader> locator = new HashMap<>();

    private SPIAsteroidsLocator(){

    }

    public static <T>List<T> locate(Class<T> gameservices){
        ServiceLoader<T> loadService = locator.get(gameservices);

        boolean printstatus = false;

        if(loadService == null){
            loadService = ServiceLoader.load(gameservices);
            locator.put(gameservices, loadService);
            printstatus = true;
        }

        List<T> serviceList = new ArrayList<>();

        if (loadService != null){
            try{
                for (T item : loadService){
                    serviceList.add(item);
                }
            } catch (ServiceConfigurationError error){
                error.printStackTrace();
            }

        }

        if (printstatus) {
            System.out.println("Found " + serviceList.size() + " implementations for interface: " + gameservices.getName());
        }
        return serviceList;
    }
}

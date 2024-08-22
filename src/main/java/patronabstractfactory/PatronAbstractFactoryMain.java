package patronabstractfactory;

import abstractfactory.bases.IServiciosBasesGenericos;
import abstractfactory.bases.ServiciosGenericosAbstractFactory;
import servicios.IServicioWeather;

import java.util.Arrays;

public class PatronAbstractFactoryMain {


    public static void main(String[] args) {

        IServiciosBasesGenericos factory =
                ServiciosGenericosAbstractFactory.createServiceFactory();
        IServicioWeather servicioWeather = factory.displayWeatherData();

        System.out.println("Servicio Weather class > "
                + servicioWeather.getClass().getCanonicalName());

        servicioWeather.displayWeatherData();


    }

}
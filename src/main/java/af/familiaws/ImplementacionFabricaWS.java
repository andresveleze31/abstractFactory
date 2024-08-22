package af.familiaws;

import abstractfactory.bases.IServiciosBasesGenericos;
import servicios.IServicioWeather;

public class ImplementacionFabricaWS implements IServiciosBasesGenericos {

    @Override
    public IServicioWeather displayWeatherData() {
        return new ImplementacionWsWeather();
    }
}
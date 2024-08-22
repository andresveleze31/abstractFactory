package af.familiarest;

import abstractfactory.bases.IServiciosBasesGenericos;
import servicios.IServicioWeather;

public class ImplementacionFabricaRestful implements IServiciosBasesGenericos {

    @Override
    public IServicioWeather displayWeatherData() {
        return new ImplementacionRestWeather();
    }



}

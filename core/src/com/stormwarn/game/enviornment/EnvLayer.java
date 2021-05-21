package com.stormwarn.game.enviornment;

public class EnvLayer {
    private double temp;

    private double windSpeed;

    private double windDirection;

    private double pressure;

    private double humidity;

    public EnvLayer() {

    }

    public EnvLayer(double temp, double windSpeed, double windDirection, double pressure, double humidity) {

    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}

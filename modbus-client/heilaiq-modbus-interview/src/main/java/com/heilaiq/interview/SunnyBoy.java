package com.heilaiq.interview;

public class SunnyBoy {

    public long timestamp;
    public double real_power;
    public double real_power_setpoint;
    public double reactive_power;
    public double reactive_power_setpoint;

    public SunnyBoy(long timestamp, double real_power, double real_power_setpoint, double reactive_power, double reactive_power_setpoint){
        this.timestamp = timestamp;
        this.real_power = real_power;
        this.real_power_setpoint = real_power_setpoint;
        this.reactive_power = reactive_power;
        this.reactive_power_setpoint = reactive_power_setpoint;
    }

}
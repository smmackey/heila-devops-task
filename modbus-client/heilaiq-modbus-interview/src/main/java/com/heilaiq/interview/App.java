package com.heilaiq.interview;

import com.heilaiq.communication.CommunicationApi;
import com.heilaiq.communication.CommunicationManager;
import com.heilaiq.communication.modbus.ModbusReadAction;
import com.heilaiq.communication.modbus.ModbusReadFunction;
import com.heilaiq.communication.modbus.ModbusWriteAction;
import com.heilaiq.communication.modbus.ModbusWriteFunction;
import com.heilaiq.lib.configuration.communication.channels.ModbusTCPChannelConfiguration;

import java.io.IOException;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.lang.Double;
import java.lang.Integer;

import java.util.Arrays;

public class App {
    CommunicationApi communicationApi;
    String channelKey;
    HttpPost poster = new HttpPost();
    int unitId = 1;

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.start();
    }
    public void start() throws Exception {
        String ipAddress = "localhost";
        CommunicationManager communicationManager = new CommunicationManager(false);
        communicationApi = CommunicationApi.getInstance();
        ModbusTCPChannelConfiguration config = new ModbusTCPChannelConfiguration(
            ipAddress,
            502
        );
        channelKey = communicationApi.addChannel(config);
        communicationApi.openChannel(channelKey);

        Random random = new Random();
        boolean keepRunning = true;
        while (keepRunning) {
            try {
                int[] setPoints = writeSetPoints(random);
                int realPowerSetpoint = setPoints[0];
                int reactivePowerSetpoint = setPoints[1];

                // read holdings 0 for real power, 1 for current
                double[] holdings = readHoldings(0, 2);
                double realPower = holdings[0];
                double current = holdings[1];

                // reactive power comes from input registers
                double reactivePower = readInputReg(0)[0];

                post(realPower, realPowerSetpoint, reactivePower, reactivePowerSetpoint);

                // sleep so we are not measuring every clockcycle. 1 second was arbitrarily chosen.
                TimeUnit.SECONDS.sleep(1);
            }
            catch(InterruptedException e) {
                System.out.println("Got keyboard interrupt. Shutting down client data generation.");
                keepRunning = false;

            }
        }
    }

    /**
     * Generates random setpoints for real and reactive power and writes them to registers 0 and 1 respectively.
     * @param random Random object used to generate random numbers.
     */
    private int[] writeSetPoints(Random random) {
        // gets a random number between -5000 and 5000
        int realPowerWrite = (random.nextInt(5000)) * (random.nextBoolean() ? -1 : 1);
        int reactivePowerWrite = (random.nextInt(5000)) * (random.nextBoolean() ? -1 : 1);

        writeHolding(0, realPowerWrite);
        writeHolding(1, reactivePowerWrite);

        int[] retVal = {realPowerWrite, reactivePowerWrite};
        return retVal;
    }

    // used to read the reactive power
    private double[] readInputReg(int offset) {
        ModbusReadAction modbusReadAction = new ModbusReadAction(ModbusReadFunction.INPUT_REGISTERS, offset, 1, unitId);
        try {
            return communicationApi.read(channelKey, modbusReadAction);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new double[1];
    }

    // used to read real power
    private double[] readHoldings(int offset, int size) {
        ModbusReadAction modbusReadAction = new ModbusReadAction(ModbusReadFunction.HOLDINGS, offset, size, unitId);
        try {
            return communicationApi.read(channelKey, modbusReadAction);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new double[size];
    }

    private void writeHolding(int offset, int size) {
        ModbusWriteAction modbusWriteAction = new ModbusWriteAction(ModbusWriteFunction.HOLDING, offset, size, unitId);
        try {
            communicationApi.write(channelKey, modbusWriteAction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void post(double real_power, double real_power_setpoint, double reactive_power, double reactive_power_setpoint) {
        SunnyBoy boy = new SunnyBoy(
                Instant.now().toEpochMilli(),
                real_power,
                real_power_setpoint,
                reactive_power,
                reactive_power_setpoint
        );

        try {
            poster.postData("http://localhost:5000/sunnyboy_inverter", boy);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}


// Below is API code that should help you form the requests that need to be sent to the modbus
// device.

/*
------------------------------------------------------------

ModbusReadAction(ModbusReadFunction readFunction, int offset, int registers, int unitId)

------------------------------------------------------------

public enum ModbusReadFunction {

    COILS(1),
    INPUT_DISCRETES(2),
    HOLDINGS(3),
    INPUT_REGISTERS(4);

    private int value;
    ModbusReadFunction(int value){ this.value = value; }
}

------------------------------------------------------------

ModbusWriteAction(ModbusWriteFunction writeFunction, int offset, int[] values, int unitId)
ModbusWriteAction(ModbusWriteFunction writeFunction, int offset, int value, int unitId)

------------------------------------------------------------

public enum ModbusWriteFunction {
    COIL(5),
    HOLDING(6),
    MULTIPLE_COILS(15),
    MULTIPLE_HOLDINGS(16);

    private int value;
    ModbusWriteFunction(int value){ this.value = value; }
}

------------------------------------------------------------



*/

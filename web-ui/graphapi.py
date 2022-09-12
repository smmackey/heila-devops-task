import subprocess
import time
from datetime import datetime
import calendar

all_data = {}
def get_data(assetname):
    global all_data
    if assetname == "sunnyboy_inverter":
        if assetname in all_data:
            return all_data[assetname]
        else:
            return {
                    "real_power" : { "x" : 0, "y": 0 } ,
                    "reactive_power" : { "x" : 0, "y" : 0 } ,
                    "real_power_setpoint": { "x" : 0, "y" : 0 }, 
                    "reactive_power_setpoint" : { "x" : 0, "y" : 0 } 
                    }


def set_data(assetname, timestamp, real_power, reactive_power, real_power_setpoint, reactive_power_setpoint):
    global all_data
    if assetname == "sunnyboy_inverter":
        timestamp = timestamp
        data = {
                "real_power" : { "x" : timestamp, "y": real_power } ,
                "reactive_power" : { "x" : timestamp, "y" : reactive_power } ,
                "real_power_setpoint": { "x" : timestamp, "y" : real_power_setpoint }, # for l in lines],
                "reactive_power_setpoint" : { "x" : timestamp, "y" : reactive_power_setpoint } # for l in lines]
                }
        all_data[assetname] = data
        return all_data[assetname]

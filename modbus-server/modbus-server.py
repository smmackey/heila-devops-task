
import logging
from socketserver import TCPServer
from collections import defaultdict

from umodbus import conf
from umodbus.server.tcp import RequestHandler, get_server
from umodbus.utils import log_to_stream

import time;

# Add stream handler to logger 'uModbus'.
log_to_stream(level=logging.DEBUG)

# Enable values to be signed (default is False).
conf.SIGNED_VALUES = True

TCPServer.allow_reuse_address = True
app = get_server(TCPServer, ('localhost', 502), RequestHandler)

clamp = lambda n, minn, maxn: max(min(maxn, n), minn)

setpoints = [0] * 10

@app.route(slave_ids=[1], function_codes=[3, 4], addresses=list(range(0, 10)))
def read_data_store(slave_id, function_code, address):
    global setpoints
    voltage = int(270 + (10 - time.time() % 10)) #simulate voltage fluctuation as a function of time.
    reactive_power = setpoints[1]
    voltage -= reactive_power / 5000 * 5 # reactive power affects voltage
    current = int(setpoints[0] / voltage)
    power = int(voltage * current)

    if function_code == 3:
        if address == 0:
            return power
        if address == 1:
            return current
    if function_code == 4:
        if address == 0:
            return reactive_power
    return 0

# to change real power, write to address 0
# to change reactive power, write to address 1.
@app.route(slave_ids=[1], function_codes=[6, 15, 16], addresses=list(range(0, 10)))
def write_data_store(slave_id, function_code, address, value):
    global setpoints
    
    value = clamp(value, -5000, 5000)
    setpoints[address] = value
    pass


if __name__ == '__main__':
    try:
        app.serve_forever()
    finally:
        app.shutdown()
        app.server_close()

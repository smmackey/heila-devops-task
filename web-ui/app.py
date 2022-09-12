from flask import Flask, render_template, jsonify, request
import graphapi as GraphApi
import calendar
import time

app = Flask(__name__)

@app.route("/sunnyboy_inverter")
def get_sunnyboy_inverter():
    return jsonify(GraphApi.get_data("sunnyboy_inverter"))

@app.route("/sunnyboy_inverter", methods=['POST'])
def set_sunnyboy_inverter():
    print("got post")
    params = request.json
    ts = params["timestamp"]
    real_power = params["real_power"]
    reactive_power = params["reactive_power"]
    real_power_setpoint = params["real_power_setpoint"]
    reactive_power_setpoint = params["reactive_power_setpoint"]
    return jsonify(GraphApi.set_data("sunnyboy_inverter", ts, real_power, reactive_power, real_power_setpoint, reactive_power_setpoint))

@app.route("/")
def index():
    return render_template("index.html")

if __name__ == "__main__":
    app.run(host= '0.0.0.0')

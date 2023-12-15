import os
import datetime
from flask import Flask, request, jsonify, Response
from prometheus_client import Gauge, generate_latest, REGISTRY
import requests
import numpy as np
import tensorflow as tf
from tensorflow import keras
import logging

app = Flask(__name__)

# Metrics untuk Prometheus
cpu_metric = Gauge('cpu_usage_percentage', 'CPU Usage Percentage', ['instance', 'timestamp'])
anomaly_counter = Gauge('anomaly_cpu_detected', 'Anomaly CPU Detected', ['instance', 'timestamp'])

# Memuat model H5
anomaly_model = keras.models.load_model('model_cpu.h5')

# Setup logging
logging.basicConfig(level=logging.DEBUG)

def detect_anomaly_cpu(cpu_metrics):
    # Menggunakan model untuk deteksi anomali
    prediction = anomaly_model.predict(np.array(cpu_metrics).reshape(1, -1))
    logging.debug(f"Prediction: {prediction}")
    return 1 if prediction > 0.69 else 0

def get_cpu_usage():
    url = "http://202.43.249.22:9090/api/v1/query?query=sum%20by%20(instance)(irate(node_cpu_seconds_total%7Bmode%21%3D%22idle%22%7D[1m]))%20%2F%20on(instance)%20group_left%20sum%20by%20(instance)%20(irate(node_cpu_seconds_total[1m]))%20*%20100"
    response = requests.get(url)
    data = response.json()
    return data

def send_anomaly_data_to_prometheus(instance, cpu_percent, anomaly_prediction, timestamp):
    # Set nilai metrik Prometheus
    cpu_metric.labels(instance=instance, timestamp=timestamp).set(cpu_percent)
    anomaly_counter.labels(instance=instance, timestamp=timestamp).set(anomaly_prediction)

@app.route('/')
def home():
    return 'Hello, Prometheus!'

@app.route('/metrics', methods=['GET'])
def prometheus_metrics():
    # Mendapatkan data metrik Prometheus yang sudah di-set sebelumnya
    metrics_data = generate_latest(REGISTRY)

    # Mengembalikan data metrik dalam format Prometheus
    return Response(metrics_data, content_type="text/plain; version=0.0.4")

@app.route('/metrics-generate', methods=['GET', 'POST'])
def process_metrics():
    try:
        # Mendapatkan data metrics dari Prometheus
        data = get_cpu_usage()
        instance = data['data']['result'][0]['metric']['instance']
        cpu_percent = float(data['data']['result'][0]['value'][1]) 

        # Memproses metrics menggunakan model H5
        anomaly_prediction = detect_anomaly_cpu(cpu_percent)

        # Menyimpan data ke metrics Prometheus dengan timestamp
        timestamp = datetime.datetime.now().isoformat()
        send_anomaly_data_to_prometheus(instance, cpu_percent, anomaly_prediction, timestamp)

        # Memberikan indikasi jika terdeteksi anomali
        indication = "Anomaly Detected!" if anomaly_prediction == 1 else "No Anomaly Detected"

        # Mengembalikan respon ke client
        response = {
            'status': 'success',
            'data': {
                'instance': instance,
                'timestamp': timestamp,
                'cpu_percent': cpu_percent,
                'anomaly_prediction': anomaly_prediction,
                'indication': indication
            }
        }
        
        return jsonify(response)
    except Exception as e:
        response = {
            'status': 'error',
            'data': {
                'message': str(e)
            }
        }
        return jsonify(response), 500

if __name__ == '__main__':
    # Menjalankan aplikasi Flask
    app.run(debug=True)

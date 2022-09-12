FROM python:3.9

# Setup the virtual env
ENV VIRTUAL_ENV=venv
RUN python3 -m venv ${VIRTUAL_ENV}
ENV PATH="${VIRTUAL_ENV}/bin:$PATH"

# Install dependencies
COPY requirements.txt .
RUN pip install -r requirements.txt

EXPOSE 502

# Run application
ADD modbus-server.py .
CMD ["python3", "./modbus-server.py"]

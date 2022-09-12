FROM python:3.9

# Setup the virtual env
ENV VIRTUAL_ENV=venv
RUN python3 -m venv ${VIRTUAL_ENV}
ENV PATH="${VIRTUAL_ENV}/bin:$PATH"

WORKDIR /app

# Install dependencies
COPY requirements.txt /app/requirements.txt
RUN pip install -r requirements.txt

# Run application
COPY . /app

CMD ["python3", "./app.py"]

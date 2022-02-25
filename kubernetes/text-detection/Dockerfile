FROM python:3.7.9

COPY . /deploy/

WORKDIR /deploy
RUN pip3 install -r requirements.txt
EXPOSE 80

ENTRYPOINT ["python3", "main.py"]

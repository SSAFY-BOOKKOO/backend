FROM prom/prometheus

# copy the Prometheus configuration file
COPY ./prometheus.yml /etc/prometheus/prometheus.yml

# set environment variables
ENV TZ=Asia/Seoul

# set the entrypoint command
ENTRYPOINT [ "prometheus", \
             "--config.file=/etc/prometheus/prometheus.yml" ] 
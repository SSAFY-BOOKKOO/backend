FROM grafana/promtail:latest

# copy the Prometheus configuration file
COPY ./promtail-config.yml /etc/promtail/config.yml

# set environment variables
ENV TZ=Asia/Seoul

# set the entrypoint command
ENTRYPOINT [ "promtail", \
             "-config.file=/etc/promtail/config.yml" ] 
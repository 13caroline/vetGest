FROM nginx
WORKDIR /nginx

RUN rm /etc/nginx/conf.d/default.conf

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY ./data/certs/ /etc/nginx/certs/

RUN cat /etc/nginx/certs/cert.pem
RUN cat /etc/nginx/certs/key.pem

RUN cat /etc/nginx/conf.d/default.conf
EXPOSE 7777
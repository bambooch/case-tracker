#!/bin/sh
set -eu

cert_dir="/etc/letsencrypt/live/${PUBLIC_DOMAIN}"

if [ -f "${cert_dir}/fullchain.pem" ] && [ -f "${cert_dir}/privkey.pem" ]; then
    cp /etc/nginx/templates/https.conf.template /etc/nginx/templates/default.conf.template
else
    cp /etc/nginx/templates/http.conf.template /etc/nginx/templates/default.conf.template
fi
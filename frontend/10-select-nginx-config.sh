#!/bin/sh
set -eu

cert_dir="/etc/letsencrypt/live/${PUBLIC_DOMAIN}"
template_dir="/opt/nginx/templates"

if [ -f "${cert_dir}/fullchain.pem" ] && [ -f "${cert_dir}/privkey.pem" ]; then
    cp "${template_dir}/https.conf.template" /etc/nginx/templates/default.conf.template
else
    cp "${template_dir}/http.conf.template" /etc/nginx/templates/default.conf.template
fi
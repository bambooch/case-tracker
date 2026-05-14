FROM node:22-alpine AS builder
WORKDIR /app
COPY package.json package-lock.json ./
# The lock file's resolved URLs point to the corporate npm proxy;
# rewrite them to the public registry for Docker builds.
RUN sed -i 's|https://service.grafit.net/npm/|https://registry.npmjs.org/|g' package-lock.json
RUN npm ci
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80

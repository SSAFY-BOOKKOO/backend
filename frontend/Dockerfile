# Step 1: Build the React application
FROM node:20.15.1 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install -g npm@10.7.0
RUN npm install
COPY . .
RUN npm run build

# Step 2: Serve the React application using Nginx
FROM nginx:alpine

# Nginx 설정 파일을 이미지에 복사
COPY default.conf /etc/nginx/conf.d/default.conf

COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

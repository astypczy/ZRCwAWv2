# Etap budowania
FROM node:20 AS build
WORKDIR /app

# Instalacja zależności
COPY package*.json ./
RUN npm install

# Kopiowanie kodu źródłowego
COPY . .

# Budowanie aplikacji
RUN npm run build

# Etap produkcyjny
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Kopiowanie zbudowanej aplikacji
COPY --from=build /app/dist/frontend/browser/ .

# Kopiowanie konfiguracji Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Ekspozycja portu
EXPOSE 80

# Uruchomienie Nginx
CMD ["nginx", "-g", "daemon off;"]

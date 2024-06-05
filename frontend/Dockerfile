FROM node:22
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN echo "VITE_BACKEND_API_BASE_URL=http://localhost:8080" > .env
RUN npm run build
EXPOSE 5000
CMD ["npm", "run", "preview"]

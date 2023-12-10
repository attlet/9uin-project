FROM node:18.16.0
WORKDIR /app
COPY package.json .
RUN yarn install
ENV REACT_APP_NODE_ENV=local

COPY . .

EXPOSE 3000
ENTRYPOINT ["yarn", "start:local"]


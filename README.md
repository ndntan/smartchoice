# SMARTCHOICE


Copyright 2021, Tan Nguyen Dang Nhut ndnhuttan@outlook.com


## About The Project

As statistics assure us, more than 80% of users take advantage of special websites to find the cheapest prices. Such web-based aggregators are useful to all participants in the process of buying a product or ordering a service. Judge for yourself: they help customers make affordable purchases and, on the other hand, serve as assistants to different vendors in looking for new clients. Moreover, the owners of aggregators don't remain at a loss either. So what we’re observing here is undeniably a classic win-win situation.

And if you don’t belong to suppliers of goods or service providers, we think it’s high time you joined the ranks of those who earn on shopping comparison sites. This is a profitable investment and a sure way to make good money. All you have to do is develop a custom website for comparing prices. 

Does it sound complicated? Don't worry, we're going to help you clarify the issue step by step. We've conducted a thorough analysis and prepared detailed material on this topic. And, of course, we're always ready to answer your questions and advise if necessary.

So let’s find out how to start a price comparison business.

### Where to get data to implement the comparison feature
Before moving on, let's consider a few technical solutions for price comparison website development and explore their pros and cons. Then you'll be able to choose the option that best suits your specific project.

#### Web scraping
This is a very popular way to get comparison data, although not always legally approved. It’s about web crawlers or spiders periodically visiting pre-selected sites to collect the necessary information. 

It acts as if it scrapes data from the web platform (hence the name - web scraping).

The method is rather simple: a specially trained algorithm, which is also an automated code, goes to the main page of an online resource and begins to visit each internal link, parse an HTML document, search for data, and convert it to the specified format.

##### Pros:
getting data is very easy; you don’t even need permission from the owner of the resource;

there is a chance to increase the database to the level of competitors in a short time period;

the solution is quite simple to implement.

##### Cons:
you'll be unable to use the fee-based monetization model;

some resources have protection against data copying;

the source code may contain links to the same images of different sizes (say, previews);

the site is able to determine the country in which your server is located and provide you with information in your language (not in English);

the scraping method isn’t approved by everyone.

As you can see, the method has more cons than pros. One more important thing: creating scraping from scratch is quite expensive, and turnkey solutions aren't always optimal.

#### APIs
Among the digital comparison tools, API integration is being widely-used. The method involves obtaining information directly from the vendors themselves, via the API. The received data is being automatically entered into your database.

##### Pros:
it’s possible to receive information in the right format and quality;

the method is legal, approved by all participants in the process;

it’s very easy to monetize your resource through fees;

you’ll be obtaining real-time information.

##### Cons:
the method is quite resource-intensive;

there are sites, which don’t provide their API.

The model is especially good if most pricing sites use the API and are willing to share it with you. And we're pretty sure they'll be happy to give you access to their data: in the end, it's beneficial to them too (we've described the benefits above, remember?).

#### Manually adding data
This isn’t the most convenient way to build a price comparison website, of course. Though, we're going to describe it anyway.

So, if sellers don't have an API but want to participate in your program, they can provide you with their information in the form of XML and CSV files. Or you may let them enter data manually. 

##### Pros:
the method is easy to implement;

you get the necessary data completely legally, with the approval of the vendors.

##### Cons:
extra expenses might be required: you'll either have to hire employees to enter data into your database or create a special interface so that vendors would do it themselves;

the method isn’t very convenient, given the manual way of entering data.

Such a model is suitable as an addition to the above-described API option.

#### On-demand Data
The approach is being used when it comes to specific goods and services (insurance, loans, digital products) or when expert assistance is required (if indicating the price itself isn't enough).

Imagine the situation: the customer chooses a credit card and, of course, wants to know the advantages and disadvantages of each option: say, whether there are any unacceptable conditions or strings attached. So he needs an expert opinion. Your product price comparison website sends a message to the vendor about the request received, and the vendor may satisfy it (if he wants to).

The pros and cons are about the same as in the case of manually adding data: the model is easy to implement but inconvenient in subsequent use.

### In this project we will use API solution to fetch products from the well-known suppliers


## Product processor service 0.0.1-SNAPSHOT
This is the main application where the user can search the products and also it handles almost the data from some suppliers

## Tiki supplier service 0.0.1-SNAPSHOT

This service will fetch the data from the Tiki platform when receiving a request from Product processor.

## Sendo supplier service 0.0.1-SNAPSHOT

This service will fetch the data from the Sendo platform when receiving a request from Product processor.

## Design solution
### UML diagram

![category](https://user-images.githubusercontent.com/80661119/111285159-6ba0e680-8673-11eb-88d1-ba1e78c6144b.png)

### Architecture

![Smartchoice architect](https://user-images.githubusercontent.com/80661119/111290800-18319700-8679-11eb-96c9-e0bcb5880291.png)


	
## Prerequisites

#### Packages are required
* JDK 11
* Maven
* Docker


##### Deploy these below services into stack
```shell
docker stack deploy --compose-file infrastructure.yml smartchoice
```

###### infrastructure.yml
```text
version: '3'
services:
  rabbitmq:
    image: rabbitmq:3.8-management
    ports:
      - "5672:5672"
    deploy:
      mode: replicated
      replicas: 1
      placement:
        constraints: [node.role == manager]
      labels:
        com.df.notify: "true"
        com.df.servicePath: "/rabbitmq/"
        com.df.port: "15672"
        com.df.reqPathSearchReplace: "/rabbitmq/,/"
    networks:
      - proxy
      - default
    environment:
      RABBITMQ_DEFAULT_USER: "guest"
      RABBITMQ_DEFAULT_PASS: "guest"
  postgresql-db:
    image: mdillon/postgis:11
    environment:
      POSTGRES_USER: tan
      POSTGRES_PASSWORD: tandb
      POSTGRES_DB: smartchoice
    ports:
      - "5432:5432"
    deploy:
      mode: replicated
      replicas: 1
      placement:
        constraints: [node.role == manager]
    networks:
      - default
    logging:
      driver: "json-file"
      options:
        max-file: "5"
        max-size: "10m"
  proxy:
    image: dockerflow/docker-flow-proxy
    ports:
      - "80:80"
    networks:
      - proxy
    environment:
      LISTENER_ADDRESS: swarm-listener
      MODE: swarm
    deploy:
      mode: replicated
      replicas: 1
    logging:
      driver: "json-file"
      options:
        max-file: "5"
        max-size: "10m"
  swarm-listener:
    image: dockerflow/docker-flow-swarm-listener
    networks:
      - proxy
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
    environment:
      DF_NOTIFY_CREATE_SERVICE_URL: http://proxy:8080/v1/docker-flow-proxy/reconfigure
      DF_NOTIFY_REMOVE_SERVICE_URL: http://proxy:8080/v1/docker-flow-proxy/remove
    deploy:
      mode: replicated
      placement:
        constraints: [node.role == manager]
      replicas: 1
    logging:
      driver: "json-file"
      options:
        max-file: "5"
        max-size: "10m"
    depends_on:
      - proxy
networks:
  proxy:
    driver: overlay
  default:
    external: false
```

####### Initialize pg_trgm on the Postgresl db (<password is tandb (see my infrastructure.yml file)>)
```
psql -h localhost -d smartchoice -U tan

CREATE EXTENSION pg_trgm;
```

##### Build the my own services

###### Step 1. Clone my source code

```shell
git clone https://github.com/ndntan/smartchoice.git
```

###### Step 2. Go to ${HOME}/smartchoice
```shell
mvn clean install -DskipTests
```

###### Step 2. Go to ${HOME}/smartchoice. The below command will build all modules in parent folder
```shell
mvn clean install -DskipTests
```

###### Step 3 run product-processor. We need to wait a little bit for creating the queues on rabbitmq
```shell
cd ${HOME}/smartchoice/product-processor/
mvn spring-boot:run
```

###### Step 4 run tiki-supplier
```shell
cd ${HOME}/smartchoice/tiki-supplier/
mvn spring-boot:run
```

###### Step 4 run sendo-supplier
```shell
cd ${HOME}/smartchoice/sendo-supplier/
mvn spring-boot:run
```

## Usage

### Initialize category
Fist of all, we need to create our own category and give the system a product search text

```shell
curl -X POST \
  http://localhost:9399/rest/category/bulkCreate \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 8186e104-264a-8bba-c1ad-ec18d9523ce0' \
  -d '[
	{
		"name": "dien thoai iphone",
		"productPrimaryKeySearch": ["Iphone"]
	}
]'
```

##### Output
```text
[
    {
        "id": 1,
        "name": "dien thoai iphone",
        "createdTime": "2021-03-16T11:49:47+0000",
        "updatedTime": "2021-03-16T11:49:47+0000",
        "productPrimaryKeySearch": [
            "Iphone"
        ]
    }
]
```
When you got status code 200 of the above request. Let see how the Smartchoice works and enjoys!


### Search compareable products
```shell
curl -X GET \
  'http://localhost:9399/rest/products/search?search=iPhone' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: b4e3493f-ba4b-fa34-6b71-8973e16b85e0'
```

##### Output

```text
[
    {
        "id": 1,
        "name": "?i?n tho?i iphone 11 64GB - Hàng Chí Hãng",
        "icon": "https://media3.scdn.vn/img4/2021/03_16/WzgiazKxykJzqNCxANYd.jpg",
        "category": {
            "id": 1,
            "name": "dien thoai iphone",
            "createdTime": "2021-03-16T11:49:47+0000"
        },
        "createdTime": "2021-03-16T11:49:49+0000",
        "productDetails": [
            {
                "id": 1,
                "externalId": 41525235,
                "icon": "https://media3.scdn.vn/img4/2021/03_16/WzgiazKxykJzqNCxANYd.jpg",
                "price": 19990000,
                "productPath": "https://www.sendo.vn/dien-thoai-iphone-11-64gb-hang-chi-hang-41525235.html",
                "createdTime": "2021-03-16T11:49:49+0000",
                "supplier": "SENDO"
            },
            {
                "id": 4,
                "externalId": 32033717,
                "description": "\nThi?t k? ??m ch?t phá cách nh?ng c?ng không kém sang tr?ng\n?i?n Tho?i iPhone 11 là s?n ph?m k? nhi?m cho chi?c iPhone Xr t?ng dành ???c s? chú ý c?a gi?i công ngh?. L?n này, Apple v?n ?i theo nh?ng...",
                "icon": "https://salt.tikicdn.com/cache/280x280/ts/product/0c/62/39/31879ad1c9cf92b35e58749268ba4ff7.jpg",
                "price": 16490000,
                "discount": 6500000,
                "discountRate": 28,
                "productPath": "https://tiki.vn/dien-thoai-iphone-11-64gb-hang-chinh-hang-p32033717.html?src=ss-organic",
                "createdTime": "2021-03-16T11:49:49+0000",
                "supplier": "TIKI"
            }
        ]
    },
    {
        "id": 2,
        "name": "?i?n Tho?i iPhone 12 Mini - Hàng Chính Hãng",
        "icon": "https://media3.scdn.vn/img4/2021/03_09/asI33wkkkl6rxo4XqOv4.jpg",
        "category": {
            "id": 1,
            "name": "dien thoai iphone",
            "createdTime": "2021-03-16T11:49:47+0000"
        },
        "createdTime": "2021-03-16T11:49:49+0000",
        "productDetails": [
            {
                "id": 2,
                "externalId": 41364220,
                "icon": "https://media3.scdn.vn/img4/2021/03_09/asI33wkkkl6rxo4XqOv4.jpg",
                "price": 21990000,
                "productPath": "https://www.sendo.vn/dien-thoai-iphone-12-mini-hang-chinh-hang-41364220.html",
                "createdTime": "2021-03-16T11:49:49+0000",
                "supplier": "SENDO"
            },
            {
                "id": 7,
                "externalId": 71201117,
                "description": "Vi?n máy vát ph?ng cùng màn hình tai th? 5.4 inch\n?i?n tho?i iPhone 12 Mini là m?t trong nh?ng phiên b?n ?i?n tho?i siêu ph?m c?a Apple, ? dòng máy này vi?n máy không còn ???c thi?t k? bo cong các...",
                "icon": "https://salt.tikicdn.com/cache/280x280/ts/product/ea/2b/1a/aa25f64b1f028fc8d3b1fde44c5a3dd0.jpg",
                "price": 17690000,
                "discount": 4300000,
                "discountRate": 20,
                "productPath": "https://tiki.vn/dien-thoai-iphone-12-mini-64gb-hang-chinh-hang-p71201117.html?src=ss-organic",
                "createdTime": "2021-03-16T11:49:50+0000",
                "supplier": "TIKI"
            }
        ]
    }
]
```

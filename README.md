# Product assortment
## Steps to build

1. ```docker build -t product-assortment .```
2. ```docker run -d -p 5000:8090 product-assortment```

## Application description

### GET

```aidl
curl -X GET \
  http://localhost:5000/products \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache'
```

```
curl -X GET \
  http://localhost:5000/products/product_id \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache,no-cache''
 ```
 ### POST

```aidl
curl -X POST \
  http://localhost:5000/products \
  -H 'accept: application/json' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
  "name": "TestPHONE",
  "description": "A test product phone",
  "status":"OUT_OF_STOCK"
}'

```
### PUT

```aidl
curl -X PUT \
  http://localhost:5000/products/product_id \
  -H 'accept: application/json' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
  "name": "TestPHONE",
  "description": "A test product phone update",
  "status":"IN_STOCK"
}'
```

### DELETE

```aidl
curl -X DELETE \
  http://localhost:5000/products/product_id \
  -H 'accept: application/json' \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json'
```

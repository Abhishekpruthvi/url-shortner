# url-shortner

This application is hosted on the server

Swagger UI link 
http://geniuscollegemysore.in:8080/shortify/swagger-ui/index.html

## Curl requests
#### Post API to shorten the URL

  * Request
    
`curl --location --request POST 'https://geniuscollegemysore.in/shortify/api/shorten' \
--header 'Content-Type: application/json' \
--data '{
  "originalUrl": "https://g2risksolutions.com/",
  "customAlias": "g2rs"
}'`

* Response
  
`{
    "shortUrl": "https://geniuscollegemysore.in/shortify/api/g2rs",
    "originalUrl": "https://g2risksolutions.com/",
    "expiryAt": "2025-06-20T17:46:33.53725506"
}`



#### Get API to open the link

* Request
  
  `curl --location 'https://geniuscollegemysore.in/shortify/api/g2rs1'`

* Response
    
  It redirects to the original page if the URL is not expired.


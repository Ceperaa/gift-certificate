# Gift certificate

Develop web service for Gift Certificates system with the following entities (many-to-many)
## launch
- Put the collected Gift certificate.war in the webapps folder
- Start Tomcat
## Application
Available addresses for inquiries:

  - work with certificate
 > GiftCertificate:
     id,
     name,
      description,
      duration,
      price,
     createDate,
    lastUpdateDate
 - when creating, new tags are passed (cascade addition)
```sh
POST/api/v1/gift-certificates
```
- search by id
```sh
GET/api/v1/gift-certificates/{id}
```
- all list
```sh
GET/api/v1/gift-certificates?limit={pageSize}&offset={page}
```
 - only those fields that are passed in the request are updated
```sh
PUT/api/v1/gift-certificates/{id}
```
 - delete by id
```sh
DELETE/api/v1/gift-certificates/{id}
```
 - certificates with tags:
           search by part of name,
           sort by date, by name, or by date and name
```sh
GET/api/v1/gift-certificates/tag?limit={pageSize}&offset={page}&sorts={sort}&tagName={searchByName}
```
  - work with tags
  >Tag:
      id,
      name
 - creating a new tag
 ```sh
POST/api/v1/tags
```
- search by id
```sh
GET/api/v1/tags/{id}
```
- all list
```sh
GET/api/v1/tags?limit={pageSize}&offset={page}
```
 - only those fields that are passed in the request are updated
```sh
PUT/api/v1/tags/{id}
```
 - delete by id
```sh
DELETE/api/v1/tags/{id}
```  



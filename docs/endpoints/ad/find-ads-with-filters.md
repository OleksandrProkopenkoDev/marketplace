# Find Ads with Filters

Here we discuss GET requests to `/api/v1/ad` with
request params.

Available 3 groups of request params

- Page params
  `page=0&size=5&sort=price,desc`
- Ad's fields
  `title=New&authorId=3&description=fish&priceMin=100.0&priceMax=400&category=1`
- Ad's Attributes (should have a word 'attribute_' at the beginning)
  `attribute_age=2&attribute_size=Big&attribute_gender=Male&attribute_coat_length=Medium&attribute_color=Grey&attribute_health_condition=Healthy&attribute_pet_name=Rocky`

Here is the full list:

- title:New
- authorId:3
- description:fish
- priceMin:100.0
- priceMax:400
- category:1
- attribute_breed:Retriever
- attribute_age:2
- attribute_size:Big
- attribute_gender:Male
- attribute_coat_length:Medium
- attribute_color:Grey
- attribute_health_condition:Healthy
- attribute_pet_name:Rocky
- page:0
- size:5
- sort:price,desc

You may use any number of them at once, or use none of them.
`attribute_age` also is a string.
For now, it checks full equality of strings and it is case-sensitive.

example:
`http://localhost:8080/api/v1/ad?title=New&authorId=3&description=fish&priceMin=100.0&priceMax=400&category=1&attribute_breed=Retriever&attribute_age=2&attribute_size=Big&attribute_gender=Male&attribute_coat_length=Medium&attribute_color=Grey&attribute_health_condition=Healthy&attribute_pet_name=Rocky&page=0&size=5&sort=price,desc`
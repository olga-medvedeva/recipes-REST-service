# Backend project using Java + Spring Boot + PostgreSQL.

The program is a multi-user web service based on Spring Boot that allows storing, retrieving, updating, and deleting recipes with authentication.

A recipe includes 4 fields: name, category, date, description, ingredients, directions.

Service has following fuctionality:
- POST /api/recipe/new receives a recipe as a JSON object and returns a JSON object with one id field;
- GET /api/recipe/{id} returns a recipe with a specified id as a JSON object;
- DELETE /api/recipe/{id} endpoint. It deletes a recipe with a specified {id}. The server responds with the 204 (No Content) status code. 
If a recipe with a specified id does not exist, the server returns 404 (Not found). Only an author can delete his recipe;
- PUT /api/recipe/{id} receives a recipe as a JSON object and updates a recipe with a specified id. Also, update the date field too. 
The server returns the 204 (No Content) status code. If a recipe with a specified id does not exist, the server returns 404 (Not found). 
The server responds with 400 (Bad Request) if a recipe doesn't follow the restrictions indicated above (all fields are required, string fields 
can't be blank, arrays should have at least one item). Only an author can edit his recipe;
- GET /api/recipe/search takes one of the two mutually exclusive query parameters:
	- category – if this parameter is specified, it returns a JSON array of all recipes of the specified category. Search is case-insensitive, 
	sort the recipes by date (newer first);
	- name – if this parameter is specified, it returns a JSON array of all recipes with the names that contain the specified parameter. 
	Search is case-insensitive, sort the recipes by date (newer first).
- POST /api/register receives a JSON object with two fields: email (string), and password (string). If a user with a specified email does not exist, 
the program saves (registers) the user in a database and responds with 200 (Ok). If a user is already in the database, server responds with the 
400 (Bad Request) status code. Both fields are required and must be valid: email should contain @ and . symbols, password should contain 
at least 8 characters and shouldn't be blank. If the fields do not meet these restrictions, the service should respond with 400 (Bad Request).

## Examples
**Example 1: POST /api/recipe/new request without authentication**

{<br />
   "name": "Fresh Mint Tea",<br />
   "category": "beverage",<br />
   "description": "Light, aromatic and refreshing beverage, ...",<br />
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],<br />
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]<br />
}<br />
Status code: 401 (Unauthorized)

**Example 2: POST /api/register request without authentication**

{<br />
   "email": "Cook_Programmer@somewhere.com",<br />
   "password": "RecipeInBinary"<br />
}<br />
Status code: 200 (Ok)

Further POST /api/recipe/new request with basic authentication; email (login): Cook_Programmer@somewhere.com, and password: RecipeInBinary

{<br />
   "name": "Mint Tea",<br />
   "category": "beverage",<br />
   "description": "Light, aromatic and refreshing beverage, ...",<br />
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],<br />
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]<br />
}<br />
Response:

{<br />
   "id": 1<br />
}<br />
Further PUT /api/recipe/1 request with basic authentication; email (login): Cook_Programmer@somewhere.com, password: RecipeInBinary

{<br />
   "name": "Fresh Mint Tea",<br />
   "category": "beverage",<br />
   "description": "Light, aromatic and refreshing beverage, ...",<br />
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],<br />
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]<br />
}<br />
Status code: 204 (No Content)

Further GET /api/recipe/1 request with basic authentication; email (login): Cook_Programmer@somewhere.com, password: RecipeInBinary

Response:

{<br />
   "name": "Fresh Mint Tea",<br />
   "category": "beverage",<br />
   "date": "2020-01-02T12:11:25.034734",<br />
   "description": "Light, aromatic and refreshing beverage, ...",<br />
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],<br />
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]<br />
}<br />

**Example 3: POST /api/register request without authentication**

{<br />
   "email": "CamelCaseRecipe@somewhere.com",<br />
   "password": "C00k1es."<br />
}<br />

Status code: 200 (Ok)

Further response for the GET /api/recipe/1 request with basic authentication; email (login): CamelCaseRecipe@somewhere.com, password: C00k1es.

{<br />
   "name": "Fresh Mint Tea",<br />
   "category": "beverage",<br />
   "date": "2020-01-02T12:11:25.034734",<br />
   "description": "Light, aromatic and refreshing beverage, ...",<br />
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],<br />
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]<br />
}<br />

Further PUT /api/recipe/1 request with basic authentication; email (login): CamelCaseRecipe@somewhere.com, password: C00k1es.

{<br />
   "name": "Warming Ginger Tea",<br />
   "category": "beverage",<br />
   "description": "Ginger tea is a warming drink for cool weather, ...",<br />
   "ingredients": ["1 inch ginger root, minced", "1/2 lemon, juiced", "1/2 teaspoon manuka honey"],<br />
   "directions": ["Place all ingredients in a mug and fill with warm water (not too hot so you keep the beneficial honey compounds in tact)", "Steep for 5-10 minutes", "Drink and enjoy"]<br />
}<br />

Status code: 403 (Forbidden)

Further DELETE /api/recipe/1 request with basic authentication; email (login): CamelCaseRecipe@somewhere.com, password: C00k1es.

Status code: 403 (Forbidden)

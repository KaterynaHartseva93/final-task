USE `market`;

SELECT * FROM products;
SELECT * FROM users;


SELECT CONCAT (id_model, ',', name)
	FROM products; 
	
	SELECT id_user, email, role
	FROM users;

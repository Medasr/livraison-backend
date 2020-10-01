-- get articles by restaurateur

SELECT A.*
  FROM ARTICLES A
  INNER JOIN ( SELECT DISTINCT PC.id_article as id FROM PROD_CAT PC INNER JOIN CATEGORIES C WHERE C.id_restaurateur = ?1 ) C
    ON A.id = C.id
  WHERE A.titre LIKE ?2;

;

-- get articles by categorie
SELECT A.*
  FROM ARTICLES A
  INNER JOIN ( SELECT DISTINCT PC.id_article as id FROM PROD_CAT PC WHERE PC.id_categorie = ?1) C
    ON A.id = C.id

;

-- article belong to restaurateur
SELECT COUNT(C.id) > 0 FROM ( SELECT DISTINCT PC.id_article as id FROM PROD_CAT PC INNER JOIN CATEGORIES C WHERE C.id_restaurateur = ?1 LIMIT 1) C
;


SELECT A FROM Article a;
(SELECT PS.id FROM PRODUCT_SET PS INNER JOIN )
Groupe : Anthony LOROSCIO Benjamin PIAT

La classe Connection.java s'occupe de récupérer les tweet selon un mot clé spécifié. Puis stocke les tweet en .csv

La classe CSVtoTrans transforme le csv généré en .trans lisible par apriori. Puis serialise la hashmap utilisé afin de pouvoir
décoder de nouveau le fichier généré.
Il faut lancer l'algorithme apriori.

//Trans to csv permet de convertir le trans généré par apriori en csv à nouveau

Extraction_règle_assoc génère les règles à partir du fichier généré par apriori. Elle crée 2 fichier, un fichier qui contient les règles
sans Lift et un qui contient les règles avec lift. (Nous n'avons pas pu génerer des règles avec le lift, c'est pourquoi nous distinguons les 2
fichiers). Puis la fonction ssociationsRulesToWords() est censé transformer les règles générées en mots selon la hashmap sérialisée
(comme Trans to csv, sauf que ça ne fonctionne pas).

Enfin, vu que certaines fonctions plus haut ne fonctionnent pas, nous avons pas eu le temps de créer une interface graphique.


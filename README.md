Sovellus on toteutettu Kotlinilla ja hyödyntää Spring Boot -kehystä. Sen avulla käyttäjät voivat tehdä ostoja ja kerätä voittoja pelitililleen. Sovellus käyttää in-memory lähestymistapaa tietokantoihin.
Sovellukseen on tehty 6 unit testiä testaamaan kriittisimmät toiminnot. Koodin kommenteista voi saada lisätietoa niistä.

Ohjelman ajaminen:
1. Ohjelma tulee buildata käyttämällä maven työkalua. Navigoi veikkaus-demo kansioon ja aja komento "mvn package"
   Tämä ajaa myös testit, jotka tulisi mennä läpi
2. Seuraavaksi navigoi veikkaus-demo/target kansioon, jossa sijaitsee buildattu jar file. Tämä tulee ajaa komennolla "java -jar demo-0.0.1-SNAPSHOT.jar"
3. Ohjelman tulisi käynnistyä ja se on nähtävissä selaimessa osoitteessa https://localhost:8086/

Ohjelman käyttäminen:
1. Ohjelman ajattaessa se luo automaattisesti 3 pelaajaa. Nämä ovat nähtävissä osoitteessa "https://localhost:8086/all"
2. Ohjelma myöskin sisältää endpointin käyttäjien luomiseen. Tämä onnistuu esim. ajamalla curl-komento "curl -X POST http://localhost:8086/create -H "Content-Type: application/json" -d '{"name": "John", "balance": 100}'"
   Tässä siis annetaan käyttäjän nimi ja saldo parametreina
4. Ohjelma myöskin sisältää endpointin osto tai voitto -tapahtumien luomiseen. Osto tapahtumat tehdään osoitteeseen "http://localhost:8085/buy" kun taas voitot osoitteeseen "http://localhost:8085/win"
 Tämä onnistuu esim. ajamalla komento  "curl -X POST http://localhost:8085/buy -H "Content-Type: application/json" -d '{"userIDRef": "06552f48-8a9e-4fac-bcc5-b16ca3060fb6", "sum": 50}'"
 Tässä annetaan käyttäjä ID ja summa joka joko vähennetään tai lisätään käyttäjän saldosta. Tämä palauttaa käyttäjän jäljellä olevan saldon.
5. Käyttäjä ID tulostuu, kun käyttäjä ensi kertaa luodaan. Ne voi myöskin hakea menemällä osoitteeseen "https://localhost:8086/all"

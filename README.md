# DataExtractorApp

This is a MicroService that fetches data from the Italian Goverment Lombardia region API with regards of the ammount of people vaccinated in each Comune.

API LINK: https://hub.dati.lombardia.it/resource/vtzi-zmp8.json

It uses this second API to match the Comune Province with the Province Sigla: https://comuni-ita.herokuapp.com/api/province

One the data has been fetched it is sent to a Kafka Topic to later be consumed.

To fetch the data and send it via Kafka you need to issue a GET request to the path /requestSendRecords.

It works in conjunction with this other repository that recieves the data from the Topic and provides fetching methods as well as store the data in a Mongo DB: https://github.com/HermanGareis/DataAggregatorApp

You can find the fronted for this app here: https://github.com/HermanGareis/ComuneDoseTracker

# ensemblrest
ensemblrest is a proof of concept project for demonstrating the use of the RxJava API to increase concurreny for time consuming operations.
It's primary function is to invoke ensembl's Variant Effect Predictor RESTful Web service to annotate
genomic variations expressed in HGVS format. Prior to making a Web service request, a local mySQL database is querued
to see if the same genomic variation has already been annotated and its results cached in the database. If not, the ensembl service
is invoked. When the annotation results are received from ensembl they are simultaneously returned to the user and inserted into the databsase.
The VEP annotation results persisted in the same JSON format provided by th ensembl RESTful service.

An initial requirement for this project was that it be run as an independent microservice. Thus it also provides its own RESTful
service that allow clients to request access to the persisted annotation cache and ensembl VEP annotation services. For this RESTful
service to be abailable. The user needs to invoke the VepServiceApplication class.

This application was developed in 2015 using Java 7 and and early version of RxJava. Therefore it does not incorporate Java 8 capabilities or the latest features of RxJava

Fred Criscuolo (sdrc1888@gmail.com)

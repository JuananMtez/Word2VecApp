## About the project

This application implements a distributed system based on the Word2Vec tool, an advanced natural language processing technique developed in 2013. Using a neural network model, Word2Vec learns word associations from a large corpus of text, providing significant capabilities in natural language analysis and understanding. 

## Overall architecture

![Arquitectura](https://github.com/JuananMtez/Word2VecApp/assets/86200289/3d8307f5-bfbc-49ff-a412-14c129c64cf2)



## Backend (Frontend support)
This service, as its name suggests, is responsible for servicing the front-end. All the requests that the front-end makes, except for the request to obtain the result of a training session, are received by this service. The requests addressed to the Word2VecTrain backend are also received by this service for greater ease, as a gRPC client has been integrated in this backend, which communicates with a gRPC server (Backend Word2VecTrain).


## Backend (Word2VecTrain)

This services is responsible of training files.


## Backend (Word2VecUse)

This external REST server will perform the prediction tasks using a model previously trained by the internal gRPC service seen in previous sections.



## Author

* **Juan Antonio Martínez López** - [Website](https://juananmtez.github.io/) - [LinkedIn](https://www.linkedin.com/in/juanantonio-martinez/)




## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

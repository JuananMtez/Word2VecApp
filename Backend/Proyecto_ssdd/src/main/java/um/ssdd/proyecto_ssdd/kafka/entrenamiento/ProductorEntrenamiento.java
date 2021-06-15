package um.ssdd.proyecto_ssdd.kafka.entrenamiento;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import um.ssdd.proyecto_ssdd.grpc.entrenamiento.CrearEntrenamientoRequest;

public class ProductorEntrenamiento {
	
	private final String topico;
	private final Producer<String, String> productor;
	
	public ProductorEntrenamiento( String topico ) {
		this.topico = topico;
		
		Properties props = new Properties();
		
		props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        
        productor = new KafkaProducer<>(props);
	}
	
	public void EnviarMensaje(CrearEntrenamientoRequest mensaje) {
		try {
			productor.send(new ProducerRecord<String, String>(topico, mensaje.getEntrenamiento().getId()));
			System.out.println("Enviado: " + mensaje.toString());
		} catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	
	public void Finalizar() {
		productor.close();
	}

}

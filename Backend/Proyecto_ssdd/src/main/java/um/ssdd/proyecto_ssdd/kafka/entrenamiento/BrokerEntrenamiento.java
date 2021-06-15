package um.ssdd.proyecto_ssdd.kafka.entrenamiento;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;

public class BrokerEntrenamiento {
	
    public static void main(String[] args)
    {
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("session.timeout.ms", "30000");

        String topic = "w2vec";
        int partitions = 2;
        short replicationFactor = 1;

        AdminClient adminClient = AdminClient.create(props);
        NewTopic newTopic = new NewTopic(topic, partitions, replicationFactor);

        adminClient.createTopics(Arrays.asList(newTopic));
        adminClient.close();
    }

}

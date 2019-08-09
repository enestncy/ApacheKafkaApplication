package com.enestuncay.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Properties;


public class Consumer {

    KafkaConsumer<String, String> kafkaConsumer;

    private String topicName;

    public static int[] logPerCity = new int[5];

    public static String line="";

    public static boolean flag = false;

    public Consumer(String topicName){
        this.topicName = topicName;
    }

    private static HttpURLConnection con;

    private Object lock = new Object();




    public static Properties createConsumerConfig() {
        Properties configProperties = new Properties();
        configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "buyukveriGroup");
        configProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, "client_id");
        return configProperties;
    }

    public void run(){

        kafkaConsumer = new KafkaConsumer<String, String>(createConsumerConfig());
        kafkaConsumer.subscribe(Collections.singletonList(topicName));
        //kafkaConsumer.subscribe(Arrays.asList(topicName));

        try {
            while (true) {

                ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {


                    synchronized (lock)
                    {

                    line = record.value();
                    flag = true;

                    String[] parsedline = record.value().split(" ");

                    switch (parsedline[3]) {
                        case "Istanbul":
                            logPerCity[0]++;
                            break;
                        case "London":
                            logPerCity[1]++;
                            break;
                        case "Tokyo":
                            logPerCity[2]++;
                            break;
                        case "Moscow":
                            logPerCity[3]++;
                            break;
                        case "Beijing":
                            logPerCity[4]++;
                            break;
                    }
                }


                        try {
                            senPOST();
                        } catch (MalformedURLException m) {
                            m.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
        } catch (WakeupException ex) {
            System.out.println("Exception caught " + ex.getMessage());
        } finally {
            kafkaConsumer.close();
            System.out.println("Close KafkaConsumer");
        }
    }

    public static void senPOST() throws MalformedURLException, ProtocolException, IOException {

        String url = "http://localhost:8080/saveLogs";
        String urlParameters = "name=Jack&occupation=programmer";
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Java client");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }catch (FileNotFoundException ex){
                System.out.println("");
            }

        }finally {
            con.disconnect();
        }
    }

}

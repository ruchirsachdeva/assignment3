package com.lnu.foundation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by rucsac on 06/11/2018.
 */
@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Ping Heroku apps every 20 minutes to stop them from sleeping every 30 minutes.
     * @throws IOException
     */
    @Scheduled(fixedRate = 1200000)
    public void pingHerokuApps() throws IOException {
        logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        ping("https://pd-social-server.herokuapp.com");
        ping("https://lit-beach-29911.herokuapp.com");
    }

    private void ping(String urlString) throws MalformedURLException {
        URL url = new URL(urlString);
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.getResponseCode();
        } catch (IOException e) {
            logger.error("Scheduling failed for server: " + urlString, e);
        }
        finally {
        con.disconnect();
        }
    }

}

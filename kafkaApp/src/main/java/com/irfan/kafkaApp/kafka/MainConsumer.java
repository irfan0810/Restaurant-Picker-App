package com.irfan.kafkaApp.kafka;

import com.irfan.kafkaApp.dao.UserRepository;
import com.irfan.kafkaApp.entity.User;
import jakarta.transaction.Transactional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MainConsumer {

    private final UserRepository userRepository;

    public MainConsumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Consume a CSV payload with header:
     * first_name,last_name,username,password,email
     */
    @KafkaListener(topics = "${app.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public void consumeCsvUsers(String csvPayload) throws Exception {
        if (csvPayload == null || csvPayload.isBlank()) return;

        Reader in = new StringReader(csvPayload);

        // Parse with header; trim cells; skip the header row
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                .setHeader()             // read header from first row
                .setSkipHeaderRecord(true)
                .setTrim(true)
                .build()
                .parse(in);

        List<User> toSave = new ArrayList<>();

        for (CSVRecord rec : records) {
            String firstName = rec.get("first_name");
            String lastName  = rec.get("last_name");
            String username  = rec.get("username");
            String password  = rec.get("password");  // already bcrypt hash per your sample
            String email     = rec.get("email");

            // Basic validation
            if (isBlank(username) || isBlank(password) || isBlank(email)) {
                continue;
            }

            User user = userRepository.findUserByUsername(username);

            // If new user
            if (user.getId() == null) {
                user.setJoinDate(new Date());
                user.setActive(true);
                user.setNotLocked(true);
                user.setRole("ROLE_USER");
            }

            // set the user fields
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUsername(username);
            // already hashed
            user.setPassword(password);
            user.setEmail(email);
            toSave.add(user);
        }

        if (!toSave.isEmpty()) {
            try {
                userRepository.saveAll(toSave);
            } catch (DataIntegrityViolationException ex) {
                throw ex;
            }
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}

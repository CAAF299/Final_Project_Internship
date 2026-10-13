package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@RestController
@RequestMapping("api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EntryController {

    private final DynamoDbTable<UserObject> dataTable;
    private final AtomicInteger idGenerator = new AtomicInteger(1);
    private static final Logger log = LoggerFactory.getLogger(EntryController.class);

    
    @Value("${server.port:8080}")
    int port;

    
    
    public EntryController(DynamoDbEnhancedClient enhancedClient) {
        this.dataTable = enhancedClient.table("dataTable", TableSchema.fromBean(UserObject.class));
    }

    
    
    @GetMapping("/info")
    public ResponseEntity<String> greet() {
        return ResponseEntity.status(HttpStatus.OK).body("Running on port " + port);
    } 

    
    
   @PostMapping
    public ResponseEntity<UserObject> makeUser(@Valid @RequestBody UserObject postUser) {
    int id = (int) (System.currentTimeMillis() & 0xfffffff);
    postUser.setId(id);
    postUser.setAdult(postUser.getAge() >= 18);

    dataTable.putItem(postUser);
    return ResponseEntity.status(HttpStatus.CREATED).body(postUser);
}
    
    
    @PutMapping("/{id}")
    public ResponseEntity<UserObject> editUser(@Valid @RequestBody UserObject putUser, @PathVariable Integer id) {
        Key key = Key.builder().partitionValue(id).build();
        UserObject existingUser = dataTable.getItem(key);

        if (existingUser == null) {
            log.warn("User not found of id {} ", id);
            return ResponseEntity.notFound().build();
        }

        putUser.setAdult(putUser.getAge() >= 18);
        putUser.setId(id);

        dataTable.putItem(putUser);

        log.info("Resource succesfully modified of the id {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(putUser);
    }

    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        Key key = Key.builder().partitionValue(id).build();
        UserObject existingUser = dataTable.getItem(key);

        if (existingUser == null) {
            log.warn("User not found of id {}", id);
            return ResponseEntity.notFound().build();
        }

        dataTable.deleteItem(key);

        log.info("Resource succesfully deleted of id {}", id);
        return ResponseEntity.noContent().build();
    }

    
    
    @GetMapping("/{id}")
    public ResponseEntity<UserObject> fetchUser(@PathVariable Integer id) {
        Key key = Key.builder().partitionValue(id).build();
        UserObject user = dataTable.getItem(key);

        if (user == null) {
            log.warn("User not found of id {}", id);
            return ResponseEntity.notFound().build();
        }

        log.info("Succesfully retrieved user of id {}", id);
        return ResponseEntity.ok(user);
    }

    
    
    @GetMapping
    public ResponseEntity<List<UserObject>> getAllUsers() {
        List<UserObject> users = dataTable.scan().items().stream().collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
}
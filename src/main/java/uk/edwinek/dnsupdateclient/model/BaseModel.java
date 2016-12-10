package uk.edwinek.dnsupdateclient.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

abstract class BaseModel {

    private ObjectMapper mapper = new ObjectMapper();

    BaseModel() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public String toString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("There was an error marshalling the object: " + e.getMessage());
        }
    }

}

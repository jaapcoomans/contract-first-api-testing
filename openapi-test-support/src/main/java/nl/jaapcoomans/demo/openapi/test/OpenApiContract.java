package nl.jaapcoomans.demo.openapi.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class OpenApiContract {
    private static final String DEFAULT_MEDIA_TYPE = "application/json";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final JsonNode contractJson;

    private OpenApiContract(JsonNode contractJson) {
        this.contractJson = contractJson;
    }

    public static OpenApiContract fromClasspath(String path) {
        try (var inputStream = OpenApiContract.class.getClassLoader().getResourceAsStream(path)) {
            String rawContract = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            var contractJson = objectMapper.readTree(rawContract);

            return new OpenApiContract(contractJson);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public OpenApiAssert assertThat(String json) {
        return new OpenApiAssert(this, json);
    }

    public String getRequestSchema(String operationId) {
        var operation = getOperation(operationId);
        var requestBody = getRequestBody(operation);
        var schema = requestBody.get("schema");
        return schema.toString();
    }

    private JsonNode getRequestBody(JsonNode operation) {
        return operation.get("requestBody")
                .get("content")
                .get(DEFAULT_MEDIA_TYPE);
    }

    public String getResponseSchema(String operationId, int statusCode) {
        var operation = getOperation(operationId);
        var responseBody = getResponseBody(operation, statusCode);
        var schema = responseBody.get("schema");
        
        var resolved = resolveRefs(schema);
        
        return resolved.toString();
    }

    public JsonNode resolveRefs(JsonNode node) {
        if (node.isObject()) {
            if (node.has("$ref")) {
                String ref = node.get("$ref").asText();
                JsonNode resolvedNode = resolveRef(ref);
                if (resolvedNode != null) {
                    return resolveRefs(resolvedNode);
                }
            } else {
                ObjectNode result = objectMapper.createObjectNode();
                node.fields().forEachRemaining(entry ->
                        result.set(entry.getKey(), resolveRefs(entry.getValue())));
                return result;
            }
        } else if (node.isArray()) {
            for (int i = 0; i < node.size(); i++) {
                ((ArrayNode) node).set(i, resolveRefs(node.get(i)));
            }
        }
        return node;
    }

    private JsonNode resolveRef(String ref) {
        if (ref.startsWith("#/components/")) {
            String[] path = ref.substring(2).split("/");
            JsonNode current = contractJson;
            for (String key : path) {
                if (current.has(key)) {
                    current = current.get(key);
                } else {
                    return null;
                }
            }
            return current;
        }
        return null;
    }

    public JsonNode getResponseBody(JsonNode operation, int statusCode) {
        return operation.get("responses").get(String.valueOf(statusCode))
                .get("content")
                .get(DEFAULT_MEDIA_TYPE);
    }

    private JsonNode getOperation(String operationId) {
        var paths = contractJson.get("paths");

        return StreamSupport.stream(paths.spliterator(), false)
                .flatMap(this::getOperations)
                .filter(operation -> operation.has("operationId"))
                .filter(operation -> operation.get("operationId").textValue().equals(operationId))
                .findFirst()
                .orElseThrow();
    }

    private Stream<JsonNode> getOperations(JsonNode pathItem) {
        return Stream.of(
                pathItem.get("get"),
                pathItem.get("post"),
                pathItem.get("put"),
                pathItem.get("delete"),
                pathItem.get("patch")
        ).filter(Objects::nonNull);
    }
}

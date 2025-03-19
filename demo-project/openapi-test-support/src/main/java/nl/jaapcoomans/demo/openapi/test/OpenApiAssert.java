package nl.jaapcoomans.demo.openapi.test;

import com.networknt.schema.InputFormat;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.util.Set;

public class OpenApiAssert {
    private static final JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);

    private final OpenApiContract contract;
    private final String json;

    OpenApiAssert(OpenApiContract contract, String json) {
        this.contract = contract;
        this.json = json;
    }

    public void isValidRequestFor(String operationId) {
        var requestSchema = contract.getRequestSchema(operationId);

        var schema = jsonSchemaFactory.getSchema(requestSchema);
        schema.validate(json, InputFormat.JSON);
    }

    public void isValidResponseFor(String operationId, int statusCode) {
        var responseSchema = contract.getResponseSchema(operationId, statusCode);

        var schema = jsonSchemaFactory.getSchema(responseSchema);
        var validationMessages = schema.validate(json, InputFormat.JSON);

        if (!validationMessages.isEmpty()) {
            throw errorForResponse(validationMessages, operationId, statusCode);
        }
    }

    private static AssertionError errorForResponse(Set<ValidationMessage> validationMessages, String operationId, int statusCode) {
        var details = new StringBuilder();
        details.append("\nNot a valid JSON response body for operation ")
                .append(operationId)
                .append(" with status code ")
                .append(statusCode)
                .append(".\n");

        for (ValidationMessage validationMessage : validationMessages) {
            details.append("- ")
                    .append(validationMessage.getMessage())
                    .append("\n");
        }
        
        return new AssertionError(details.toString());
    }
}

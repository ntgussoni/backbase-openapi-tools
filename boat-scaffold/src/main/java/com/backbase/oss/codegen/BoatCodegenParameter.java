package com.backbase.oss.codegen;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openapitools.codegen.CodegenParameter;

@Slf4j
@ToString(callSuper = true)
public class BoatCodegenParameter extends CodegenParameter {

    private List<BoatExample> examples;
    private String dataTypeDisplayName;

    public boolean hasDefaultValue() {
        return StringUtils.isNotEmpty(defaultValue);
    }

    public boolean hasSingleExample() {
        return example != null;
    }

    public boolean hasExamples() {
        return examples != null && !examples.isEmpty();
    }

    public BoatCodegenParameter() {
        super();
    }


    static BoatCodegenParameter fromCodegenParameter(CodegenParameter codegenParameter) {

        // Standard properties
        BoatCodegenParameter output = new BoatCodegenParameter();
        output.isFile = codegenParameter.isFile;
        output.hasMore = codegenParameter.hasMore;
        output.isContainer = codegenParameter.isContainer;
        output.secondaryParam = codegenParameter.secondaryParam;
        output.baseName = codegenParameter.baseName;
        output.paramName = codegenParameter.paramName;
        output.dataType = codegenParameter.dataType;
        output.datatypeWithEnum = codegenParameter.datatypeWithEnum;
        output.enumName = codegenParameter.enumName;
        output.dataFormat = codegenParameter.dataFormat;
        output.collectionFormat = codegenParameter.collectionFormat;
        output.isCollectionFormatMulti = codegenParameter.isCollectionFormatMulti;
        output.isPrimitiveType = codegenParameter.isPrimitiveType;
        output.isModel = codegenParameter.isModel;
        output.description = codegenParameter.description;
        output.unescapedDescription = codegenParameter.unescapedDescription;
        output.baseType = codegenParameter.baseType;
        output.isFormParam = codegenParameter.isFormParam;
        output.isQueryParam = codegenParameter.isQueryParam;
        output.isPathParam = codegenParameter.isPathParam;
        output.isHeaderParam = codegenParameter.isHeaderParam;
        output.isCookieParam = codegenParameter.isCookieParam;
        output.isBodyParam = codegenParameter.isBodyParam;
        output.required = codegenParameter.required;
        output.maximum = codegenParameter.maximum;
        output.exclusiveMaximum = codegenParameter.exclusiveMaximum;
        output.minimum = codegenParameter.minimum;
        output.exclusiveMinimum = codegenParameter.exclusiveMinimum;
        output.maxLength = codegenParameter.maxLength;
        output.minLength = codegenParameter.minLength;
        output.pattern = codegenParameter.pattern;
        output.maxItems = codegenParameter.maxItems;
        output.minItems = codegenParameter.minItems;
        output.uniqueItems = codegenParameter.uniqueItems;
        output.multipleOf = codegenParameter.multipleOf;
        output.jsonSchema = codegenParameter.jsonSchema;
        output.defaultValue = "null".equals(codegenParameter.defaultValue) ? null : codegenParameter.defaultValue;
        output.example = codegenParameter.example;
        output.isEnum = codegenParameter.isEnum;
        output.setMaxProperties(codegenParameter.getMaxProperties());
        output.setMinProperties(codegenParameter.getMinProperties());
        output.maximum = codegenParameter.maximum;
        output.minimum = codegenParameter.minimum;
        output.pattern = codegenParameter.pattern;

        if (codegenParameter._enum != null) {
            output._enum = new ArrayList<>(codegenParameter._enum);
        }
        if (codegenParameter.allowableValues != null) {
            output.allowableValues = new HashMap<>(codegenParameter.allowableValues);
        }
        if (codegenParameter.items != null) {
            output.items = codegenParameter.items;
        }
        if (codegenParameter.mostInnerItems != null) {
            output.mostInnerItems = codegenParameter.mostInnerItems;
        }
        if (codegenParameter.vendorExtensions != null) {
            output.vendorExtensions = new HashMap<>(codegenParameter.vendorExtensions);
        }
        output.hasValidation = codegenParameter.hasValidation;
        output.isNullable = codegenParameter.isNullable;
        output.isBinary = codegenParameter.isBinary;
        output.isByteArray = codegenParameter.isByteArray;
        output.isString = codegenParameter.isString;
        output.isNumeric = codegenParameter.isNumeric;
        output.isInteger = codegenParameter.isInteger;
        output.isLong = codegenParameter.isLong;
        output.isDouble = codegenParameter.isDouble;
        output.isFloat = codegenParameter.isFloat;
        output.isNumber = codegenParameter.isNumber;
        output.isBoolean = codegenParameter.isBoolean;
        output.isDate = codegenParameter.isDate;
        output.isDateTime = codegenParameter.isDateTime;
        output.isUuid = codegenParameter.isUuid;
        output.isUri = codegenParameter.isUri;
        output.isEmail = codegenParameter.isEmail;
        output.isFreeFormObject = codegenParameter.isFreeFormObject;
        output.isAnyType = codegenParameter.isAnyType;
        output.isListContainer = codegenParameter.isListContainer;
        output.isMapContainer = codegenParameter.isMapContainer;
        output.isExplode = codegenParameter.isExplode;
        output.style = codegenParameter.style;

        if (codegenParameter instanceof BoatCodegenParameter) {
            output.examples = ((BoatCodegenParameter) codegenParameter).examples;
            output.dataTypeDisplayName = ((BoatCodegenParameter) codegenParameter).dataTypeDisplayName;
        } else {
            if (output.dataType != null && output.dataType.startsWith("array")) {
                output.dataTypeDisplayName = "array of " + output.baseType.toLowerCase() + "s";
            } else {
                output.dataTypeDisplayName = output.dataType;
            }
        }

        return output;
    }


    @Override
    public CodegenParameter copy() {
        return fromCodegenParameter(this);
    }

    static BoatCodegenParameter fromCodegenParameter(Parameter parameter, CodegenParameter codegenParameter, OpenAPI openAPI) {
        BoatCodegenParameter boatCodegenParameter = fromCodegenParameter(codegenParameter);
        // Copy Parameter Examples if applicable
        if (parameter.getExamples() != null) {
            boatCodegenParameter.examples = parameter.getExamples().entrySet().stream()
                .map(stringExampleEntry -> new BoatExample(stringExampleEntry.getKey(), null, stringExampleEntry.getValue()))
                .collect(Collectors.toList());
        }

        if (parameter.getContent() != null) {
            parameter.getContent().forEach((contentType, mediaType) ->
                dereferenceExamples(boatCodegenParameter, openAPI, contentType, mediaType));

        }
        return boatCodegenParameter;
    }

    //
    public static CodegenParameter fromCodegenParameter(CodegenParameter codegenParameter, RequestBody body, OpenAPI openAPI) {
        BoatCodegenParameter boatCodegenParameter = fromCodegenParameter(codegenParameter);
        body.getContent().forEach((contentType, mediaType) ->
            dereferenceExamples(boatCodegenParameter, openAPI, contentType, mediaType));
        return boatCodegenParameter;
    }

    private static void dereferenceExamples(BoatCodegenParameter boatCodegenParameter, OpenAPI openAPI, String contentType, MediaType mediaType) {
        if (boatCodegenParameter.examples == null) {
            boatCodegenParameter.examples = new ArrayList<>();
        }
        List<BoatExample> examples = new ArrayList<>();

        if (mediaType.getExample() != null) {
            Object example = mediaType.getExample();
            BoatExample boatExample = new BoatExample("example", contentType, new Example().value(example));
            if (example instanceof ObjectNode && ((ObjectNode) example).has("$ref")) {
                boatExample.getExample().set$ref(((ObjectNode) example).get("$ref").asText());
            }
            examples.add(boatExample);
        }
        if (mediaType.getExamples() != null) {
            mediaType.getExamples().forEach((key, example) -> {
                BoatExample boatExample = new BoatExample(key, contentType, example);
                examples.add(boatExample);
            });
        }
        // dereference examples
        examples.stream().filter(boatExample -> boatExample.getExample().get$ref() != null)
            .forEach(boatExample -> {
                String ref = StringUtils.substringAfterLast(boatExample.getExample().get$ref(), "/");
                Example example = openAPI.getComponents().getExamples().get(ref);
                if (example == null) {
                    log.warn("Example ref: {} refers to an example that does not exist", ref);
                } else {
                    log.debug("Replacing Example ref: {} with example from components: {}", ref, example);
                    boatExample.setExample(example);
                }
            });
        boatCodegenParameter.examples.addAll(examples);
    }

    public List<BoatExample> getExamples() {
        return examples;
    }

    public void setExamples(List<BoatExample> examples) {
        this.examples = examples;
    }

    public String getDataTypeDisplayName() {
        return dataTypeDisplayName;
    }

    public void setDataTypeDisplayName(String dataTypeDisplayName) {
        this.dataTypeDisplayName = dataTypeDisplayName;
    }
}
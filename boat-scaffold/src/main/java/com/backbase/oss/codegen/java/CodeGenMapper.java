package com.backbase.oss.codegen.java;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.openapitools.codegen.CodegenOperation;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CodeGenMapper {

    CodeGenOperationEx extendCodeGenOperation(CodegenOperation source, int beanParamTrigger);
}

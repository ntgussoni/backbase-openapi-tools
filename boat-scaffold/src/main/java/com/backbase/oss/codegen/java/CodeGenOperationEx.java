package com.backbase.oss.codegen.java;

import org.openapitools.codegen.CodegenOperation;

public class CodeGenOperationEx extends CodegenOperation {
    public int beanParamTrigger;

    public boolean isUseBeanParam() {
        return beanParamTrigger > 0 && allParams.size() >= beanParamTrigger;
    }
}

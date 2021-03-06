# boat maven plugin.

The `boat` plugin has multiple goals:


## boat:export

Generates client/server code from a OpenAPI json/yaml definition. Finds files name `api.raml`, `client-api.raml` or `service-api.raml`.
Processes these files (and the json schemes they refer to) to produce `open-api.yaml` files in the output directory.

## boat:export-bom

Converts all RAML spec dependencies to OpenAPI Specs. See integration tests for examples

## boat:export-dep

Exports project dependencies where the ArtifactId ends with.  See integration tests for examples '-spec'.

## boat:generate

Open API Generator based on https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator-maven-plugin. All configuration options as 
defined on openapi-generator-maven-plugin can be applied here too. 

Boat maven plugin uses slightly modified templates for html, java and webclient that help generate specs and clients that work best in a Backbase projects.

## boat:generate-spring-boot-embedded

Same with `generate` but with opinionated defaults for Spring

    <configuration>
        <inputSpec>${project.basedir}/../api/product-service-api/src/main/resources/openapi.yaml</inputSpec>
        <apiPackage>com.backbase.product.api.service.v2</apiPackage>
        <modelPackage>com.backbase.product.api.service.v2.model</modelPackage>
    </configuration>

... is the same as:

    <configuration>
        <output>${project.build.directory}/generated-sources/openapi</output>
        <generateSupportingFiles>true</generateSupportingFiles>
        <generatorName>spring-boat</generatorName>
        <strictSpec>true</strictSpec>
        <generateApiTests>false</generateApiTests>
        <generateModelTests>false</generateModelTests>
        <inputSpec>${project.basedir}/../api/product-service-api/src/main/resources/openapi.yaml</inputSpec>
        <configOptions>
            <library>spring-boot</library>
            <dateLibrary>java8</dateLibrary>
            <interfaceOnly>true</interfaceOnly>
            <skipDefaultInterface>true</skipDefaultInterface>
            <useBeanValidation>true</useBeanValidation>
            <useClassLevelBeanValidation>false</useClassLevelBeanValidation>
            <useTags>true</useTags>
            <java8>true</java8>
            <useOptional>false</useOptional>
            <apiPackage>com.backbase.product.api.service.v2</apiPackage>
            <modelPackage>com.backbase.product.api.service.v2.model</modelPackage>
        </configOptions>
    </configuration>

## boat:generate-rest-template-embedded

Same with `generate` but with opinionated defaults for Rest Template Client

    <configuration>
        <output>${project.build.directory}/generated-sources/openapi</output>
        <generateSupportingFiles>true</generateSupportingFiles>
        <generatorName>java</generatorName>
        <strictSpec>true</strictSpec>
        <generateApiTests>false</generateApiTests>
        <generateModelTests>false</generateModelTests>
        <inputSpec>${project.basedir}/../api/product-service-api/src/main/resources/openapi.yaml</inputSpec>
        <configOptions>
            <library>resttemplate</library>
            <dateLibrary>java8</dateLibrary>
            <interfaceOnly>true</interfaceOnly>
            <skipDefaultInterface>true</skipDefaultInterface>
            <useBeanValidation>true</useBeanValidation>
            <useClassLevelBeanValidation>false</useClassLevelBeanValidation>
            <useTags>true</useTags>
            <java8>true</java8>
            <useOptional>false</useOptional>
            <apiPackage>com.backbase.goldensample.product.api.client.v2</apiPackage>
            <modelPackage>com.backbase.goldensample.product.api.client.v2.model</modelPackage>
        </configOptions>
    </configuration>

## boat:generate-webclient-embedded

Same with `generate` but with opinionated defaults for Web Client

    <configuration>
        <output>${project.build.directory}/generated-sources/openapi</output>
        <generateSupportingFiles>true</generateSupportingFiles>
        <generatorName>java</generatorName>
        <strictSpec>true</strictSpec>
        <generateApiTests>false</generateApiTests>
        <generateModelTests>false</generateModelTests>
        <inputSpec>${project.basedir}/../api/product-service-api/src/main/resources/openapi.yaml</inputSpec>
        <configOptions>
            <library>webclient</library>
            <dateLibrary>java8</dateLibrary>
            <interfaceOnly>true</interfaceOnly>
            <skipDefaultInterface>true</skipDefaultInterface>
            <useBeanValidation>true</useBeanValidation>
            <useClassLevelBeanValidation>false</useClassLevelBeanValidation>
            <useTags>true</useTags>
            <java8>true</java8>
            <useOptional>false</useOptional>
            <apiPackage>com.backbase.goldensample.product.api.client.v2</apiPackage>
            <modelPackage>com.backbase.goldensample.product.api.client.v2.model</modelPackage>
        </configOptions>
    </configuration>

## boat:decompose

Merges any components using allOf references.

## boat:diff

Calculates a Change log for APIs.

## boat:remove-deprecated

Removes deprecated elements in an OpenAPI spec.

## boat:validate

Validates OpenAPI specs.

Configuration can point to a specific file, or a directory. When a directory is specified all files with a `.yaml` 
extension are validated. `failOnWarning` specifies whether to fail the build when validation violations are found,
otherwise, warnings are written to the log for everyone to ignore.

    <configuration>
        <input>${project.build.outputDirectory}/specs/</input>
        <failOnWarning>true</failOnWarning>
    </configuration>

## boat:lint

API lint which provides checks for compliance with many of Backbase's API standards

Available parameters:

    failOnWarning (Default: false)
        Set this to true to fail in case a warning is found.

    ignoreRules
        List of rules ids which will be ignored.

    inputSpec
        Required: true
        Input spec directory or file.

    output (Default: ${project.build.directory}/boat-lint-reports)
        Output directory for lint reports.

    showIgnoredRules (Default: false)
        Set this to true to show the list of ignored rules..
   
    writeLintReport (Default: true)
        Set this to true to generate lint report.

Example:

    <configuration>
        <inputSpec>${unversioned-filename-spec-dir}/</inputSpec>
        <output>${project.build.directory}/boat-lint-reports</output>
        <writeLintReport>true</writeLintReport>
        <ignoreRules>${ignored-lint-rules}</ignoreRules>
        <showIgnoredRules>true</showIgnoredRules>
    </configuration>

To see details about this goal:

    mvn help:describe -DgroupId=com.backbase.oss -DartifactId=boat-maven-plugin  -Dgoal=lint -Ddetail`
  
  
## boat:bundle

Bundles a spec by resolving external references.

Configuration can point to a single in- and output file, or to in- and output directories. When directories are
specified, all files specified by the `includes` parameter are bundled.

Examples in `json` files are parsed to objects.

    <configuration>
        <skip>${bundle.skip}</skip>
        <input>${project.basedir}/src/main/resources/</input>
        <output>${project.build.outputDirectory}/specs/</output>
        <includes>*-api-v*.yaml</includes>
        <removeExtensions>
            <extension>x-extra-annotations</extension>
            <extension>x-implements</extension>
        </removeExtensions>
    </configuration>

For more information, run 

    mvn help:describe -Dplugin=com.backbase.oss:boat-maven-plugin -Dgoal=bundle -Ddetail

Configuration example

```$xml
   <build>
       <plugins>
            <plugin>
                <groupId>com.backbase.oss</groupId>
                <artifactId>boat-maven-plugin</artifactId>
                <version>0.1.0.0-SNAPSHOT</version>
                <configuration>
                    <!-- Showing defaults - do not configure defaults! -->
                    <!-- The input directory -->
                    <input>${project.basedir}/src/main/resources</input>
                    <!-- The output directory -->
                    <output>${project.basedir}/target/openapi</output>
                    <!-- Whether to fail the build on errors -->
                    <failOnError>true</failOnError>
                    <!-- Override the default server entry -->
                    <servers>
                        <server>
                            <url>http://localhost:4010</url>
                            <description>mock-api-server</description>
                        </server>
                    </servers>
                    <!-- Add additional properties ('additions') element to specified types -->
                    <addAdditionalProperties>
                        <schema>User</schema>
                        <schema>UserItem</schema>
                    </addAdditionalProperties>
                    <!-- Adding 'x-java-type' extension when json schema includes 'javaType' (default false) -->
                    <addJavaTypeExtensions>false</addJavaTypeExtensions>
                    <!-- Convert request and response body examples to yaml (default true) -->
                    <convertJsonExamplesToYaml>false</convertJsonExamplesToYaml>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

Usage

    mvn boat:generate

Or hook up to your build process by adding ```executions``` configuration.

## boat:transform

Apply transformers to an existing specification.

Available parameters:

    inputs
      Required: true
      User property: boat.transform.inputs
      A list of input specifications.

    mappers
      File name mappers used to generate the output file name, instances of
      org.codehaus.plexus.components.io.filemappers.FileMapper.
      The following mappers can be used without needing to specify the FQCN of
      the implementation.
      
      regexp:
        org.codehaus.plexus.components.io.filemappers.RegExpFileMapper
      merge:
        org.codehaus.plexus.components.io.filemappers.MergeFileMapper
      prefix:
        org.codehaus.plexus.components.io.filemappers.PrefixFileMapper
      suffix:
        org.codehaus.plexus.components.io.filemappers.SuffixFileMapper
      
      The parameter defaults to
      
       <mappers>
            <suffix>-transformed</suffix>
       </mappers>

    options
      Additional options passed to transformers.

    output (Default: ${project.build.directory})
      User property: boat.transform.output
      Target directory of the transformed specifications.

    pipeline
      Required: true
      The list of transformers to be applied to each input specification.

    serverId
      User property: boat.transform.serverId
      Retrieves authorization from Maven's settings.xml.

    skip
      Alias: codegen.skip
      User property: boat.transform.skip
      Whether to skip the execution of this goal.

Configuration example

```$xml
    <configuration>
        <inputs>
            <input>${project.build.directory}/openapi.yaml</input>
        </inputs>
        <mappers>
            <merge>${spec.name}-${spec.version}.yaml</merge>
        </mappers>
        <pipeline>
            <setVersion>${spec.version}</setVersion>
            <extensionFilter>
                <remove>
                    <extension>abstract</extension>
                    <extension>implements</extension>
                    <extension>extra-annotation</extension>
                    <extension>extra-java-code</extension>
                </remove>
            </extensionFilter>
        </pipeline>
    </configuration>
```

package com.backbase.oss.boat;

import java.io.File;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * Bundles all references in the OpenAPI specification into one file.
 */
@Mojo(name = "x-bundle", requiresDependencyResolution = ResolutionScope.RUNTIME, threadSafe = true)
@Slf4j
public class CreateBundleMojo extends AbstractMojo {

    /**
     * Skip the execution.
     */
    @Parameter(name = "skip", property = "bundle.skip", defaultValue = "false", alias = "codegen.skip")
    private boolean skip;

    @Parameter(name = "input", required = true)
    private File input;

    @Parameter(name = "output", required = true)
    private File output;

    @Parameter(name = "version", required = false)
    private String version;

    @Parameter(name = "remove-extensions", required = false, defaultValue = "")
    private List<String> removeExtensions;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("Skipping OpenAPI bundle.");

            return;
        }

        log.info("Bundling OpenAPI: {} to: {}", input, output);
    }
}



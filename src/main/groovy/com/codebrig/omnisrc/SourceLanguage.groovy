package com.codebrig.omnisrc

import com.google.common.base.Charsets
import com.google.common.io.Files
import com.google.common.io.Resources

/**
 * @author github.com/BFergerson
 */
enum SourceLanguage {

    OmniSRC([]),
    //Bash(["csh", "tcsh", "bash", "sh", "zsh"]),
    Go(["go"]),
    Java(["java"]),
    Javascript(["js"]),
    Php(["php"]),
    Python(["py"]),
    Ruby(["rb"])

    private final Set<String> fileExtensions

    SourceLanguage(List<String> fileExtensions) {
        this.fileExtensions = new HashSet<>(fileExtensions)
    }

    String key() {
        return name().toLowerCase()
    }

    String getQualifiedName() {
        if (this == OmniSRC) {
            return "Omnilingual"
        }
        return key().substring(0, 1).toUpperCase() + key().substring(1)
    }

    String getSchemaDefinitionName() {
        return "OmniSRC_" + qualifiedName + "_Schema"
    }

    String getFullSchemaDefinition(String version) {
        if (this == OmniSRC) {
            return Resources.toString(Resources.getResource(
                    "schema/omnilingual/$schemaDefinitionName-$version" + ".gql"), Charsets.UTF_8)
        } else {
            return Resources.toString(Resources.getResource(
                    "schema/unilingual/" + key() + "/$schemaDefinitionName-$version" + ".gql"), Charsets.UTF_8)
        }
    }

    boolean isValidExtension(String extension) {
        return fileExtensions.contains(extension.toLowerCase())
    }

    static boolean isSourceLanguageKnown(File file) {
        def fileExtension = Files.getFileExtension(file.name)
        return values().any { it.isValidExtension(fileExtension) }
    }

    static SourceLanguage getSourceLangauge(File file) {
        def sourceLanguage
        def fileExtension = Files.getFileExtension(file.name)
        values().each {
            if (it.isValidExtension(fileExtension)) {
                sourceLanguage = it
            }
        }
        if (sourceLanguage != null) {
            return sourceLanguage
        } else {
            throw new IllegalArgumentException("Could not detect source code language of file: " + file)
        }
    }
}

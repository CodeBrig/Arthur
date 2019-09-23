package com.codebrig.arthur.observe.structure.filter.operator.logical

import com.codebrig.arthur.ArthurTest
import com.codebrig.arthur.SourceLanguage
import com.codebrig.arthur.observe.structure.filter.FunctionFilter
import com.codebrig.arthur.observe.structure.filter.MultiFilter
import com.codebrig.arthur.observe.structure.filter.NameFilter
import org.bblfsh.client.v2.BblfshClient
import org.junit.Test

import static org.junit.Assert.*

class AndOperatorFilterTest extends ArthurTest {

    @Test
    void andOperator_Go() {
        assertAndOperatorPresent(new File("src/test/resources/same/operators/Operators.go"),
                "&&")
    }

    @Test
    void andOperator_Java() {
        assertAndOperatorPresent(new File("src/test/resources/same/operators/Operators.java"),
                "&&", "Operators.")
    }

    @Test
    void andOperator_Javascript() {
        assertAndOperatorPresent(new File("src/test/resources/same/operators/Operators.js"),
                "&&")
    }

    @Test
    void andOperator_Python() {
        assertAndOperatorPresent(new File("src/test/resources/same/operators/Operators.py"),
                "and")
    }

    @Test
    void andOperator_CSharp() {
        assertAndOperatorPresent(new File("src/test/resources/same/operators/Operators.cs"),
                "&&")
    }

    @Test
    void andOperator_CPlusPlus() {
        assertAndOperatorPresent(new File("src/test/resources/same/operators/Operators.cpp"),
                "&&")
    }

    private static void assertAndOperatorPresent(File file, String andToken) {
        assertAndOperatorPresent(file, andToken, "")
    }

    private static void assertAndOperatorPresent(File file, String andToken, String qualifiedName) {
        def language = SourceLanguage.getSourceLanguage(file)
        def resp = client.parse(file.name, file.text, language.babelfishName)
        def rootNode = new BblfshClient.UastMethods(resp.uast()).decode().root().load()

        def foundAndOperator = false
        def functionFilter = new FunctionFilter()
        def nameFilter = new NameFilter(qualifiedName + "andOperator()")
        MultiFilter.matchAll(functionFilter, nameFilter).getFilteredNodes(language, rootNode).each {
            assertEquals(qualifiedName + "andOperator()", it.name)

            new AndOperatorFilter().getFilteredNodes(it).each {
                assertFalse(foundAndOperator)
                assertEquals(andToken, it.token)
                foundAndOperator = true
            }
        }
        assertTrue(foundAndOperator)
    }
}
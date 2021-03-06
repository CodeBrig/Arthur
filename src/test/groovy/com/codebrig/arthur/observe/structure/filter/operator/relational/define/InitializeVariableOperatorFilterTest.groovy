package com.codebrig.arthur.observe.structure.filter.operator.relational.define

import com.codebrig.arthur.ArthurTest
import com.codebrig.arthur.SourceLanguage
import com.codebrig.arthur.observe.structure.filter.FunctionFilter
import com.codebrig.arthur.observe.structure.filter.MultiFilter
import com.codebrig.arthur.observe.structure.filter.NameFilter
import com.codebrig.arthur.observe.structure.filter.TokenFilter
import gopkg.in.bblfsh.sdk.v1.protocol.generated.Encoding
import org.junit.Test

import static org.junit.Assert.*

class InitializeVariableOperatorFilterTest extends ArthurTest {

    @Test
    void initializeVariableOperator_Go() {
        assertInitializeVariableOperatorPresent(new File("src/test/resources/same/operators/Operators.go"))
    }

    @Test
    void initializeVariableOperator_Java() {
        assertInitializeVariableOperatorPresent(new File("src/test/resources/same/operators/Operators.java"),
                "Operators.")
    }

    @Test
    void initializeVariableOperator_Javascript() {
        assertInitializeVariableOperatorPresent(new File("src/test/resources/same/operators/Operators.js"))
    }

    @Test
    void initializeVariableOperator_Php() {
        assertInitializeVariableOperatorPresent(new File("src/test/resources/same/operators/Operators.php"))
    }

    @Test
    void initializeVariableOperator_Python() {
        assertInitializeVariableOperatorPresent(new File("src/test/resources/same/operators/Operators.py"))
    }

    @Test
    void initializeVariableOperator_CSharp() {
        assertInitializeVariableOperatorPresent(new File("src/test/resources/same/operators/Operators.cs"))
    }

    @Test
    void initializeVariableOperator_CPlusPlus() {
        assertInitializeVariableOperatorPresent(new File("src/test/resources/same/operators/Operators.cpp"))
    }

    @Test
    void initializeVariableOperator_Ruby() {
        assertInitializeVariableOperatorPresent(new File("src/test/resources/same/operators/Operators.rb"))
    }

    @Test
    void initializeVariableOperator_Bash() {
        assertInitializeVariableOperatorPresent(new File("src/test/resources/same/operators/Operators.sh"))
    }

    private static void assertInitializeVariableOperatorPresent(File file) {
        assertInitializeVariableOperatorPresent(file, "")
    }

    private static void assertInitializeVariableOperatorPresent(File file, String qualifiedName) {
        def language = SourceLanguage.getSourceLanguage(file)
        def resp = client.parse(file.name, file.text, language.babelfishName, Encoding.UTF8$.MODULE$)

        def foundInitializeVariableOperator = false
        def functionFilter = new FunctionFilter()
        def nameFilter = new NameFilter(qualifiedName + "initializeVariableOperator()")
        MultiFilter.matchAll(functionFilter, nameFilter).getFilteredNodes(language, resp.uast).each {
            assertEquals(qualifiedName + "initializeVariableOperator()", it.name)

            new InitializeVariableOperatorFilter().getFilteredNodesIncludingCurrent(it).each {
                assertFalse(foundInitializeVariableOperator)
                assertNotNull(new TokenFilter("x").getFilteredNodesIncludingCurrent(it).next())
                foundInitializeVariableOperator = true
            }
        }
        assertTrue(foundInitializeVariableOperator)
    }
}

package com.codebrig.omnisrc.observe.structure.naming

import com.codebrig.omnisrc.SourceNode
import com.codebrig.omnisrc.observe.structure.StructureNaming

/**
 * Used to get the names of Ruby AST nodes
 *
 * @version 0.3.1
 * @since 0.3
 * @author <a href="mailto:brandon.fergerson@codebrig.com">Brandon Fergerson</a>
 */
class RubyNaming implements StructureNaming {

    @Override
    boolean isNamedNodeType(String internalType) {
        switch (Objects.requireNonNull(internalType)) {
            case "def":
                return true
            default:
                return false
        }
    }

    @Override
    String getNodeName(SourceNode node) {
        switch (Objects.requireNonNull(node).internalType) {
            case "def":
                return getDefName(node)
            default:
                return null
        }
    }

    static String getDefName(SourceNode node) {
        def defName = node.token
        return defName + "()"
    }
}

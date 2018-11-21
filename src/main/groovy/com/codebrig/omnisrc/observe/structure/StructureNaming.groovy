package com.codebrig.omnisrc.observe.structure

import com.codebrig.omnisrc.SourceNode

/**
 * Used to get the names/qualified names of UAST nodes
 *
 * @version 0.3
 * @since 0.2
 * @author <a href="mailto:brandon.fergerson@codebrig.com">Brandon Fergerson</a>
 */
interface StructureNaming {
    String getNodeName(SourceNode node)
}

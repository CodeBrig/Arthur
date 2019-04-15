package com.codebrig.omnisrc.observe.filter

import com.codebrig.omnisrc.SourceLanguage
import com.codebrig.omnisrc.SourceNode
import com.codebrig.omnisrc.SourceNodeFilter

/**
 * Match by the source code language
 *
 * @version 0.3.1
 * @since 0.2
 * @author <a href="mailto:brandon.fergerson@codebrig.com">Brandon Fergerson</a>
 */
class LanguageFilter extends SourceNodeFilter<LanguageFilter, SourceLanguage> {

    LanguageFilter(SourceLanguage... values) {
        accept(values)
    }

    @Override
    boolean evaluate(SourceNode node) {
        if (node != null) {
            return evaluateProperty(node.language)
        }
        return false
    }
}

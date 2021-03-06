package com.codebrig.arthur.observe

import com.codebrig.arthur.SourceLanguage
import com.codebrig.arthur.observe.observations.ObservedRoles
import com.codebrig.arthur.schema.SchemaSegment

/**
 * Represents multiple observed languages
 *
 * @version 0.4
 * @since 0.1
 * @author <a href="mailto:brandon.fergerson@codebrig.com">Brandon Fergerson</a>
 */
class ObservedLanguages extends ObservedLanguage {

    static ObservedLanguages mergeLanguages(ObservedLanguage... observedLanguages) {
        return mergeLanguages(Arrays.asList(observedLanguages))
    }

    static ObservedLanguages mergeLanguages(List<ObservedLanguage> observedLanguages) {
        def segments = new HashSet<SchemaSegment>()
        observedLanguages.each {
            segments.addAll(it.config.observedSegments)
        }
        def omniLanguage = new ObservedLanguages(new ObservationConfig(segments.toArray(new SchemaSegment[0])))
        observedLanguages.each { lang ->
            lang.observedAttributes.each { attribute ->
                observedLanguages.stream().each {
                    if (lang.language != it.language && it.observedAttribute(attribute)) {
                        omniLanguage.observeGlobalAttribute(attribute)
                        observedLanguages.each {
                            it.addAttributeExtends(attribute)
                        }
                    }
                }
            }
            lang.observedRelations.each { relation ->
                observedLanguages.stream().each {
                    if (lang.language != it.language && it.observedRelation(relation)) {
                        omniLanguage.observeGlobalRelation(relation)
                        observedLanguages.each {
                            it.addRelationExtends(relation)
                        }
                    }
                }
            }
            lang.getObservedSemanticRoles(true).each {
                omniLanguage.observeGlobalSemanticRole(it)
            }
        }
        return omniLanguage
    }

    final Set<String> globalAttributes
    final Set<String> globalRelations
    final Set<String> globalSemanticRoles

    private ObservedLanguages(ObservationConfig observationConfig) {
        super(SourceLanguage.Omnilingual, observationConfig)
        this.globalAttributes = new HashSet<>()
        this.globalRelations = new HashSet<>()
        this.globalSemanticRoles = new HashSet<>()
    }

    void observeGlobalAttribute(String attribute) {
        globalAttributes.add(attribute)
    }

    void observeGlobalRelation(String relation) {
        globalRelations.add(relation)
    }

    void observeGlobalSemanticRole(String role) {
        globalSemanticRoles.add(role)
    }

    @Override
    List<String> getObservedAttributes(boolean naturalOrdering) {
        if (naturalOrdering) {
            def rtnAttributes = globalAttributes.toList()
            rtnAttributes.sort(String.CASE_INSENSITIVE_ORDER)
            return rtnAttributes
        } else {
            return globalAttributes.toList()
        }
    }

    @Override
    List<String> getObservedSemanticRoles(boolean naturalOrdering) {
        if (naturalOrdering) {
            def rtnRoles = globalSemanticRoles.toList()
            rtnRoles.sort(String.CASE_INSENSITIVE_ORDER)
            return rtnRoles
        } else {
            return globalSemanticRoles.toList()
        }
    }

    @Override
    List<String> getObservedRelations(boolean naturalOrdering) {
        if (naturalOrdering) {
            def rtnRelations = globalRelations.toList()
            rtnRelations.sort(String.CASE_INSENSITIVE_ORDER)
            return rtnRelations
        } else {
            return globalRelations.toList()
        }
    }

    @Override
    boolean observedAttribute(String attribute) {
        return globalAttributes.contains(attribute)
    }

    @Override
    boolean observedRelation(String relation) {
        return globalRelations.contains(relation)
    }
}

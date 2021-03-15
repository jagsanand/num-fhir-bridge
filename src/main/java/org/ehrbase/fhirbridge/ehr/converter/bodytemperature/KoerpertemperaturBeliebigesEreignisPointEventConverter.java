package org.ehrbase.fhirbridge.ehr.converter.bodytemperature;

import ca.uhn.fhir.rest.server.exceptions.UnprocessableEntityException;
import org.ehrbase.fhirbridge.ehr.converter.AbstractEntryEntityConverter;
import org.ehrbase.fhirbridge.ehr.converter.AbstractPointEventConverter;
import org.ehrbase.fhirbridge.ehr.opt.intensivmedizinischesmonitoringkorpertemperaturcomposition.definition.KoerpertemperaturBeliebigesEreignisPointEvent;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Quantity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class KoerpertemperaturBeliebigesEreignisPointEventConverter extends AbstractPointEventConverter<Observation, KoerpertemperaturBeliebigesEreignisPointEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(BodyTemperatureCompositionConverter.class);

    @Override
    public KoerpertemperaturBeliebigesEreignisPointEvent convert(Observation resource) {
        Quantity fhirValue = null;
        BigDecimal fhirValueNumeric = null;
        try {
            fhirValue = resource.getValueQuantity();
            fhirValueNumeric = fhirValue.getValue();
            LOG.debug("Value numeric: {}", fhirValueNumeric);
        } catch (Exception e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        if (fhirValueNumeric == null) {
            throw new UnprocessableEntityException("Value is required in FHIR Observation and should be Quantity");
        }
        KoerpertemperaturBeliebigesEreignisPointEvent tempEvent = new KoerpertemperaturBeliebigesEreignisPointEvent();
        mapCommonAttributes(resource, tempEvent);
        tempEvent.setTemperaturMagnitude(fhirValueNumeric.doubleValue());
        tempEvent.setTemperaturUnits(fhirValue.getUnit());
        return tempEvent;
    }
}

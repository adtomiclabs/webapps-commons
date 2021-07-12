package com.adtomiclabs.commons.dtos.opportunity;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OpportunityClientGoogleTextDTO {

    @JsonProperty("client_id")
    private Long clientId;

    @JsonProperty("texts")
    private List<OpportunityGoogleTextDTO> texts;
}

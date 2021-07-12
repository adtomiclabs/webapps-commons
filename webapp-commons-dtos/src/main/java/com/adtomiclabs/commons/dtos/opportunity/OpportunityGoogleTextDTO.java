package com.adtomiclabs.commons.dtos.opportunity;

import java.time.LocalDate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.adtomiclabs.commons.dtos.utils.LocalDateDeserializer;
import com.adtomiclabs.commons.dtos.utils.LocalDateSerializer;
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
public class OpportunityGoogleTextDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("language")
    private String language;

    @JsonProperty("header")
    private String header;

    @JsonProperty("description")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("last_modified")
    private LocalDate lastModified;
}

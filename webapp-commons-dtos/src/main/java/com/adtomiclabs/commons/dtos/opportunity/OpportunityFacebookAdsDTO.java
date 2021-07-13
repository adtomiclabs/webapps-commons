package com.adtomiclabs.commons.dtos.opportunity;

import java.time.LocalDate;
import java.util.List;
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
public class OpportunityFacebookAdsDTO {

    @JsonProperty("ads_id")
    private List<String> adIds;

    @JsonProperty("status")
    private String status;

    @JsonProperty("type")
    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("date_from")
    private LocalDate dateFrom;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("date_to")
    private LocalDate dateTo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty("date_created")
    private LocalDate dateCreated;

    @JsonProperty("name")
    private String name;

    @JsonProperty("product_set_name")
    private String productSetName;

    @JsonProperty("is_default_ad")
    private boolean defaultAd;
}

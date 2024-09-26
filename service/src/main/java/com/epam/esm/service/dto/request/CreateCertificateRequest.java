package com.epam.esm.service.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Set;

public class CreateCertificateRequest {
    @Size(min = 3, max = 50, message = "Certificate name must be {min}-{max} characters long.")
    @NotBlank(message = "Certificate name can't be blank.")
    @Pattern(regexp = "^[\\w\\s]+$", message = "Certificate name must be alphanumeric.")
    private String name;

    @Size(min = 10, max = 3000, message = "Certificate description must be {min}-{max} characters long.")
    @NotBlank(message = "Certificate description can't be blank.")
    @Pattern(regexp = "^[\\w\\s]+$", message = "Certificate description must be alphanumeric.")
    private String description;

    @NotNull
    @Positive(message = "Certificate price must be positive number.")
    private Integer price;

    @NotNull
    @Positive(message = "Certificate duration must be positive number.")
    private Integer duration;

    private Set<@Valid TagRequest> tags;

    public CreateCertificateRequest() {
    }

    public CreateCertificateRequest(String name, String description, Integer price, Integer duration) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }

    public CreateCertificateRequest(String name, String description, Integer price, Integer duration, Set<TagRequest> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Set<TagRequest> getTags() {
        return tags;
    }

    public void setTags(Set<TagRequest> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "CreateCertificateRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", tags=" + tags +
                '}';
    }
}

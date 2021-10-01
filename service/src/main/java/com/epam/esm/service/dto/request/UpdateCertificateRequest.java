package com.epam.esm.service.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Set;

public class UpdateCertificateRequest {
    @NotNull(message = "Certificate id can't be null.")
    private Integer id;

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

    private Set<
            @NotBlank(message = "Tag name can't be blank")
            @Size(min = 3, max = 25, message = "Tag name must be {min}-{max} characters long.")
            @Pattern(regexp = "^[\\w\\s]+$", message = "Tag name must be alphanumeric.")
                    String
            > tagNames;

    public UpdateCertificateRequest() {
    }

    public UpdateCertificateRequest(Integer id, String name, String description, Integer price, Integer duration, Set<String> tagNames) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tagNames = tagNames;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Set<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(Set<String> tagNames) {
        this.tagNames = tagNames;
    }

    @Override
    public String toString() {
        return "UpdateCertificateRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + price +
                ", duration=" + duration +
                ", tagNames=" + tagNames +
                '}';
    }
}

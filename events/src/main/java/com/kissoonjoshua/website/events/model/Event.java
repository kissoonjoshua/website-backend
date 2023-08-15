package com.kissoonjoshua.website.events.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Events")
public class Event {
    @Id
    private String id;
    @NotBlank(message = "Missing VideoID")
    private String videoId;
    @NotBlank(message = "Missing UserID")
    private String userId;
    @NotBlank(message = "Missing Title")
    private String title;
    @NotBlank(message = "Missing Description")
    private String description;
    private long views;
    private long likes;
    private long comments;
    @NotEmpty(message = "Missing Categories")
    private List<String> categories;
}

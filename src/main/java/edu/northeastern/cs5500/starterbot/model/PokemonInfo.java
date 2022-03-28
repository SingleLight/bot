package edu.northeastern.cs5500.starterbot.model;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class PokemonInfo implements Model {

    private ObjectId id;
    private String pokemonName;
    private int pokemonNumber;
    private String gender;
    private List<String> pokemonType;
    private List<ObjectId> evolution;
    private String introduction;
}
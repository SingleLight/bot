package edu.northeastern.cs5500.starterbot.controller;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.Pokemon;
import edu.northeastern.cs5500.starterbot.model.PokemonInfo;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import java.util.HashMap;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PokemonControllerTest {

    private PokemonInfoController pokemonInfoController;
    private PokemonController pokemonController;

    @BeforeEach
    void setUp() {
        pokemonInfoController = new PokemonInfoController(new InMemoryRepository<>());
        pokemonController =
                new PokemonController(pokemonInfoController, new InMemoryRepository<>());
    }

    @Test
    void testGetPokemonByObjectId() {
        Pokemon pokemon = new Pokemon();
        pokemonController.addPokemon(pokemon);
        Pokemon pokemon2 = pokemonController.getPokemonByObjectId(pokemon.getId());
        assertThat(pokemon2).isEqualTo(pokemon);
    }

    @Test
    void testGetGender() {
        Pokemon pokemon = new Pokemon();
        pokemon.setGender("male");
        pokemonController.addPokemon(pokemon);
        String gender = pokemonController.getGender(pokemon.getId());
        assertThat(gender).isEqualTo("male");
    }

    @Test
    void testGetCp() {
        Pokemon pokemon = new Pokemon();
        pokemon.setCp(123);
        pokemonController.addPokemon(pokemon);
        int cp = pokemonController.getCp(pokemon.getId());
        assertThat(cp).isEqualTo(123);
    }

    @Test
    void testGetPokemonInfo() {
        PokemonInfo pokemonInfo = new PokemonInfo();
        pokemonInfoController.addToRepo(pokemonInfo);
        Pokemon pokemon = new Pokemon();
        pokemon.setPokemonInfo(pokemonInfo.getId());
        pokemonController.addPokemon(pokemon);
        ObjectId info = pokemonController.getPokemonInfo(pokemon.getId());
        assertThat(info).isEqualTo(pokemonInfo.getId());
    }

    @Test
    void testGetLevel() {
        Pokemon pokemon = new Pokemon();
        pokemon.setLevel(66);
        pokemonController.addPokemon(pokemon);
        int level = pokemonController.getLevel(pokemon.getId());
        assertThat(level).isEqualTo(66);
    }

    @Test
    void testPowerUp() {
        Pokemon pokemon = new Pokemon();
        pokemon.setLevel(67);
        pokemonController.addPokemon(pokemon);
        pokemonController.powerUp(pokemon.getId());
        assertThat(pokemon.getLevel()).isEqualTo(68);
    }

    @Test
    void testGetName() {
        PokemonInfo pokemonInfo = new PokemonInfo();
        pokemonInfo.setPokemonName("testPokemonName");
        pokemonInfoController.addToRepo(pokemonInfo);
        Pokemon pokemon = new Pokemon();
        pokemon.setPokemonInfo(pokemonInfo.getId());
        pokemonController.addPokemon(pokemon);
        assertThat(pokemonController.getName(pokemon.getId())).isEqualTo("testPokemonName");
    }

    @Test
    void testGetHp() {
        Pokemon pokemon = new Pokemon();
        pokemon.setHp(100);
        pokemonController.addPokemon(pokemon);
        assertThat(pokemonController.getHp(pokemon.getId())).isEqualTo(100);
    }

    @Test
    void testGetMaxHp() {
        PokemonInfo pokemonInfo = new PokemonInfo();
        pokemonInfo.setMaxHP(1200);
        pokemonInfoController.addToRepo(pokemonInfo);
        Pokemon pokemon = new Pokemon();
        pokemon.setPokemonInfo(pokemonInfo.getId());
        pokemonController.addPokemon(pokemon);
        assertThat(pokemonController.getMaxHp(pokemon.getId())).isEqualTo(1200);
    }

    @Test
    void testGetImage() {
        PokemonInfo pokemonInfo = new PokemonInfo();
        pokemonInfo.setPictureAddress("pictureAddress");
        pokemonInfoController.addToRepo(pokemonInfo);
        Pokemon pokemon = new Pokemon();
        pokemon.setPokemonInfo(pokemonInfo.getId());
        pokemonController.addPokemon(pokemon);
        assertThat(pokemonController.getImage(pokemon.getId())).isEqualTo("pictureAddress");
    }

    @Test
    void testGetCurrentHp() {
        Pokemon pokemon = new Pokemon();
        pokemon.setCurrentHp(1201);
        pokemonController.addPokemon(pokemon);
        assertThat(pokemonController.getCurrentHp(pokemon.getId())).isEqualTo(1201);
    }

    @Test
    void testSetCurrentHp() {
        Pokemon pokemon = new Pokemon();
        pokemon.setCurrentHp(1001);
        pokemonController.addPokemon(pokemon);
        pokemonController.setCurrentHp(pokemon.getId(), 1021);
        assertThat(pokemonController.getCurrentHp(pokemon.getId())).isEqualTo(1021);
    }

    @Test
    void testGetOwnedMoves() {
        HashMap<String, String> newMoves = new HashMap<String, String>();
        newMoves.put("Seed Bomb", "80 100");
        newMoves.put("Take Down", "90 85");
        Pokemon pokemon = new Pokemon();
        pokemon.setOwnedMoves(newMoves);
        pokemonController.addPokemon(pokemon);
        assertThat(pokemonController.getOwnedMoves(pokemon.getId()).length).isEqualTo(4);
    }
}

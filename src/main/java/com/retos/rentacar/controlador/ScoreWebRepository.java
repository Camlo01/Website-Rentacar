
package com.retos.rentacar.controlador;

import com.retos.rentacar.modelo.Score;
import com.retos.rentacar.servicios.ScoreServices;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Score")
@CrossOrigin(origins = "*", methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ScoreWebRepository {
    @GetMapping("/holaMundoScore")
    public String saludad(){
    return "Hola Mundo Score";
    }
    
    @Autowired
    private ScoreServices servicios;
    @GetMapping("/all")
    public List <Score> getScore(){
        return servicios.getAll();
    }
    @GetMapping("/{id}")
    public Optional<Score> getScore(@PathVariable("id") int idScore) {
        return servicios.getScore(idScore);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Score save(@RequestBody Score score) {
        return servicios.save(score);
    }
     
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public boolean delete(@PathVariable ("id") int idScore) {
        return servicios.deleteScore(idScore);
    }
}

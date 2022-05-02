package com.retos.rentacar.repositorio;

import com.retos.rentacar.interfaces.ScoreInterface;
import com.retos.rentacar.modelo.Score;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ScoreRepository {
    @Autowired
    private ScoreInterface crudScore;
    
    public List<Score> getAll(){
        return (List<Score>) crudScore.findAll();
    }
    public Optional <Score> getScore(int id){
        return crudScore.findById(id);
    }
    
    public Score save(Score score){
        return crudScore.save(score);
    }
    public void delete(Score score){
        crudScore.delete(score);
    }    
    
}

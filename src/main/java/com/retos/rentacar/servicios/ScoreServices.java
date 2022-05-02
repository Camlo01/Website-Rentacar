
package com.retos.rentacar.servicios;

import com.retos.rentacar.modelo.Score;
import com.retos.rentacar.repositorio.ScoreRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreServices {
    @Autowired
    private ScoreRepository metodosCrudScore;
    
    public List<Score> getAll(){
         return metodosCrudScore.getAll();
    }
    
    public Optional<Score> getScore(int idScore){
        return metodosCrudScore.getScore(idScore);
    }
    
    
    public Score save(Score score){
        if(score.getIdScore()==null){
            return metodosCrudScore.save(score);
        }else{
            Optional<Score> evt=metodosCrudScore.getScore(score.getIdScore());
            if(evt.isEmpty()){
            return metodosCrudScore.save(score);
            }else{
                return score;
            }
       
        }
    
    }
   
    public Score update(Score score){
        if(score.getIdScore()!=null){
            Optional<Score> evt=metodosCrudScore.getScore(score.getIdScore());
            if(!evt.isEmpty()){
                if(score.getScore()!=null){
                    evt.get().setScore(score.getScore());
                    }
                if(score.getTextScore()!=null){
                    evt.get().setTextScore(score.getTextScore());
                    }
                metodosCrudScore.save(evt.get());
                return score;
                }
            else{
                return score;
            }
        }
        else{
            return score;
                }
        }   
    
    public boolean deleteScore (int IdScore) {
        Boolean aBoolean = getScore(IdScore).map(score ->{
            metodosCrudScore.delete(score);
            return true;
        }).orElse(false);
        return aBoolean;
    }
}

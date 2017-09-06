/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.util.ArrayList;

/**
 *
 * @author yisroel
 */
public class HighScore
{
    ArrayList<Score> highScore = new ArrayList();
    int maxHighScore = 3;
    
    public void save()
    {
        
    }
    
     public void load()
    {
        
    }
     
    public void add(String name, int score)
    {
       if(isHighScore(score))
       {
            if(highScore.size()<maxHighScore)
        {
           // highScore.add();
        }
            
            
       }
    }
    public boolean isHighScore(int score)
    {
        if(highScore.size()<maxHighScore)
        {
            return true;
        }
        if(highScore.get(highScore.size()-1).getScore() > score)
        {
             return true;
        }
        return false;
    }
   
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (Score score : highScore)
        {
            sb.append(score.getName());
            sb.append(" - ");
            sb.append(score.getScore());
            sb.append("\n");
        }
        return sb.toString();
    }
    
    
    
    
    
    
}
